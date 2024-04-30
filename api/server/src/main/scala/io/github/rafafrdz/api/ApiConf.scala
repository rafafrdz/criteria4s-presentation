package io.github.rafafrdz.api

import io.github.rafafrdz.api.ApiConf.DBConfig

case class ApiConf(db: DBConfig)

object ApiConf {

  case class DBConfig(host: String, port: Int, user: String, password: String)
}
