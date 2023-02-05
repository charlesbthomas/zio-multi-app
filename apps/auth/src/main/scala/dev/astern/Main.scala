package dev.astern

import zio.ZIOAppDefault
import zio.Scope
import zio.ZIO
import zio.ZIOAppArgs
import zio.Task
import dev.astern.http.DefaultRequestContextManager

object Main extends ZIOAppDefault:
  override def run: Task[Unit] = ZIO
    .serviceWithZIO[AuthServer](_.start)
    .provide(
      AuthComponents.all,
      AuthServer.layer,
      DefaultRequestContextManager.layer
    )
