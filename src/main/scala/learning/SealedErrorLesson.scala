package learning

object SealedErrorLesson {
  // Choose wording here, separately from the structured error data.
  def describe(error: PolicyInputError): String = error match {
    // In a pattern, number/text/count bind fields from the existing object.
    // They do not construct another error object.
    case PolicyNotFound(number) => "Policy not found: " + number
    case MalformedInstallments(text) => "Cannot read installments: " + text
    case NonPositiveInstallments(count) => "Installments must be positive: " + count
  }

  def run(): Unit = {
    println("--- Sealed traits: named error alternatives ---")
    // Each concrete value can be referred to through the shared trait type.
    val missing: PolicyInputError = PolicyNotFound("POL-999")
    val malformed: PolicyInputError = MalformedInstallments("hello")
    val nonPositive: PolicyInputError = NonPositiveInstallments(0)

    println("Error data: " + missing)
    println("Display message: " + describe(missing))
    println("Error data: " + malformed)
    println("Display message: " + describe(malformed))
    println("Error data: " + nonPositive)
    println("Display message: " + describe(nonPositive))
    println("Execution continues: constructing an error value does not throw.")

    assert(describe(missing) == "Policy not found: POL-999")
    assert(describe(malformed) == "Cannot read installments: hello")
    assert(describe(nonPositive) == "Installments must be positive: 0")
    assert(describe(NonPositiveInstallments(-1)) == "Installments must be positive: -1")
    assert(missing == PolicyNotFound("POL-999"))
    assert(missing != PolicyNotFound("POL-002"))

    // Names/types model the error cases, but do not validate fields themselves.
    // A later validation method will choose NonPositiveInstallments only for <= 0.
    // Keep the existing String-based ResultFlowLesson unchanged for comparison.
  }
}
