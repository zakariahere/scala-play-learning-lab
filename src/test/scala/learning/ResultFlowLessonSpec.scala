package learning

import org.scalatest.funsuite.AnyFunSuite

// A suite groups named tests. sbt discovers it through ScalaTest; no main needed.
class ResultFlowLessonSpec extends AnyFunSuite {
  // test is inherited from AnyFunSuite, not a Scala keyword. The first argument
  // list gives the name; the following block supplies the body to run as a test.
  test("a known policy and 12 installments produce a 50 EUR base-premium label") {
    // Arrange: explicit inputs, independent of PremiumLesson's practice values.
    val policyNumber = "POL-001"
    val installments = "12"

    // Act: call the actual workflow, not a duplicate of its implementation.
    val actual = ResultFlowLesson.installmentLabel(policyNumber, installments)

    // Assert: compare the entire Either, including its Right/Left branch.
    assert(actual == Right("POL-001: 600 EUR / 12 = 50 EUR"))
  }
}
