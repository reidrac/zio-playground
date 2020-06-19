package hello

import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.implicits._

import zio._
import zio.clock.Clock
import zio.interop.catz._
import zio.logging._
import zio.logging.slf4j._

object HelloApp extends zio.App {

  def runHttpServer[R <: Clock with Logging]: ZIO[R, Throwable, Unit] =
    ZIO.runtime[R].flatMap { implicit rts =>
      type Task[A] = RIO[R, A]

      val dsl: Http4sDsl[Task] = Http4sDsl[Task]
      import dsl._

      val httpApp = HttpRoutes
        .of[Task] {
          case GET -> Root / "hello" / name =>
            for {
              _ <- log.info(s"Saying hello to $name")
              response <- Ok(s"hello, $name!")
            } yield response
        }
        .orNotFound

      BlazeServerBuilder[Task]
        .bindHttp(8080, "localhost")
        .withHttpApp(httpApp)
        .serve
        .compile[Task, Task, cats.effect.ExitCode]
        .drain
    }

  override def run(args: List[String]): ZIO[ZEnv, Nothing, zio.ExitCode] = {
    val env = Clock.live ++ Slf4jLogger.make((_, logEntry) => logEntry)

    val app = for {
      _ <- log.info("Starting on http://localhost:8080/")
      _ <- runHttpServer
    } yield zio.ExitCode(0)

    app.provideSomeLayer(env).orDie
  }
}
