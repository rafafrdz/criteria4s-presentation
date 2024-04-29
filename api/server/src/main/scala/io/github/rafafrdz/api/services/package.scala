package io.github.rafafrdz.api

import org.http4s.dsl.impl.{OptionalQueryParamDecoderMatcher, QueryParamDecoderMatcher}

import scala.util.Try

package object services {

  object IDVar {
    def unapply(string: String): Option[Int] =
      if (string.nonEmpty) Try(string.toInt).toOption
      else Option.empty
  }

  object DNIVar extends QueryParamDecoderMatcher[Int]("dni"){
    def check(string: String): Option[Int] =
      if (string.nonEmpty && string.length<=9) Try(string.toInt).toOption
      else Option.empty

    override def unapply(params: Map[String, collection.Seq[String]]): Option[Int] =
      for {
        dniOpt <- super.unapply(params)
        checkedDNIOption <- check(dniOpt.toString)
      } yield checkedDNIOption
  }

  object UserNameVar extends OptionalQueryParamDecoderMatcher[String]("name")
}
