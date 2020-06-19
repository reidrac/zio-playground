import mill._, scalalib._, scalafmt._

object hello extends ScalaModule with ScalafmtModule {
  def scalaVersion = "2.13.2"

  val zioVersion = "1.0.0-RC20"
  val zioInteropCatsEffectVersion = "2.1.3.0-RC15"
  val zioLoggingVersion = "0.3.1"
  val http4sVersion = "0.21.4"
  val log4j2Version = "2.13.3"

  def ivyDeps = Agg(
    ivy"dev.zio::zio:$zioVersion",
    ivy"dev.zio::zio-interop-cats:$zioInteropCatsEffectVersion",
    ivy"dev.zio::zio-logging:$zioLoggingVersion",
    ivy"dev.zio::zio-logging-slf4j:$zioLoggingVersion",
    ivy"org.http4s::http4s-blaze-server:$http4sVersion",
    ivy"org.http4s::http4s-circe:$http4sVersion",
    ivy"org.http4s::http4s-dsl:$http4sVersion",
    ivy"org.apache.logging.log4j:log4j-api:$log4j2Version",
    ivy"org.apache.logging.log4j:log4j-core:$log4j2Version",
    ivy"org.apache.logging.log4j:log4j-slf4j-impl:$log4j2Version"
  )
}
