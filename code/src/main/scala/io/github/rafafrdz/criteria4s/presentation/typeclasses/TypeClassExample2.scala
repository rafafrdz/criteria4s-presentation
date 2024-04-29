package io.github.rafafrdz.criteria4s.presentation.typeclasses

object TypeClassExample2 extends App {

  trait Add[T] {
    def add(x: T, y: T): T
  }

  trait Sub[T] {
    def sub(x: T, y: T): T
  }

  trait Mul[T] {
    def mul(x: T, y: T): T
  }

  trait Div[T] {
    def div(x: T, y: T): T
  }

  object Add {
    implicit val intAdd: Add[Int] = new Add[Int] {
      def add(x: Int, y: Int): Int = x + y
    }

    implicit val doubleAdd: Add[Double] = new Add[Double] {
      def add(x: Double, y: Double): Double = x + y
    }

    implicit val stringAdd: Add[String] = new Add[String] {
      def add(x: String, y: String): String = s"$x + $y"
    }
  }

  object Sub {
    implicit val intSub: Sub[Int] = new Sub[Int] {
      def sub(x: Int, y: Int): Int = x - y
    }

    implicit val doubleSub: Sub[Double] = new Sub[Double] {
      def sub(x: Double, y: Double): Double = x - y
    }

    implicit val stringSub: Sub[String] = new Sub[String] {
      def sub(x: String, y: String): String = s"$x - $y"
    }
  }

  object Mul {
    implicit val intMul: Mul[Int] = new Mul[Int] {
      def mul(x: Int, y: Int): Int = x * y
    }

    implicit val doubleMul: Mul[Double] = new Mul[Double] {
      def mul(x: Double, y: Double): Double = x * y
    }

    implicit val stringMul: Mul[String] = new Mul[String] {
      def mul(x: String, y: String): String = s"$x * $y"
    }
  }

  object Div {
    implicit val stringDiv: Div[String] = new Div[String] {
      def div(x: String, y: String): String = s"$x / $y"
    }
  }

  implicit class Ops[T](x: T) {
    def :+(y: T)(implicit op: Add[T]): T = op.add(x, y)
    def :-(y: T)(implicit op: Sub[T]): T = op.sub(x, y)
    def :*(y: T)(implicit op: Mul[T]): T = op.mul(x, y)
    def :/(y: T)(implicit op: Div[T]): T = op.div(x, y)
  }

  /** Expr 1 */
  def expr1[T: Add: Mul: Sub](x: T, y: T): T = (x :+ y) :- (y :* y)
  val intExpr1: Int                       = expr1(10, 5)     // -10
  val doubleExpr1: Double                 = expr1(10.0, 5.0) // -10.0
  val stringExpr1: String                 = expr1("10", "5") // ((10 + 5) - (5 * 5))

  /** Expr 2 */
  def expr2[T: Div](x: T, y: T): T = x :/ y
  // val intExpr2: Int                  = expr2(10, 5)     // This is not possible because there is no an instance of Div[Int]
  // val doubleExpr2: Double            = expr2(10.0, 5.0) // This is not possible because there is no an instance of Div[Double]
  val stringExpr2: String                 = expr2("10", "5") // (10 / 5)

  /** Inline Expr */
  // val intExprInline: Int       = 10 :/ 2     // This is not possible because there is no an instance of Div[Int]
  // val doubleExprInline: Double = 10.0 :/ 2.0 // This is not possible because there is no an instance of Div[Double]
  val stringExprInline: String      = "10" :/ "2" // (10 / 2)

}
