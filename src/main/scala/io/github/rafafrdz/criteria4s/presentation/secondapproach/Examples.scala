package io.github.rafafrdz.criteria4s.presentation.secondapproach

import io.github.rafafrdz.criteria4s.presentation.secondapproach.DSL._
import io.github.rafafrdz.criteria4s.presentation.secondapproach.Interpreters._

object Examples extends App {

  val expr: Criteria =
    Or(
      And(
        Gt(Column("x"), Literal(0)),
        Leq(Column("x"), Literal(10))
      ),
      Gt(Column("x"), Literal(20))
    )

  val expr2: Criteria =
    Or(
      And(
        Gt(Column("x"), Literal(0)),
        Leq(Column("x"), Literal(10))
      ),
      Eq(Column("user"), Literal("07715cee-5d87-427d-99a7-cc03f2b5ef4a"))
    )

  val expr3: Criteria =
    And(
      IsNull(Column("user")),
      Eq(Column("user"), Literal("07715cee-5d87-427d-99a7-cc03f2b5ef4a"))
    )

  /** Expr 1 */
  DB1.eval(expr)
  // ((`x` > '0' AND `x` <= '10') OR `x` > '20')

  DB2.eval(expr)
  // { ope: `OR`, left: { ope: `AND`, left: { ope: `gt`, left: `x`, right: '0'}, right: { ope: `leq`, left: `x`, right: '10'}}, right: { ope: `gt`, left: `x`, right: '20'}}

  /** Expr 2 */
  DB1.eval(expr2)
  // ((`x` > '0' AND `x` <= '10') OR `user` = '07715cee-5d87-427d-99a7-cc03f2b5ef4a')

  DB2.eval(expr2)
  /**
   * { ope: `OR`, left: { ope: `AND`, left: { ope: `gt`, left: `x`, right: '0'}, right: { ope:
   * `leq`, left: `x`, right: '10'}}, right: { ope: `eq`, left: `user`, right:
   * '07715cee-5d87-427d-99a7-cc03f2b5ef4a'}}
   */

  /** Expr 3 */
  DB1.eval(expr3)
  // (`user` IS NULL AND `user` = '07715cee-5d87-427d-99a7-cc03f2b5ef4a')

  DB2.eval(expr3)
  // This will fail because IsNull is not implemented in DB2 interpreter!

}
