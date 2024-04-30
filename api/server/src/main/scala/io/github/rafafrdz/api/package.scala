package io.github.rafafrdz

import cats.effect._
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

package object api {

  implicit def logger[F[_]: Sync]: Logger[F] = Slf4jLogger.getLogger[F]
}
