package io.github.rafafrdz.criteria4s.presentation.finalapproach

import io.github.rafafrdz.criteria4s.presentation.finalapproach.DSL._

object Examples {

  /** Examples */
  def expr[D <: CriteriaTag: Gt: Leq: AND: OR]: Criteria[D] =
    ((col[D]("x") :> lit(0)) and (col[D]("x") :<= lit(10))) or
      (col[D]("x") :> lit(20))

  def exprIsNull[D <: CriteriaTag: IsNull: OR: Eq]: Criteria[D] =
    col[D]("user").isNull or
      (col[D]("user") :== lit("07715cee-5d87-427d-99a7-cc03f2b5ef4a"))


  /** Impossible to implement these expressions */

  /**
    val exprString: Criteria[String] =
      ((col[String]("x") :> lit(0)) and (col[String]("x") :<= lit(10))) or
        (col[String]("x") :> lit(20))

    val exprOptionInt: Criteria[Option[Int]] =
      ((col[Option[Int]]("x") :> lit(0)) and (col[Option[Int]]("x") :<= lit(10))) or
        (col[Option[Int]]("x") :> lit(20))

    */

}
