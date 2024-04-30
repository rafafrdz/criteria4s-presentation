package io.github.rafafrdz.api.datastores.data

import org.mongodb.scala.bson.Document
import org.mongodb.scala.result.InsertManyResult
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object MongoDBData extends App {

  implicit val ec: ExecutionContext = ExecutionContext.global

  // Replace the placeholder with your Atlas connection string
  val connectionString = "mongodb://admin:1234@localhost:27017"

  val client: MongoClient    = getConnection(connectionString)
  val dbName: String         = "airbnb"
  val collectionName: String = "reviews"
  val count: Long            = setUpData(client, dbName, collectionName)

  println(count)

  def getConnection(connectionString: String): MongoClient = {
    val mongoClient: MongoClient = MongoClient(connectionString)
    Try {
      Await.result(mongoClient.listDatabaseNames().toFuture(), Duration.Inf)
    } match {
      case Success(_)            => mongoClient
      case Failure(e: Throwable) => throw e
    }
  }

  def setUpData(
      client: MongoClient,
      dbName: String,
      collectionName: String,
      overwrite: Boolean = true
  )(implicit
      ec: ExecutionContext
  ): Long = {
    val db: MongoDatabase = client.getDatabase(dbName)
    val exec: Future[Long] = for {
      _ <- if (overwrite) removeData(db) else Future.successful(Seq.empty)
      _ <- db.createCollection(collectionName).toFuture()
      collection = db.getCollection(collectionName)
      _     <- insertDocuments(collection, getData(dbName, collectionName))
      count <- collection.countDocuments().toFuture()
      _ = println(s"Inserted $count documents in $dbName.$collectionName")
    } yield count

    Await.result(exec, Duration.Inf)
  }

  def insertDocuments(
      collection: MongoCollection[Document],
      documents: List[Document]
  ): Future[InsertManyResult] =
    collection.insertMany(documents).toFuture()

  def removeData(db: MongoDatabase)(implicit ec: ExecutionContext): Future[Seq[Unit]] = {
    for {
      collections <- db.listCollectionNames().toFuture()
      remove <- Future.sequence(collections.map(col => db.getCollection(col).drop().toFuture()))
    } yield remove
  }

  def getData(db: String, collection: String): List[Document] = {
    val source = scala.io.Source.fromResource(s"data/mongo/$db/$collection.json")
    val lines  = source.getLines().toList
    source.close()
    lines.map(Document(_))
  }

}
