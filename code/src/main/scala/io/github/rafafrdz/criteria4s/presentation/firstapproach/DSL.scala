package io.github.rafafrdz.criteria4s.presentation.firstapproach

object DSL {

  trait Criteria {
    def and(x: String, y: String): String
    def or(x: String, y: String): String
    def leq(x: String, y: String): String
    def gt(x: String, y: String): String
  }

  object Criteria {
    def and(x: String, y: String): String = s"($x AND $y)"
    def or(x: String, y: String): String  = s"($x OR $y)"
    def leq(x: String, y: String): String = s"$x <= $y"
    def gt(x: String, y: String): String  = s"$x > $y"
  }

}
