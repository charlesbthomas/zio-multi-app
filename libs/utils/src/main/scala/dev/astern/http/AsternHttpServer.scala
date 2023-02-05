package dev.astern.http

import zhttp.http.*
import zhttp.service.Server
import zio.*

trait AsternHttpServer:

  def apps: Seq[AsternHttpApp]

  private val allRoutes = apps.map(_.routes).reduce((a, b) => a ++ b)

  def start: ZIO[RequestContextManager, Throwable, Unit] =
    for
      _ <- ZIO.succeed(println("Starting HTTP Server"))
      port <- System.envOrElse("PORT", "8080").map(_.toInt)
      _ <- Server.start(port, allRoutes) <* ZIO.succeed(
        println(s"HTTP Server listening on port $port")
      )
    yield ()
