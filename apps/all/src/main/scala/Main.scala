import zio.ZIOAppDefault
import dev.astern.AuthComponents
import dev.astern.http.AsternHttpServer
import dev.astern.http.AsternHttpApp
import dev.astern.AuthApp
import zio.ZLayer
import dev.astern.http.DefaultRequestContextManager
import zio.URLayer
import zio.Task
import zio.ZIO

final case class MonolithServer(authApp: AuthApp) extends AsternHttpServer:
  override def apps: Seq[AsternHttpApp] = Seq(authApp)

object MonolithServer:
  lazy val layers = ZLayer.fromFunction(MonolithServer.apply)

object Main extends ZIOAppDefault:
  override def run: Task[Unit] = ZIO
    .serviceWithZIO[MonolithServer](_.start)
    .provide(
      AuthComponents.all,
      DefaultRequestContextManager.layer,
      MonolithServer.layers
    )
