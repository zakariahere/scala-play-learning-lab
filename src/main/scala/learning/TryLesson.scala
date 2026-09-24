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
  }
}
