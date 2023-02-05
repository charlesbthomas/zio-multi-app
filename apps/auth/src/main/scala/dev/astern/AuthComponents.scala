package dev.astern

import dev.astern.endpoints.HelloWorldEndpoint
import zio.ZLayer

object AuthComponents:
  lazy val endpointLayer = HelloWorldEndpoint.layer
  lazy val appLayer = AuthApp.layer
  lazy val all = endpointLayer >>> appLayer
