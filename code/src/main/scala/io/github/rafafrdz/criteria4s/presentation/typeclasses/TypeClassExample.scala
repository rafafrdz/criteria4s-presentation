package io.github.rafafrdz.criteria4s.presentation.typeclasses

object TypeClassExample extends App {

  trait Arithmetic[T] {
    def add(x: T, y: T): T
    def sub(x: T, y: T): T
    def mul(x: T, y: T): T
    def div(x: T, y: T): T
  }

  object Arithmetic {
    implicit val intArithmetic: Arithmetic[Int] = new Arithmetic[Int] {
      def add(x: Int, y: Int): Int = x + y
      def sub(x: Int, y: Int): Int = x - y
      def mul(x: Int, y: Int): Int = x * y
      def div(x: Int, y: Int): Int = ??? // x / 0 --> This will throw an exception
    }

    implicit val doubleArithmetic: Arithmetic[Double] = new Arithmetic[Double] {
      def add(x: Double, y: Double): Double = x + y
      def sub(x: Double, y: Double): Double = x - y
      def mul(x: Double, y: Double): Double = x * y
      def div(x: Double, y: Double): Double = ??? // x / 0 --> This will throw an exception
    }

    implicit val stringArithmetic: Arithmetic[String] = new Arithmetic[String] {
      def add(x: String, y: String): String = s"($x + $y)"
      def sub(x: String, y: String): String = s"($x - $y)"
      def mul(x: String, y: String): String = s"($x * $y)"
      def div(x: String, y: String): String = s"($x / $y)"
    }
  }

  implicit class ArithmeticOps[T: Arithmetic](x: T) {
    def :+(y: T): T = implicitly[Arithmetic[T]].add(x, y)
    def :-(y: T): T = implicitly[Arithmetic[T]].sub(x, y)
    def :*(y: T): T = implicitly[Arithmetic[T]].mul(x, y)
    def :/(y: T): T = implicitly[Arithmetic[T]].div(x, y)
  }

  /** Expr 1 */
  def expr1[T: Arithmetic](x: T, y: T): T = (x :+ y) :- (y :* y)
  val intExpr1: Int                       = expr1(10, 5)     // -10
  val doubleExpr1: Double                 = expr1(10.0, 5.0) // -10.0
  val stringExpr1: String                 = expr1("10", "5") // ((10 + 5) - (5 * 5))

  /** Expr 2 */
  def expr2[T: Arithmetic](x: T, y: T): T = x :/ y
  lazy val intExpr2: Int                  = expr2(10, 5)     // This will throw an exception
  lazy val doubleExpr2: Double            = expr2(10.0, 5.0) // This will throw an exception
  val stringExpr2: String                 = expr2("10", "5") // (10 / 5)

  /** Inline Expr */
  lazy val intExprInline: Int       = 10 :/ 2     // This will throw an exception
  lazy val doubleExprInline: Double = 10.0 :/ 2.0 // This will throw an exception
  val stringExprInline: String      = "10" :/ "2" // (10 / 2)

}
