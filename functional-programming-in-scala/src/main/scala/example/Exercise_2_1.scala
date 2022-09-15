package example

object Exercise_2_1:
  def fibs[T](n: T)(using numeric: Numeric[T]): T =
    import scala.math.Numeric.Implicits.*
    import scala.math.Ordering.Implicits.*

    @annotation.tailrec
    def go(n: T, a: T = numeric.zero, b: T = numeric.one): T =
      if n <= numeric.zero then a
      else go(n - numeric.one, b, a+b)

    go(n)
