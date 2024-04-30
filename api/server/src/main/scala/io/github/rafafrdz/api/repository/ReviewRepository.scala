package io.github.rafafrdz.api.repository

import cats.effect.Async
import cats.implicits._
import io.github.rafafrdz.api.model.Review
import io.github.rafafrdz.criteria4s.core._
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}

trait ReviewRepository[F[_]] {

  def getReviews: F[Seq[Review]]

  def getReviewBy[D <: CriteriaTag](criteria: Criteria[D]): F[Seq[Review]]

}

object ReviewRepository {

  def using[F[_]: Async](mongo: MongoClient): ReviewRepository[F] =
    new ReviewRepository[F] {

      def db: MongoDatabase                     = mongo.getDatabase("airbnb")
      def collection: MongoCollection[Document] = db.getCollection("reviews")

      override def getReviews: F[Seq[Review]] =
        for {
          docs <- Async[F].fromFuture(collection.find().toFuture().pure[F])
          reviews = docs.map(documentToReview)
        } yield reviews

      override def getReviewBy[D <: CriteriaTag](criteria: Criteria[D]): F[Seq[Review]] = {
        for {
          docs <- Async[F].fromFuture(
            collection.find(Document(criteria.toString())).toFuture().pure[F]
          )
          reviews = docs.map(documentToReview)
        } yield reviews
      }
    }

  /** Helper MongoDB methods */
  private def documentToReview(doc: Document): Review =
    Review(
      doc.getInteger("id"),
      doc.getString("owner"),
      doc.getString("adress"),
      doc.getDouble("rate"),
      doc.getString("review")
    )

}