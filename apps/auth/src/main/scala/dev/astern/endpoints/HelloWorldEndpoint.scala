package dev.astern.endpoints

import zhttp.http.*

import zio.*

import zio.ULayer

final class HelloWorldEndpoint:
  lazy val route: HttpApp[Any, Throwable] = Http.collectZIO[Request] {
    case request @ Method.GET -> !! / "app" / "auth" / "hello" =>  ZIO.succeed(Response.text("hello from auth"))
  }

object HelloWorldEndpoint:
  lazy val layer: ULayer[HelloWorldEndpoint] = ZLayer.succeed(new HelloWorldEndpoint)
