package io.github.rafafrdz.criteria4s.presentation.thirdapproach

import io.github.rafafrdz.criteria4s.presentation.thirdapproach.DSL._

object Interpreters {


  trait DB1 // DB1 type

  object DB1 {
    implicit val orDB1: OR[DB1] = new OR[DB1] {
      override def eval(cr1: Criteria[DB1], cr2: Criteria[DB1]): Criteria[DB1] =
        Criteria.pure(s"(${cr1.value} OR ${cr2.value})")
    }
    implicit val andDB1: AND[DB1] = new AND[DB1] {
      override def eval(cr1: Criteria[DB1], cr2: Criteria[DB1]): Criteria[DB1] =
        Criteria.pure(s"(${cr1.value} AND ${cr2.value})")
    }
    implicit val gtDB1: Gt[DB1] = new Gt[DB1] {
      override def eval(cr1: Reference[DB1], cr2: Reference[DB1]): Criteria[DB1] =
        Criteria.pure(s"${cr1.value} > ${cr2.value}")
    }
    implicit val leqDB1: Leq[DB1] = new Leq[DB1] {
      override def eval(cr1: Reference[DB1], cr2: Reference[DB1]): Criteria[DB1] =
        Criteria.pure(s"${cr1.value} <= ${cr2.value}")
    }
    implicit val eqDB1: Eq[DB1] = new Eq[DB1] {
      override def eval(cr1: Reference[DB1], cr2: Reference[DB1]): Criteria[DB1] =
        Criteria.pure(s"${cr1.value} = ${cr2.value}")
    }
    implicit val isNullDB1: IsNull[DB1] = new IsNull[DB1] {
      override def eval(cr: Reference[DB1]): Criteria[DB1] = Criteria.pure(s"${cr.value} IS NULL")
    }

  }

  trait DB2 // DB2 type

  object DB2 {
    implicit val orDB2: OR[DB2] = new OR[DB2] {
      override def eval(cr1: Criteria[DB2], cr2: Criteria[DB2]): Criteria[DB2] =
        Criteria.pure(s"(${cr1.value} OR ${cr2.value})")
    }
    implicit val andDB2: AND[DB2] = new AND[DB2] {
      override def eval(cr1: Criteria[DB2], cr2: Criteria[DB2]): Criteria[DB2] =
        Criteria.pure(s"(${cr1.value} AND ${cr2.value})")
    }
    implicit val gtDB2: Gt[DB2] = new Gt[DB2] {
      override def eval(cr1: Reference[DB2], cr2: Reference[DB2]): Criteria[DB2] =
        Criteria.pure(s"${cr1.value} > ${cr2.value}")
    }
    implicit val leqDB2: Leq[DB2] = new Leq[DB2] {
      override def eval(cr1: Reference[DB2], cr2: Reference[DB2]): Criteria[DB2] =
        Criteria.pure(s"${cr1.value} <= ${cr2.value}")
    }
    implicit val eqDB2: Eq[DB2] = new Eq[DB2] {
      override def eval(cr1: Reference[DB2], cr2: Reference[DB2]): Criteria[DB2] =
        Criteria.pure(s"${cr1.value} = ${cr2.value}")
    }

  }

  object DBString {
    implicit val orString: OR[String] = new OR[String] {
      override def eval(cr1: Criteria[String], cr2: Criteria[String]): Criteria[String] =
        Criteria.pure(s"(${cr1.value} OR ${cr2.value})")
    }
    implicit val andString: AND[String] = new AND[String] {
      override def eval(cr1: Criteria[String], cr2: Criteria[String]): Criteria[String] =
        Criteria.pure(s"(${cr1.value} AND ${cr2.value})")
    }
    implicit val gtString: Gt[String] = new Gt[String] {
      override def eval(cr1: Reference[String], cr2: Reference[String]): Criteria[String] =
        Criteria.pure(s"${cr1.value} > ${cr2.value}")
    }
    implicit val leqString: Leq[String] = new Leq[String] {
      override def eval(cr1: Reference[String], cr2: Reference[String]): Criteria[String] =
        Criteria.pure(s"${cr1.value} <= ${cr2.value}")
    }
    implicit val eqString: Eq[String] = new Eq[String] {
      override def eval(cr1: Reference[String], cr2: Reference[String]): Criteria[String] =
        Criteria.pure(s"${cr1.value} = ${cr2.value}")
    }

  }

  object DBOptionInt {
    implicit val orOptionInt: OR[Option[Int]] = new OR[Option[Int]] {
      override def eval(cr1: Criteria[Option[Int]], cr2: Criteria[Option[Int]]): Criteria[Option[Int]] =
        Criteria.pure(s"(${cr1.value} OR ${cr2.value})")
    }
    implicit val andOptionInt: AND[Option[Int]] = new AND[Option[Int]] {
      override def eval(cr1: Criteria[Option[Int]], cr2: Criteria[Option[Int]]): Criteria[Option[Int]] =
        Criteria.pure(s"(${cr1.value} AND ${cr2.value})")
    }
    implicit val gtOptionInt: Gt[Option[Int]] = new Gt[Option[Int]] {
      override def eval(cr1: Reference[Option[Int]], cr2: Reference[Option[Int]]): Criteria[Option[Int]] =
        Criteria.pure(s"${cr1.value} > ${cr2.value}")
    }
    implicit val leqOptionInt: Leq[Option[Int]] = new Leq[Option[Int]] {
      override def eval(cr1: Reference[Option[Int]], cr2: Reference[Option[Int]]): Criteria[Option[Int]] =
        Criteria.pure(s"${cr1.value} <= ${cr2.value}")
    }
    implicit val eqOptionInt: Eq[Option[Int]] = new Eq[Option[Int]] {
      override def eval(cr1: Reference[Option[Int]], cr2: Reference[Option[Int]]): Criteria[Option[Int]] =
        Criteria.pure(s"${cr1.value} = ${cr2.value}")
    }

  }


}
