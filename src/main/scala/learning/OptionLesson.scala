package learning

object OptionLesson {
  // A deliberately tiny lookup with one known policy, not a database query.
  // Option[PolicySnapshot] means: a PolicySnapshot may be present or absent.
  def findPolicy(number: String): Option[PolicySnapshot] = {
    if (number == "POL-001") {
      Some(PolicySnapshot("POL-001", 600, 3))
    } else {
      None
    }
  }

  // match is an expression: the selected branch supplies the result String.
  def describePolicy(result: Option[PolicySnapshot]): String = {
    result match {
      // A pattern, not a constructor call: bind the contained snapshot to policy.
      case Some(policy) => "Found policy " + policy.number
      case None => "Policy not found"
    }
  }

  def run(): Unit = {
    println("--- Option: present or absent ---")

    val found: Option[PolicySnapshot] = findPolicy("POL-001")
    val missing: Option[PolicySnapshot] = findPolicy("POL-999")

    println("Found: " + found)
    println("Missing: " + missing)
    // These methods inspect presence; they do not extract the policy.
    println("Found is defined: " + found.isDefined)
    println("Missing is empty: " + missing.isEmpty)

    println("--- Handling Option with match ---")
    println(describePolicy(found))
    println(describePolicy(missing))
  }
}
