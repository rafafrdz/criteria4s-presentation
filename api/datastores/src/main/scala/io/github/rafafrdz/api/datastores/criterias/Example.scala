package io.github.rafafrdz.api.datastores.criterias

import io.github.rafafrdz.criteria4s.core.Criteria.CriteriaTag
import io.github.rafafrdz.criteria4s.core._
import io.github.rafafrdz.criteria4s.extensions._
import io.github.rafafrdz.criteria4s.functions._

object Example extends App {

  def expr[T <: CriteriaTag: EQ: Sym](fieldName: String, id: String): Criteria[T] =
    col[T](fieldName) === lit(id.toString)

  def expr2[T <: CriteriaTag: EQ: Sym: OR](fieldName: String, name: String*): Criteria[T] =
    name.map(n => col[T](fieldName) === lit(n)).reduce(_ or _)

  def rateFilter[TT <: CriteriaTag: GEQ: Sym: LEQ: OR](
      fieldName: String,
      rate: Double
  ): Criteria[TT] =
    (col[TT](fieldName) geq lit(rate)) or (col[TT](fieldName) leq lit(1.0))

  val mongo    = rateFilter[MongoDB]("rate", 5)
  val postgres = rateFilter[Postgres]("rate", 5)

  println(mongo)
  println(postgres)

}
