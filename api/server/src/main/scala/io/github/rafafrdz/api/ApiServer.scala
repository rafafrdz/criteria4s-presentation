package io.github.rafafrdz.api

import cats.effect.kernel.Resource
import cats.effect.{Async, ExitCode}
import cats.syntax.all._
import com.comcast.ip4s._
import io.github.rafafrdz.api.middleware.LoggerM
import io.github.rafafrdz.api.repository.DB
import io.github.rafafrdz.api.services.ReviewServices
import org.http4s.HttpApp
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.Server

object ApiServer {

  def build[F[_]: Async](implicit conf: ApiConf): Resource[F, Server] = {
//    val client                            = DB[org.mongodb.scala.MongoClient] // Using MongoDB client
    val client                            = DB[java.sql.Connection] // Using JDBC client
    val reviewServices: ReviewServices[F] = ReviewServices.using[F](client)
    val httpApp: HttpApp[F] = (
      ApiRoutes.pingRoutes[F] <+>
        ApiRoutes.reviewRoutes[F](reviewServices)
    ).orNotFound

    val finalHttpApp: HttpApp[F] =
      LoggerM.impl[F](showDetailedLogs = false, logHeaders = false, logBodys = false)(httpApp)
    for {
      server <-
        EmberServerBuilder
          .default[F]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"4321")
          .withHttpApp(finalHttpApp)
          .build
    } yield server
  }

  def run[F[_]: Async](implicit conf: ApiConf): F[ExitCode] =
    build.use(_ => Async[F].never.as(ExitCode.Success))
}
