package io.github.rafafrdz.api.datastores.criterias

import io.github.rafafrdz.criteria4s.core.ConjOp._
import io.github.rafafrdz.criteria4s.core.Criteria.CriteriaTag
import io.github.rafafrdz.criteria4s.core.PredOp._
import io.github.rafafrdz.criteria4s.core.Sym
import io.github.rafafrdz.criteria4s.instances._

import scala.util.{Failure, Success, Try}

trait MongoDB extends CriteriaTag

object MongoDB {

  val s: String      = "$"
  val prefix: String = "#C#"

  private def dropPrefix(s: String): String = s.replace(prefix, "")

  private def format(symbol: String)(left: String, right: String): String = {
    if (left.startsWith(prefix))
      s"""{ ${dropPrefix(left)}: ${innerF(symbol)(right)} }""".stripMargin
    else if (right.startsWith(prefix))
      s"""{ ${dropPrefix(right)}: ${innerF(symbol)(left)} }""".stripMargin
    else s"""{ $s$symbol: [ $left, $right ] }""".stripMargin
  }

  private def innerF(symbol: String)(left: String): String =
    s"""{ $s$symbol: $left }""".stripMargin

  implicit val ltPred: LT[MongoDB] = build[MongoDB, LT](format("lt"))

  implicit val gtPred: GT[MongoDB] = build[MongoDB, GT](format("gt"))

  implicit val orOp: OR[MongoDB] = build[MongoDB, OR](format("or"))

  implicit val andOp: AND[MongoDB] = build[MongoDB, AND](format("and"))

  implicit val eqPred: EQ[MongoDB] = build[MongoDB, EQ](format("eq"))

  implicit val neqPred: NEQ[MongoDB] = build[MongoDB, NEQ](format("ne"))

  implicit val leqPred: LEQ[MongoDB] = build[MongoDB, LEQ](format("lte"))

  implicit val geqPred: GEQ[MongoDB] = build[MongoDB, GEQ](format("gte"))

  val C: String => String = (s: String) => if (s.contains('.')) s"$prefix\"$s\"" else s"$prefix$s"
  val V: String => String = (s: String) =>
    Try(s.toDouble) match {
      case Failure(_) => s"\"$s\""
      case Success(_) => s"$s"
    }

  implicit val symRef: Sym[MongoDB] = sym[MongoDB](C, V)

}
