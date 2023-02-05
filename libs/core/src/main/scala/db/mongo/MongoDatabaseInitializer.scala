package db.mongo

import zio.ZIO
import zio.UIO
import zio.URLayer
import zio.ZLayer
import org.mongodb.scala.*
import java.util.concurrent.TimeUnit

final case class MongoDatabaseInitializer(dataSource: DataSource) extends DatabaseInitializer {

  private val mongoServerSettings = ZIO.succeed {
    MongoClientSettings.builder()
      .applyToClusterSettings(_.serverSelectionTimeout(5, TimeUnit.SECONDS))
      .build()
  }

  override def initialize(dbConfig: DatabaseConfig): UIO[Unit] =
    (for
      settings <- zio.Console.printLine(s"Attempting to establish the connection with MongoDB on port: ${dbConfig.port} with db ${dbConfig.name}") *> mongoServerSettings
      client   <- ZIO.attempt(MongoClient(settings))
      db       <- ZIO.attempt(client.getDatabase(dbConfig.name))
      _        <- ZIO.fromFuture(implicit ec => db.listCollectionNames.toFuture).catchSome {
        case mte: MongoTimeoutException => ZIO.fail(RuntimeException(s"Connecting to MongoDB failed, reason: ${mte.getMessage}"))
        case _ => ZIO.fail(RuntimeException("Connecting to MongoDB failed, reason unknown"))
      } *> dataSource.setCtx(DatabaseContext(db))
    yield ()).orDie
}

object MongoDatabaseInitializer {
  lazy val layer: URLayer[DataSource, DatabaseInitializer] = ZLayer.fromFunction(MongoDatabaseInitializer.apply) 
}
