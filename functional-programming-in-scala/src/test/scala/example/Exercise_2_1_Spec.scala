package example

import zio.*
import zio.test.*
import zio.test.Assertion.*

object FibbonaciSpec extends ZIOSpecDefault {
  import Exercise_2_1.fibs

  override def spec = suite("FibonacciSpec")(
    test("fibs(0) == 0") {
      assertTrue(fibs(0) == 0)
    },
    test("fibs(1) == 1") {
      assertTrue(fibs(1) == 1)
    },
    test("fibs(n) == fibs(n-1) + fibs(n-2) for n > 1") {
      check(Gen.int(2, 40)) { n =>
        assertTrue(fibs(n) == fibs(n - 1) + fibs(n - 2))
      }
    }
  )
}
