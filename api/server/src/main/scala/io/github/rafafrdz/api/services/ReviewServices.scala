package io.github.rafafrdz.api.services

import cats.effect.Async
import io.github.rafafrdz.api.datastores.criterias.MongoDB
import io.github.rafafrdz.api.model.Review
import io.github.rafafrdz.api.repository.ReviewRepository
import io.github.rafafrdz.criteria4s.core._
import io.github.rafafrdz.criteria4s.extensions._
import io.github.rafafrdz.criteria4s.functions._
import org.mongodb.scala.MongoClient
import org.typelevel.log4cats.Logger

trait ReviewServices[F[_]] {

  def getAllReviews: F[Seq[Review]]

  def getReviewBy(owner: String): F[Seq[Review]]

  def getReviewBy(rate: Double): F[Seq[Review]]

  def getReviewBy(owner: String, rate: Double): F[Seq[Review]]

}

object ReviewServices {

  private def ownerCriteria[D <: CriteriaTag: EQ: Sym](owner: String): Criteria[D] =
    col[D]("owner") === lit(owner)

  private def rateCriteria[D <: CriteriaTag: GT: Sym](rate: Double): Criteria[D] =
    col[D]("rate") gt lit(rate)

  def build[F[_] : Logger, D <: CriteriaTag: EQ: GT: Sym: AND](rep: ReviewRepository[F]): ReviewServices[F] =
    new ReviewServices[F] {

      override def getAllReviews: F[Seq[Review]] =
        rep.getReviews

      override def getReviewBy(owner: String): F[Seq[Review]] =
        rep.getReviewBy(ownerCriteria(owner))

      override def getReviewBy(rate: Double): F[Seq[Review]] =
        rep.getReviewBy(rateCriteria(rate))

      override def getReviewBy(owner: String, rate: Double): F[Seq[Review]] =
        rep.getReviewBy(ownerCriteria(owner) and rateCriteria(rate))
    }

  def using[F[_]: Async : Logger](mongo: MongoClient): ReviewServices[F] =
    build[F, MongoDB](ReviewRepository.using(mongo))

}
