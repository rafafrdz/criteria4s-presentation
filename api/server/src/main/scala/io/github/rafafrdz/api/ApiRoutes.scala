package io.github.rafafrdz.api

import cats.effect.Sync
import cats.implicits._
import io.github.rafafrdz.api.model.Feedback
import io.github.rafafrdz.api.services._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.Http4sDsl

object ApiRoutes {

  def reviewRoutes[F[_]: Sync](R: ReviewServices[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {

      case GET -> Root / "reviews" =>
        for {
          reviews <- R.getAllReviews
          resp    <- Ok(Feedback(reviews))
        } yield resp

      case GET -> Root / "reviews" :? RateVar(rate) :? OwnerVar(optName) =>
        for {
          reviews <- optName.fold(R.getReviewBy(rate))(name => R.getReviewBy(name, rate))
          resp    <- Ok(Feedback(reviews))
        } yield resp
    }
  }

  def pingRoutes[F[_]: Sync]: HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / "ping" =>
      Ok("pong")
    }
  }
}
