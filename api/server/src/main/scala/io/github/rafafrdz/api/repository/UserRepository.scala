package io.github.rafafrdz.api.repository

trait UserRepository[F[_]] {

  def getUserBy(criteria: Criteria): F[User]

}
