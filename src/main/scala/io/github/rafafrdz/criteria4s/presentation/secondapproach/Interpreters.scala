package io.github.rafafrdz.criteria4s.presentation.secondapproach

import io.github.rafafrdz.criteria4s.presentation.secondapproach.DSL._

object Interpreters {

  /** DB1 */
  object DB1 {
    private def evalR(r: Reference): String = r match {
      case Column(name) => s"`$name`"
      case Literal(v)   => s"'$v'"
      case True         => "true"
      case False        => "false"
    }

    def eval(c: Criteria): String = c match {
      case And(l, r) => s"(${eval(l)} AND ${eval(r)})"
      case Or(l, r)  => s"(${eval(l)} OR ${eval(r)})"
      case Leq(l, r) => s"${evalR(l)} <= ${evalR(r)}"
      case Gt(l, r)  => s"${evalR(l)} > ${evalR(r)}"
      case Eq(l, r)  => s"${evalR(l)} = ${evalR(r)}"
      case IsNull(r) => s"${evalR(r)} IS NULL"
    }

  }

  /** DB1 */
  object DB2 {
    private def evalR(r: Reference): String = r match {
      case Column(name) => s"`$name`"
      case Literal(v)   => s"'$v'"
      case True         => "true"
      case False        => "false"
    }

    def eval(c: Criteria): String = c match {
      case And(l, r) => s"{ ope: `AND`, left: ${eval(l)}, right: ${eval(r)}}"
      case Or(l, r)  => s"{ ope: `OR`, left: ${eval(l)}, right: ${eval(r)}}"
      case Leq(l, r) => s"{ ope: `leq`, left: ${evalR(l)}, right: ${evalR(r)}}"
      case Gt(l, r)  => s"{ ope: `gt`, left: ${evalR(l)}, right: ${evalR(r)}}"
      case Eq(l, r)  => s"{ ope: `eq`, left: ${evalR(l)}, right: ${evalR(r)}}"
    }

  }

}
