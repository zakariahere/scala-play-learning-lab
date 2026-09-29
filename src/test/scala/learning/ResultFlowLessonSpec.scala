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

  test("six installments produce a 100 EUR base-premium label") {
    // Arrange: a second successful case, with its own explicit inputs.
    val policyNumber = "POL-001"
    val installments = "6"

    // Act: call the real workflow once.
    val actual = ResultFlowLesson.installmentLabel(policyNumber, installments)

    // Assert: derive the expectation independently: 600 / 6 = 100.
    // This catches an implementation that always produces a 50 EUR amount.
    assert(actual == Right("POL-001: 600 EUR / 6 = 100 EUR"))
  }

  // A returned Left is an expected workflow outcome, not a failed test.
  // The test passes when the entire outcome equals the expected Left.
  test("a missing policy returns its not-found reason") {
    val actual = ResultFlowLesson.installmentLabel("POL-999", "12")

    assert(actual == Left("Policy not found: POL-999"))
  }

  test("a malformed installment count returns a parsing reason") {
    val actual = ResultFlowLesson.installmentLabel("POL-001", "hello")

    assert(actual == Left("Cannot read installments: NumberFormatException"))
  }

  test("zero installments are rejected after successful parsing") {
    val actual = ResultFlowLesson.installmentLabel("POL-001", "0")

    assert(actual == Left("Installments must be positive"))
  }

  test("negative installments are rejected after successful parsing") {
    val actual = ResultFlowLesson.installmentLabel("POL-001", "-1")

    assert(actual == Left("Installments must be positive"))
  }

  test("a missing policy reason wins when the installment text is also invalid") {
    val actual = ResultFlowLesson.installmentLabel("POL-999", "hello")

    // Check the externally visible first-error contract. This result alone
    // does not instrument or prove whether an internal method was invoked.
    assert(actual == Left("Policy not found: POL-999"))
  }
}
