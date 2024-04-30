package io.github.rafafrdz.api

import org.http4s.dsl.impl.{OptionalQueryParamDecoderMatcher, QueryParamDecoderMatcher}

package object services {

  object RateVar extends QueryParamDecoderMatcher[Double]("rate") {
    def check(string: String): Option[Double] =
      string.toDoubleOption.filterNot(d => 0.0 <= d || d <= 5.0)

    override def unapply(params: Map[String, collection.Seq[String]]): Option[Double] =
      for {
        rateOpt   <- super.unapply(params)
        validRate <- check(rateOpt.toString)
      } yield validRate
  }

  object OwnerVar extends OptionalQueryParamDecoderMatcher[String]("owner")
}
