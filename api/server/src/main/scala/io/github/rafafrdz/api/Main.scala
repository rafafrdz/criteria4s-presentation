package io.github.rafafrdz.api

import cats.effect._
import io.github.rafafrdz.api.ApiConf.DBConfig
import org.typelevel.log4cats.Logger

object Main extends IOApp {
  implicit lazy val conf: ApiConf = ApiConf(
    DBConfig("mongodb", "localhost", 27017, "admin", "1234") // MongoDB configuration
//    DBConfig("postgresql", "localhost", 5432, "admin", "1234", "airbnb", "org.postgresql.Driver") // JDBC-PostgreSQL configuration
  )

  def run(args: List[String]): IO[ExitCode] =
    Logger[IO].info("Starting API Server") *> ApiServer.run[IO]
}
