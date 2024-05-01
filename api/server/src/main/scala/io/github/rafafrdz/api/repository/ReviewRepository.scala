package io.github.rafafrdz.api.repository

import cats.effect.Async
import cats.implicits._
import io.github.rafafrdz.api.model.Review
import io.github.rafafrdz.criteria4s.core._
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.typelevel.log4cats.Logger

import scala.util.Try

trait ReviewRepository[F[_]] {

  def getReviews: F[Seq[Review]]

  def getReviewBy[D <: CriteriaTag](criteria: Criteria[D]): F[Seq[Review]]

}

object ReviewRepository {

  def using[F[_]: Async: Logger](mongo: MongoClient): ReviewRepository[F] =
    new ReviewRepository[F] {

      def db: MongoDatabase                     = mongo.getDatabase("airbnb")
      def collection: MongoCollection[Document] = db.getCollection("reviews")

      override def getReviews: F[Seq[Review]] =
        for {
          docs <- Async[F].fromFuture(collection.find().toFuture().pure[F])
          reviews = docs.map(documentToReview)
        } yield reviews

      override def getReviewBy[D <: CriteriaTag](criteria: Criteria[D]): F[Seq[Review]] =
        for {
          _ <- Logger[F].info(s"Searching reviews by criteria: $criteria")
          docs <- Async[F].fromFuture(
            collection.find(Document(criteria.toString())).toFuture().pure[F]
          )
          reviews = docs.map(documentToReview)
        } yield reviews
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

}
