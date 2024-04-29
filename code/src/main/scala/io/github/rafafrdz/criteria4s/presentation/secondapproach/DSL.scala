package io.github.rafafrdz.criteria4s.presentation.secondapproach

object DSL {

  sealed trait Criteria
  sealed trait Conjunction extends Criteria
  sealed trait Predicate   extends Criteria

  sealed trait Reference
  case class Column(name: String)                   extends Reference
  sealed trait Value[T]                             extends Reference
  case class Literal[T](value: T)                   extends Value[T]
  case object True                                  extends Reference with Value[Boolean]
  case object False                                 extends Reference with Value[Boolean]
  case class And(left: Criteria, right: Criteria)   extends Conjunction
  case class Or(left: Criteria, right: Criteria)    extends Conjunction
  case class Leq(left: Reference, right: Reference) extends Predicate
  case class Gt(left: Reference, right: Reference)  extends Predicate
  case class Eq(left: Reference, right: Reference)  extends Predicate
  case class IsNull(ref: Reference)                 extends Predicate

}
