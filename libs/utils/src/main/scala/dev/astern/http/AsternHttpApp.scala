package dev.astern.http

import zhttp.http.*
import dev.astern.http.RequestContextManager

trait AsternHttpApp:
  def routes: Http[RequestContextManager, Throwable, Request, Response]
