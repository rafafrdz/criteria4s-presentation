package io.github.rafafrdz.criteria4s.presentation.finalapproach

object DSL {

  trait CriteriaTag

  trait Criteria[D <: CriteriaTag] {
    def value: String
  }

  object Criteria {
    def pure[D <: CriteriaTag](v: String): Criteria[D] = new Criteria[D] {
      override def value: String = v
    }
  }

  trait Conjuction[D <: CriteriaTag] {
    def eval(cr1: Criteria[D], cr2: Criteria[D]): Criteria[D]
  }

  trait OR[D <: CriteriaTag] extends Conjuction[D]

  trait AND[D <: CriteriaTag] extends Conjuction[D]

  trait Predicate[D <: CriteriaTag]

  trait PredicateBinary[D <: CriteriaTag] extends Predicate[D] {
    def eval(cr1: Reference[D], cr2: Reference[D]): Criteria[D]
  }

  trait PredicateUnary[D <: CriteriaTag] extends Predicate[D] {
    def eval(cr: Reference[D]): Criteria[D]
  }

  trait Gt[D <: CriteriaTag] extends PredicateBinary[D]

  trait Leq[D <: CriteriaTag] extends PredicateBinary[D]

  trait Eq[D <: CriteriaTag] extends PredicateBinary[D]

  trait IsNull[D <: CriteriaTag] extends PredicateUnary[D]

  trait Reference[D <: CriteriaTag] {
    def value: String
  }

  trait Column[D <: CriteriaTag] extends Reference[D]

  trait Value[D <: CriteriaTag, V] extends Reference[D]

  implicit class ImplicitCriteriaOps[D <: CriteriaTag](cr: Criteria[D]) {
    def or(cr2: Criteria[D])(implicit ev: OR[D]): Criteria[D] = ev.eval(cr, cr2)

    def and(cr2: Criteria[D])(implicit ev: AND[D]): Criteria[D] = ev.eval(cr, cr2)
  }

  implicit class ImplicitReferenceOps[D <: CriteriaTag](ref: Reference[D]) {
    def gt(ref2: Reference[D])(implicit ev: Gt[D]): Criteria[D] = ev.eval(ref, ref2)

    def :>(ref2: Reference[D])(implicit ev: Gt[D]): Criteria[D] = ev.eval(ref, ref2)

    def leq(ref2: Reference[D])(implicit ev: Leq[D]): Criteria[D] = ev.eval(ref, ref2)

    def :<=(ref2: Reference[D])(implicit ev: Leq[D]): Criteria[D] = ev.eval(ref, ref2)

    def eq(ref2: Reference[D])(implicit ev: Eq[D]): Criteria[D] = ev.eval(ref, ref2)

    def :==(ref2: Reference[D])(implicit ev: Eq[D]): Criteria[D] = ev.eval(ref, ref2)

    def isNull(implicit ev: IsNull[D]): Criteria[D] = ev.eval(ref)
  }

  def col[D <: CriteriaTag](ref: String): Column[D] = new Column[D] {
    override def value: String = s"`$ref`"
  }

  def lit[D <: CriteriaTag, V](v: V): Value[D, V] = new Value[D, V] {
    override def value: String = s"'$v'"
  }

}
