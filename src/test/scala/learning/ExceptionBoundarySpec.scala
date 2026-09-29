package learning

import org.scalatest.funsuite.AnyFunSuite

class ExceptionBoundarySpec extends AnyFunSuite {
  test("the result-flow parser returns a Left for malformed text") {
    val actual = ResultFlowLesson.readInstallments("hello")

    // This method captured the exception internally. We receive an ordinary value.
    assert(actual == Left("Cannot read installments: NumberFormatException"))
  }

  test("raw text conversion throws an exception we can inspect") {
    // intercept is inherited from ScalaTest, not a Scala keyword.
    // [NumberFormatException] supplies the expected TYPE, not a normal value.
    // The block supplies the code; intercept runs it inside its checking boundary.
    // It fails the test if nothing is thrown or if the exception type is wrong.
    val error: NumberFormatException = intercept[NumberFormatException] {
      "hello".toInt
    }

    // intercept returns the caught exception, not an Int, Left or Try.
    // Inspect its diagnostic without depending on exact JVM message wording.
    assert(error.getMessage.nonEmpty)
  }
}
