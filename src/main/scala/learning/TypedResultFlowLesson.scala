package learning

import scala.util.{Failure, Success, Try}

object TypedResultFlowLesson {
  // Same lookup as ResultFlowLesson; only the error representation changes.
  def requirePolicy(number: String): Either[PolicyInputError, PolicySnapshot] = {
    OptionLesson.findPolicy(number) match {
      case Some(policy) => Right(policy)
      case None => Left(PolicyNotFound(number))
    }
  }

  def readInstallments(text: String): Either[PolicyInputError, Int] = {
    // Keep the exception-catching boundary limited to this conversion.
    Try(text.toInt) match {
      case Success(count) =>
        if (count > 0) Right(count)
        else Left(NonPositiveInstallments(count))
      // _ ignores the captured exception; this domain error keeps the input.
      // This is an explicit choice, not automatic Try-to-Either conversion.
      case Failure(_) => Left(MalformedInstallments(text))
    }
  }

  def installmentLabel(number: String, text: String): Either[PolicyInputError, String] = {
    // Same flatMap/map composition; both operations share PolicyInputError.
    // A Left skips the remaining dependent work and keeps its structured error.
    for {
      policy <- requirePolicy(number)
      count <- readInstallments(text)
    } yield policy.number + ": " + policy.basePremium + " EUR / " + count +
      " = " + (policy.basePremium / count) + " EUR"
    // Whole-euro BASE premium example, not production payment scheduling.
  }

  // Turn data into text at the display boundary, not inside the workflow.
  def display(result: Either[PolicyInputError, String]): String = result match {
    case Right(label) => label
    case Left(error) => SealedErrorLesson.describe(error)
  }

  def run(): Unit = {
    println("--- Typed Either: named errors instead of String reasons ---")
    val valid = installmentLabel("POL-001", "12")
    val missing = installmentLabel("POL-999", "12")
    val malformed = installmentLabel("POL-001", "hello")
    val zero = installmentLabel("POL-001", "0")
    val negative = installmentLabel("POL-001", "-1")
    val bothBad = installmentLabel("POL-999", "hello")
    println("Valid: " + valid)
    println("Missing: " + missing)
    println("Malformed: " + malformed)
    println("Zero: " + zero)
    println("Negative: " + negative)
    println("Both inputs bad, first error wins: " + bothBad)
    println("Display missing: " + display(missing))
    println("Display malformed: " + display(malformed))

    assert(requirePolicy("POL-001") == Right(PolicySnapshot("POL-001", 600, 3)))
    assert(requirePolicy("POL-999") == Left(PolicyNotFound("POL-999")))
    assert(readInstallments("12") == Right(12))
    assert(readInstallments("hello") == Left(MalformedInstallments("hello")))
    assert(readInstallments("") == Left(MalformedInstallments("")))
    assert(readInstallments("2147483648") == Left(MalformedInstallments("2147483648")))
    assert(valid == Right("POL-001: 600 EUR / 12 = 50 EUR"))
    assert(missing == Left(PolicyNotFound("POL-999")))
    assert(malformed == Left(MalformedInstallments("hello")))
    assert(zero == Left(NonPositiveInstallments(0)))
    assert(negative == Left(NonPositiveInstallments(-1)))
    assert(bothBad == missing)
    assert(display(valid) == "POL-001: 600 EUR / 12 = 50 EUR")
    assert(display(malformed) == "Cannot read installments: hello")
    assert(display(zero) == "Installments must be positive: 0")

    // This additional guard observes skipped work, not just error precedence.
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
