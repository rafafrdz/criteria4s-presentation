package io.github.rafafrdz.criteria4s.presentation.firstapproach

object Examples extends App {

  import Interpreters._

  val exprDB1: String =
    DB1.or(DB1.and(DB1.gt("x", "0"), DB1.leq("x", "10")), DB1.gt("x", "20"))

  val exprDB2: String =
    DB2.or(DB2.and(DB2.gt("x", "0"), DB2.leq("x", "10")), DB2.gt("x", "20"))

  val expr2: String =
    DB1.or(
      DB1.and(
        DB1.gt("x > 0", "x <= 10"),
        DB1.leq(
          "Lorem ipsum dolor sit amet...",
          "En un lugar de la Mancha..."
        )
      ),
      "x + & * //"
    ) // ((x > 0 > x <= 10 AND Lorem ipsum dolor sit amet... <= En un lugar de la Mancha...) OR x + & * //)

}
