package db

import java.util.UUID
import zio.Task

enum DatabaseError(val msg: String):
  case ReasonUnknown(override val msg: String) extends DatabaseError(msg)
  case InvalidId(override val msg: String) extends DatabaseError(msg)
  case NotFound(override val msg: String) extends DatabaseError(msg)

enum DatabaseSuccess(val msg: String):
  case Created(override val msg: String) extends DatabaseSuccess(msg)
  case Updated(override val msg: String) extends DatabaseSuccess(msg)
  case Deleted(override val msg: String) extends DatabaseSuccess(msg)

type DatabaseResult = Either[DatabaseError, DatabaseSuccess]

trait Repository[A]:
  def add(record: A): Task[DatabaseResult]
  def getById(id: UUID): Task[Option[A]]
  def update(id: UUID, record: A): Task[DatabaseResult]
  def delete(id: UUID): Task[DatabaseResult]
