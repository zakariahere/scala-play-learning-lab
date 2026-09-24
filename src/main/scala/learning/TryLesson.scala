package learning

// Import three names from scala.util, just as Java imports library types.
import scala.util.{Failure, Success, Try}

object TryLesson {
  // toInt returns an Int or throws NumberFormatException.
  // Try evaluates that expression here, inside its exception-handling boundary.
  // It returns Success(value), or Failure(exception) for a non-fatal exception.
  def parsePremium(text: String): Try[Int] = {
    Try(text.toInt)
  }

  def describePremium(result: Try[Int]): String = {
    result match {
      // premium is an Int; error is the captured Throwable, not a message String.
      case Success(premium) => "Parsed premium: " + premium + " EUR"
      case Failure(error) => "Cannot parse premium: " + error.getClass.getSimpleName
    }
  }

  // Whole-euro integer division is just a tiny example, not payment scheduling.
  def installmentWithMatch(result: Try[Int], installments: Int): Try[Int] = {
    result match {
      // Keep Try here: division can throw even though parsing succeeded.
      // Success(premium / installments) would not catch that exception.
      case Success(premium) => Try(premium / installments)
      case Failure(error) => Failure(error)
    }
  }

  def installmentWithMap(result: Try[Int], installments: Int): Try[Int] = {
    // The callback returns an Int. map wraps it in Success, or captures
    // a non-fatal exception from the callback as Failure.
    result.map((premium: Int) => premium / installments)
  }

  def describeInstallment(result: Try[Int]): String = {
    result match {
      case Success(amount) => "Installment: " + amount + " EUR"
      case Failure(error) => "Cannot calculate installment: " + error.getClass.getSimpleName
    }
  }

  // Unlike the earlier map callback, this method already returns Try[Int].
  def calculateInstallment(premium: Int, installments: Int): Try[Int] = {
    Try(premium / installments)
  }

  def installmentWithNestedMap(result: Try[Int], installments: Int): Try[Try[Int]] = {
    result.map((premium: Int) => calculateInstallment(premium, installments))
  }

  def installmentResultWithMatch(result: Try[Int], installments: Int): Try[Int] = {
    result match {
      // This helper already captures the division: return its Try directly.
      case Success(premium) => calculateInstallment(premium, installments)
      case Failure(error) => Failure(error)
    }
  }

  def installmentWithFlatMap(result: Try[Int], installments: Int): Try[Int] = {
    result.flatMap((premium: Int) => calculateInstallment(premium, installments))
  }

  def run(): Unit = {
    println("--- Try: turn a throwing operation into a result ---")
    val valid: Try[Int] = parsePremium("600")
    val invalid: Try[Int] = parsePremium("hello")

    println("Valid input: " + valid)
    println("Invalid input: " + invalid)
    println(describePremium(valid))
    println(describePremium(invalid))

    assert(valid == Success(600))
    assert(describePremium(valid) == "Parsed premium: 600 EUR")
    assert(describePremium(invalid) == "Cannot parse premium: NumberFormatException")

    // Conversion is not business validation: a negative integer still parses.
    assert(parsePremium("-1") == Success(-1))
    // Other bad numeric text is captured too; do not depend on JVM message wording.
    assert(describePremium(parsePremium("")) == "Cannot parse premium: NumberFormatException")
    assert(describePremium(parsePremium("2147483648")) == "Cannot parse premium: NumberFormatException")

    println("Execution continues after the failed conversion.")

    println("--- Try.map: success, existing failure, throwing transformation ---")
    val normalWithMatch = installmentWithMatch(valid, 12)
    val normalWithMap = installmentWithMap(valid, 12)
    val parseFailure = installmentWithMap(invalid, 12)
    // The divisor is zero; parsing 600 has already succeeded.
    val divisionFailureWithMatch = installmentWithMatch(valid, 0)
    val divisionFailureWithMap = installmentWithMap(valid, 0)

    println("Match, 600 / 12: " + normalWithMatch)
    println("Map, 600 / 12: " + normalWithMap)
    println("Map, existing parse failure: " + parseFailure)
    println("Match, 600 / 0: " + divisionFailureWithMatch)
    println("Map, 600 / 0: " + divisionFailureWithMap)
    println(describeInstallment(normalWithMap))
    println(describeInstallment(divisionFailureWithMap))

    assert(normalWithMatch == Success(50))
    assert(normalWithMap == normalWithMatch)
    assert(parseFailure == invalid)
    assert(installmentWithMatch(invalid, 12) == invalid)
    assert(describeInstallment(normalWithMap) == "Installment: 50 EUR")
    // Separate throws create separate exception objects. Check the outcome type,
    // not equality between two independently created Failure instances.
    assert(describeInstallment(divisionFailureWithMatch) ==
      "Cannot calculate installment: ArithmeticException")
    assert(describeInstallment(divisionFailureWithMap) ==
      "Cannot calculate installment: ArithmeticException")

    val skipped: Try[Int] = invalid.map((premium: Int) => {
      assert(false, "map must not run its callback on an existing Failure")
      premium
    })
    // If the callback ran, Try.map would capture the assertion error instead;
    // requiring the original failure here still detects that incorrect behavior.
    assert(skipped == invalid)
    assert(valid == Success(600))
    println("Original parsed value: " + valid)
    println("Execution continues after the throwing transformation.")

    println("--- Try.flatMap: the next method already returns Try ---")
    val nestedSuccess = installmentWithNestedMap(valid, 12)
    val nestedDivisionFailure = installmentWithNestedMap(valid, 0)
    val nestedParseFailure = installmentWithNestedMap(invalid, 12)
    val flatSuccess = installmentWithFlatMap(valid, 12)
    val flatDivisionFailure = installmentWithFlatMap(valid, 0)
    val flatParseFailure = installmentWithFlatMap(invalid, 12)

    println("map, both steps succeed: " + nestedSuccess)
    println("map, calculation fails: " + nestedDivisionFailure)
    println("map, parsing fails: " + nestedParseFailure)
    println("flatMap, both steps succeed: " + flatSuccess)
    println("flatMap, calculation fails: " + flatDivisionFailure)
    println("flatMap, parsing fails: " + flatParseFailure)

    assert(nestedSuccess == Success(Success(50)))
    // The outer map succeeds because the helper RETURNED a Failure value;
    // it did not throw an exception out to that map.
    assert(nestedDivisionFailure.map((inner: Try[Int]) => describeInstallment(inner)) ==
      Success("Cannot calculate installment: ArithmeticException"))
    assert(nestedParseFailure == invalid)
    assert(flatSuccess == Success(50))
    assert(describeInstallment(flatDivisionFailure) ==
      "Cannot calculate installment: ArithmeticException")
    assert(flatParseFailure == invalid)
    assert(installmentResultWithMatch(valid, 12) == flatSuccess)
    assert(describeInstallment(installmentResultWithMatch(valid, 0)) ==
      describeInstallment(flatDivisionFailure))
    assert(installmentResultWithMatch(invalid, 12) == flatParseFailure)

    val skippedCalculation = invalid.flatMap((premium: Int) => {
      assert(false, "flatMap must skip the calculation on an existing Failure")
      calculateInstallment(premium, 12)
    })
    assert(skippedCalculation == invalid)

    // flatMap also captures a non-fatal exception thrown by its callback,
    // even if that callback throws BEFORE it manages to return a Try.
    val callbackThrows: Try[Int] =
      valid.flatMap((premium: Int) => Success(premium / 0))
    assert(describeInstallment(callbackThrows) ==
      "Cannot calculate installment: ArithmeticException")
  }
}
