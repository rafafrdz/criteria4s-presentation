package io.github.rafafrdz.api

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    ApiServer.run[IO] *> IO(ExitCode.Success)
}
