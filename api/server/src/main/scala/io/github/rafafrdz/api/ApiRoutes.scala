package io.github.rafafrdz.api

import cats.effect.Sync
import cats.implicits._
import io.github.rafafrdz.api.model.Feedback
import io.github.rafafrdz.api.services._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.Http4sDsl

object ApiRoutes {

  private val ReviewsPath: String = "reviews"
  private val PingPath: String    = "ping"

  def reviewRoutes[F[_]: Sync](R: ReviewServices[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / ReviewsPath :? RateVar(rate) :? OwnerVar(optName) =>
      lazy val reviews = (rate, optName) match {
        case (Some(r), Some(n)) => R.getReviewBy(n, r)
        case (Some(r), None)    => R.getReviewBy(r)
        case (None, Some(n))    => R.getReviewBy(n)
        case _                  => R.getAllReviews
      }

      for {
        reviews <- reviews
        resp    <- Ok(Feedback(reviews))
      } yield resp
    }
  }

  def pingRoutes[F[_]: Sync]: HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / PingPath =>
      Ok("pong")
    }
  }
}
