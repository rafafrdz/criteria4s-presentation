package io.github.rafafrdz.api.repository

import io.github.rafafrdz.api.ApiConf
import org.mongodb.scala.MongoClient

trait DB[T] {

  def client: T

}

object DB {

  def apply[T](implicit db: DB[T]): T = db.client

  implicit def mongoDB(implicit conf: ApiConf): DB[MongoClient] = new DB[MongoClient] {
    override def client: MongoClient = MongoClient(
      s"mongodb://${conf.db.user}:${conf.db.password}@${conf.db.host}:${conf.db.port}"
    )
  }

}
