package io.github.rafafrdz.api.services

import io.github.rafafrdz.api.model.User
import io.github.rafafrdz.api.repository.UserRepository

trait UserService[F[_]] {

  def getUserById(id: Int): F[User]

  def getUserByNameDNI(name: String, dni: Int): F[User]

}

object UserService {

  def build[F[_]](rep: UserRepository[F]): UserService[F] =
    new UserService[F] {


    def getUserBy(id: Int): F[User] = rep.getUserBy(UserRepository.Id(id))

    def getUserBy(name: String, dni: Int): F[User] = rep.getUserBy(UserRepository.NameDni(name, dni))

  }

}
