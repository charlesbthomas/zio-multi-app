package dev.astern

import dev.astern.http.AsternHttpApp
import zhttp.http.Http
import zhttp.http.Request
import dev.astern.http.RequestContextManager
import zhttp.http.Response
import dev.astern.endpoints.HelloWorldEndpoint

case class AuthApp(helloWorldEndpoint: HelloWorldEndpoint) extends AsternHttpApp:
  override def routes: Http[RequestContextManager, Throwable, Request, Response] = 
    helloWorldEndpoint.route

object AuthApp:
  lazy val layer: zio.URLayer[HelloWorldEndpoint, AuthApp] =
    zio.ZLayer.fromFunction(AuthApp.apply)
