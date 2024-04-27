package io.github.rafafrdz.criteria4s.presentation.thirdapproach

object DSL {

  trait Criteria[D] {
    def value: String
  }

  object Criteria {
    def pure[D](v: String): Criteria[D] = new Criteria[D] {
      override def value: String = v
    }
  }

  trait Conjuction[D] {
    def eval(cr1: Criteria[D], cr2: Criteria[D]): Criteria[D]
  }

  trait OR[D] extends Conjuction[D]

  trait AND[D] extends Conjuction[D]

  trait Predicate[D]

  trait PredicateBinary[D] extends Predicate[D] {
    def eval(cr1: Reference[D], cr2: Reference[D]): Criteria[D]
  }

  trait PredicateUnary[D] extends Predicate[D] {
    def eval(cr: Reference[D]): Criteria[D]
  }

  trait Gt[D] extends PredicateBinary[D]

  trait Leq[D] extends PredicateBinary[D]

  trait Eq[D] extends PredicateBinary[D]

  trait IsNull[D] extends PredicateUnary[D]

  trait Reference[D] {
    def value: String
  }

  trait Column[D] extends Reference[D]

  trait Value[D, V] extends Reference[D]

  implicit class ImplicitCriteriaOps[D](cr: Criteria[D]) {
    def or(cr2: Criteria[D])(implicit ev: OR[D]): Criteria[D] = ev.eval(cr, cr2)

    def and(cr2: Criteria[D])(implicit ev: AND[D]): Criteria[D] = ev.eval(cr, cr2)
  }

  implicit class ImplicitReferenceOps[D](ref: Reference[D]) {
    def gt(ref2: Reference[D])(implicit ev: Gt[D]): Criteria[D] = ev.eval(ref, ref2)

    def :>(ref2: Reference[D])(implicit ev: Gt[D]): Criteria[D] = ev.eval(ref, ref2)

    def leq(ref2: Reference[D])(implicit ev: Leq[D]): Criteria[D] = ev.eval(ref, ref2)

    def :<=(ref2: Reference[D])(implicit ev: Leq[D]): Criteria[D] = ev.eval(ref, ref2)

    def eq(ref2: Reference[D])(implicit ev: Eq[D]): Criteria[D] = ev.eval(ref, ref2)

    def :==(ref2: Reference[D])(implicit ev: Eq[D]): Criteria[D] = ev.eval(ref, ref2)

    def isNull(implicit ev: IsNull[D]): Criteria[D] = ev.eval(ref)
  }

  def col[D](ref: String): Column[D] = new Column[D] {
    override def value: String = s"`$ref`"
  }

  def lit[D, V](v: V): Value[D, V] = new Value[D, V] {
    override def value: String = s"'$v'"
  }



}
