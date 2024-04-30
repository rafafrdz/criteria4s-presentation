package io.github.rafafrdz.api

import cats.effect.Async
import cats.syntax.all._
import com.comcast.ip4s._
import io.github.rafafrdz.api.repository.DB
import io.github.rafafrdz.api.services.ReviewServices
import org.http4s.HttpApp
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.Logger
import org.mongodb.scala.MongoClient

object ApiServer {

  def run[F[_]: Async](implicit conf: ApiConf): F[Nothing] = {
    val client                            = DB[MongoClient]
    val reviewServices: ReviewServices[F] = ReviewServices.using[F](client)
    val httpApp: HttpApp[F] = (
      ApiRoutes.pingRoutes[F] <+>
        ApiRoutes.reviewRoutes[F](reviewServices)
    ).orNotFound

    val finalHttpApp: HttpApp[F] =
      Logger.httpApp(logHeaders = true, logBody = true)(httpApp)
    for {
      _ <-
        EmberServerBuilder
          .default[F]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"4321")
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever
}
