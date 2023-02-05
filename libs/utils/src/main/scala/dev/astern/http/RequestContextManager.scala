package dev.astern.http

import zio.FiberRef
import zio.UIO
import zio.ULayer
import zio.ZLayer

trait RequestContextManager {
  def setCtx(ctx: RequestContext): UIO[Unit]
  def getCtx(): UIO[RequestContext]
}

final case class DefaultRequestContextManager(ref: FiberRef[RequestContext])
    extends RequestContextManager:
  override def getCtx(): UIO[RequestContext] = ref.get
  override def setCtx(ctx: RequestContext): UIO[Unit] = ref set ctx

object DefaultRequestContextManager:
  def layer: ULayer[RequestContextManager] = ZLayer.scoped {
    for ref <- FiberRef.make(RequestContext.initial)
    yield DefaultRequestContextManager(ref)
  }
