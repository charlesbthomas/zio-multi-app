package dev.astern

import dev.astern.http.AsternHttpServer
import dev.astern.http.AsternHttpApp
import zio.ZIO
import zio.ZLayer

case class AuthServer(authApp: AuthApp) extends AsternHttpServer:
  override def apps: Seq[AsternHttpApp] = Seq(authApp)
  
object AuthServer:
  lazy val layer: zio.URLayer[AuthApp, AuthServer] = ZLayer.fromFunction(AuthServer.apply)
