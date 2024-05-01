package io.github.rafafrdz.api.repository

import io.github.rafafrdz.api.ApiConf
import org.mongodb.scala.MongoClient

import java.sql.Connection

trait DB[T] {
  def client: T
}

object DB {

  def apply[T](implicit db: DB[T]): T = db.client

  implicit def mongoDB(implicit conf: ApiConf): DB[MongoClient] = new DB[MongoClient] {
    override def client: MongoClient = MongoClient(
      s"${conf.db.prefix}://${conf.db.user}:${conf.db.password}@${conf.db.host}:${conf.db.port}"
    )
  }

  implicit def jdbcDB(implicit conf: ApiConf): DB[Connection] = new DB[Connection] {
    override def client: Connection = {
      Class.forName(conf.db.driver)
      java.sql.DriverManager.getConnection(
        s"jdbc:${conf.db.prefix}://${conf.db.host}:${conf.db.port}/${conf.db.database}",
        conf.db.user,
        conf.db.password
      )
    }
  }

}
