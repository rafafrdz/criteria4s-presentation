package io.github.rafafrdz.api.middleware

import cats.effect.Async
import cats.syntax.applicative._
import io.github.rafafrdz.api.ApiServer
import org.http4s.HttpApp
import org.slf4j.Logger

object LoggerM {

  private val logger: Logger = org.slf4j.LoggerFactory.getLogger(ApiServer.getClass)

  private def generateLogAction[F[_]: Async](showLog: Boolean): String => F[Unit] =
    if (showLog) { (str: String) =>
      logger.info(str).pure[F]
    } else { (_: String) =>
      ().pure[F]
    }

  def impl[F[_]: Async](
      showDetailedLogs: Boolean,
      logHeaders: Boolean = true,
      logBodys: Boolean = true
  )(httpApp: HttpApp[F]): HttpApp[F] =
    org.http4s.server.middleware.Logger.httpApp[F](
      logHeaders,
      logBodys,
      logAction = Some(generateLogAction(showDetailedLogs))
    )(httpApp)

}
