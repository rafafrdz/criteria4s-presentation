package io.github.rafafrdz.api

import org.http4s.dsl.impl.{OptionalQueryParamDecoderMatcher, QueryParamDecoderMatcher}

package object services {

  object RateVar extends OptionalQueryParamDecoderMatcher[Double]("rate")
  object OwnerVar extends OptionalQueryParamDecoderMatcher[String]("owner")
}
