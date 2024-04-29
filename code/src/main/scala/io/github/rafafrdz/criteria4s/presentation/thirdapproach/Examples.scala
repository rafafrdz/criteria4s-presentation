package io.github.rafafrdz.criteria4s.presentation.thirdapproach

import io.github.rafafrdz.criteria4s.presentation.thirdapproach.DSL._
import io.github.rafafrdz.criteria4s.presentation.thirdapproach.Interpreters.DBOptionInt._
import io.github.rafafrdz.criteria4s.presentation.thirdapproach.Interpreters.DBString._
import io.github.rafafrdz.criteria4s.presentation.thirdapproach.Interpreters._

object Examples {

  /** Examples */
  def expr[D: Gt: Leq: AND: OR]: Criteria[D] =
    ((col[D]("x") :> lit(0)) and (col[D]("x") :<= lit(10))) or
      (col[D]("x") :> lit(20))

  val expr1: Criteria[DB1] = expr[DB1]

  val expr2: Criteria[DB2] = expr[DB2]

  val exprInline: Criteria[DB1] =
    ((col[DB1]("x") :> lit(0)) and (col[DB1]("x") :<= lit(10))) or
      (col[DB1]("x") :> lit(20))

  /** With IsNull expression */

  def exprIsNull[D: IsNull: OR: Eq]: Criteria[D] =
    col[D]("user").isNull or
      (col[D]("user") :== lit("07715cee-5d87-427d-99a7-cc03f2b5ef4a"))

  val exprIsNull1: Criteria[DB1] = exprIsNull[DB1]
//  val exprIsNull2: Criteria[DB2] = exprIsNull[DB2] // This is not possible because DB2 does not have an instance for IsNull

//  val inlineExprIsNull: Criteria[DB2] =
//    col[DB2]("user").isNull or
//      (col[DB2]("user") :== lit("07715cee-5d87-427d-99a7-cc03f2b5ef4a")) // This is not possible because DB2 does not have an instance for IsNull

  /** These expressions are too confusing */
  val exprString: Criteria[String] =
    ((col[String]("x") :> lit(0)) and (col[String]("x") :<= lit(10))) or
      (col[String]("x") :> lit(20))

  val exprOptionInt: Criteria[Option[Int]] =
    ((col[Option[Int]]("x") :> lit(0)) and (col[Option[Int]]("x") :<= lit(10))) or
      (col[Option[Int]]("x") :> lit(20))

}
