package learning

import scala.util.{Failure, Success, Try}

object ResultFlowLesson {
  // The lookup can legitimately return None. This particular workflow requires
  // a policy, so HERE we choose a failure reason for that absence.
  def requirePolicy(number: String): Either[String, PolicySnapshot] = {
    OptionLesson.findPolicy(number) match {
      case Some(policy) => Right(policy)
      case None => Left("Policy not found: " + number)
    }
  }

  def readInstallments(text: String): Either[String, Int] = {
    // Capture the throwing conversion, then choose this workflow's error text.
    // Successful parsing is separate from our fictional positive-count rule.
    Try(text.toInt) match {
      case Success(count) =>
        if (count > 0) Right(count)
        else Left("Installments must be positive")
      case Failure(error) =>
        Left("Cannot read installments: " + error.getClass.getSimpleName)
    }
  }

  def installmentLabel(number: String, text: String): Either[String, String] = {
    // Both generators now return Either with String errors. A for-comprehension
    // does not automatically mix Option, Try and Either or invent conversions.
    for {
      policy <- requirePolicy(number)
      count <- readInstallments(text)
    } yield policy.number + ": " + policy.basePremium + " EUR / " + count +
      " = " + (policy.basePremium / count) + " EUR"
    // Whole-euro integer division of BASE premium only, not a real payment
    // schedule or the claim-free discounted premium. Positive count avoids / 0.
  }

  def run(): Unit = {
    println("--- One flow: Option lookup, Try parsing, Either decisions ---")
    val valid = installmentLabel("POL-001", "12")
    val missing = installmentLabel("POL-999", "12")
    val malformed = installmentLabel("POL-001", "hello")
    val zero = installmentLabel("POL-001", "0")
    val bothBad = installmentLabel("POL-999", "hello")

    println("Valid request: " + valid)
    println("Missing policy: " + missing)
    println("Malformed count: " + malformed)
    println("Parsed but rejected count: " + zero)
    println("Both inputs bad, first error wins: " + bothBad)

    assert(requirePolicy("POL-001") == Right(PolicySnapshot("POL-001", 600, 3)))
    assert(requirePolicy("POL-999") == Left("Policy not found: POL-999"))
    assert(readInstallments("12") == Right(12))
    assert(readInstallments("hello") == Left("Cannot read installments: NumberFormatException"))
    assert(readInstallments("") == Left("Cannot read installments: NumberFormatException"))
    assert(readInstallments("2147483648") == Left("Cannot read installments: NumberFormatException"))
    assert(readInstallments("0") == Left("Installments must be positive"))
    assert(readInstallments("-1") == Left("Installments must be positive"))
    assert(valid == Right("POL-001: 600 EUR / 12 = 50 EUR"))
    assert(missing == Left("Policy not found: POL-999"))
    assert(malformed == Left("Cannot read installments: NumberFormatException"))
    assert(zero == Left("Installments must be positive"))
    assert(bothBad == missing)

    // Observe short-circuiting: this guard must not run after a missing policy.
    val skipped = for {
      policy <- requirePolicy("POL-999")
      count <- {
        assert(false, "Missing policy must skip reading the count")
        readInstallments("hello")
      }
    } yield policy.number + ": " + count
    assert(skipped == missing)
  }
}
