package io.github.rafafrdz.api

import cats.effect.Sync
import cats.implicits._
import io.github.rafafrdz.api.services._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object ApiRoutes {

  def jokeRoutes[F[_]: Sync](J: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "joke" =>
        for {
          joke <- J.get
          resp <- Ok(joke)
        } yield resp
    }
  }

  def userRoutes[F[_]: Sync](U: UserService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "user" / IDVar(id) =>
        for {
          user <- U.getUserById(id)
          resp <- Ok(user)
        } yield resp

      case GET -> Root / "user" :? UserNameVar(optName) :? DNIVar(dni) =>
        for {
          name <- optName
          user <- U.getUserByNameDNI(name, dni)
          resp <- Ok(user)
        } yield resp
    }
  }

  def helloWorldRoutes[F[_]: Sync](H: HelloWorld[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        for {
          greeting <- H.hello(HelloWorld.Name(name))
          resp <- Ok(greeting)
        } yield resp
    }
  }
}