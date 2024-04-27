package io.github.rafafrdz.criteria4s.presentation.firstapproach

import io.github.rafafrdz.criteria4s.presentation.firstapproach.DSL.Criteria

object Interpreters {

  object DB1 extends Criteria {
    def and(x: String, y: String): String = s"($x AND $y)"
    def or(x: String, y: String): String  = s"($x OR $y)"
    def leq(x: String, y: String): String = s"$x <= $y"
    def gt(x: String, y: String): String  = s"$x > $y"
  }

  object DB2 extends Criteria {
    def and(x: String, y: String): String = s"{ ope: `AND`, left: $x, right: $y}"
    def or(x: String, y: String): String  = s"{ ope: `OR`, left: $x, right: $y}"
    def leq(x: String, y: String): String = s"{ ope: `leq`, left: $x, right: $y}"
    def gt(x: String, y: String): String  = s"{ ope: `gt`, left: $x, right: $y}"
  }

}
