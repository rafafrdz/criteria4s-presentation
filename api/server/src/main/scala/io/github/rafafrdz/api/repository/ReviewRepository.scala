package io.github.rafafrdz.api.repository

import cats.effect.Async
import cats.effect.kernel.Resource
import cats.implicits._
import io.github.rafafrdz.api.model.Review
import io.github.rafafrdz.criteria4s.core._
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.typelevel.log4cats.Logger

import java.sql.{Connection, ResultSet}
import scala.util.Try

trait ReviewRepository[F[_]] {

  def getReviews: F[Seq[Review]]

  def getReviewBy[D <: CriteriaTag](criteria: Criteria[D]): F[Seq[Review]]

}

object ReviewRepository {

  private val Airbnb: String  = "airbnb"
  private val Reviews: String = "reviews"

  /** MongoDB ReviewRepository implementation */
  def using[F[_]: Async: Logger](mongo: MongoClient): ReviewRepository[F] =
    new ReviewRepository[F] {

      def db: MongoDatabase                     = mongo.getDatabase(Airbnb)
      def collection: MongoCollection[Document] = db.getCollection(Reviews)

      override def getReviews: F[Seq[Review]] =
        for {
          docs <- Async[F].fromFuture(collection.find().toFuture().pure[F])
          reviews = docs.map(documentToReview)
        } yield reviews

      override def getReviewBy[D <: CriteriaTag](criteria: Criteria[D]): F[Seq[Review]] =
        for {
          _ <- Logger[F].info(s"Searching $Reviews by criteria: $criteria")
          docs <- Async[F].fromFuture(
            collection.find(Document(criteria.toString())).toFuture().pure[F]
          )
          reviews = docs.map(documentToReview)
        } yield reviews
    }

  /** JDBC ReviewRepository implementation */
  def using[F[_]: Async: Logger](jdbc: Connection): ReviewRepository[F] =
    new ReviewRepository[F] {

      override def getReviews: F[Seq[Review]] =
        execute(jdbc, s"SELECT * FROM $Airbnb.$Reviews") { rs =>
          resultSetToReview(rs)
        }

      override def getReviewBy[D <: CriteriaTag](criteria: Criteria[D]): F[Seq[Review]] =
        Logger[F].info(s"Searching $Reviews by criteria: $criteria") *>
          execute(jdbc, s"SELECT * FROM $Airbnb.$Reviews WHERE $criteria") { rs =>
            resultSetToReview(rs)
          }
    }

  /** Helper MongoDB methods */
  private def getAsDouble(key: String, doc: Document): Double =
    Try(doc.getDouble(key).toDouble).getOrElse(doc.getInteger(key).toDouble)

  private def documentToReview(doc: Document): Review =
    Review(
      doc.getInteger("_id"),
      doc.getString("owner"),
      doc.getString("address"),
      getAsDouble("rate", doc),
      doc.getString("comment")
    )

  /** Helper JDBC methods */
  private def execute[F[_]: Async, T](conn: Connection, sql: String)(
      F: ResultSet => T
  ): F[Seq[T]] =
    Resource.make(Async[F].delay(conn.createStatement()))(s => Async[F].delay(s.close())).use {
      stmt =>
        stmt
          .executeQuery(sql)
          .pure[F]
          .map { rs =>
            Iterator
              .continually(rs)
              .takeWhile(_.next())
              .map(F)
              .toSeq
          }
    }

  private def resultSetToReview(rs: ResultSet): Review =
    Review(
      rs.getInt("id"),
      rs.getString("owner"),
      rs.getString("address"),
      rs.getDouble("rate"),
      rs.getString("comment")
    )
}
