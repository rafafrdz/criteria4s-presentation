package io.github.rafafrdz.api.services

import cats.effect.Async
import cats.implicits._
import io.github.rafafrdz.api.datastores.criterias.{MongoDB, Postgres}
import io.github.rafafrdz.api.model.Review
import io.github.rafafrdz.api.repository.ReviewRepository
import io.github.rafafrdz.criteria4s.core._
import io.github.rafafrdz.criteria4s.extensions._
import io.github.rafafrdz.criteria4s.functions._
import org.mongodb.scala.MongoClient
import org.typelevel.log4cats.Logger

import java.sql.Connection

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

  /** Generic ReviewServices implementation */
  def build[F[_]: Async: Logger, D <: CriteriaTag: EQ: GT: Sym: AND](
      rep: ReviewRepository[F]
  ): ReviewServices[F] =
    new ReviewServices[F] {

      override def getAllReviews: F[Seq[Review]] =
        Logger[F].info("Getting all reviews") *> rep.getReviews

      override def getReviewBy(owner: String): F[Seq[Review]] =
        Logger[F].info(s"Getting reviews by owner: $owner") *>
          rep.getReviewBy(ownerCriteria(owner))

      override def getReviewBy(rate: Double): F[Seq[Review]] =
        Logger[F].info(s"Getting reviews by rate: $rate") *>
          rep.getReviewBy(rateCriteria(rate))

      override def getReviewBy(owner: String, rate: Double): F[Seq[Review]] =
        Logger[F].info(s"Getting reviews by owner: $owner and rate: $rate") *>
          rep.getReviewBy(ownerCriteria(owner) and rateCriteria(rate))
    }

  /** MongoDB ReviewServices implementation */
  def using[F[_]: Async: Logger](mongo: MongoClient): ReviewServices[F] =
    build[F, MongoDB](ReviewRepository.using(mongo))

  /** JDBC-PostgreSQL ReviewServices implementation */
  def using[F[_]: Async: Logger](jdbc: Connection): ReviewServices[F] =
    build[F, Postgres](ReviewRepository.using(jdbc))

}
