package dev.astern.http

case class RequestContext(requestId: Option[String])

object RequestContext:
  def initial: RequestContext = new RequestContext(None)
