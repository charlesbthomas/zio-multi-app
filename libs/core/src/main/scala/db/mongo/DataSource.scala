package db.mongo

import org.mongodb.scala.MongoDatabase
import zio.UIO
import zio.ZIO
import zio.RIO

final case class DatabaseConfig(port: String, name: String)

final case class DatabaseContext(mongoDatabase: Option[MongoDatabase])
object DatabaseContext {
  def initial: DatabaseContext = new DatabaseContext(None)
  def apply(mongoDatabase: MongoDatabase): DatabaseContext =
    new DatabaseContext(Some(mongoDatabase))
}

trait DataSource {
  def setCtx(ctx: DatabaseContext): UIO[Unit]
  def getCtx: UIO[DatabaseContext]
}

trait DatabaseInitializer {
  def initialize(config: DatabaseConfig): RIO[DataSource, Unit]
}
