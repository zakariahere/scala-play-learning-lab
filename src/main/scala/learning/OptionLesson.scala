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

  // Keep absence in the result: this method still returns an Option.
  def policyNumberWithMatch(result: Option[PolicySnapshot]): Option[String] = {
    result match {
      case Some(policy) => Some(policy.number)
      case None => None
    }
  }

  def policyNumberWithMap(result: Option[PolicySnapshot]): Option[String] = {
    // A lambda: one PolicySnapshot input, its String number as output.
    // map calls it only when a policy exists, then wraps the result in Some.
    result.map((policy: PolicySnapshot) => policy.number)
  }

  def policyNumberOrFallback(result: Option[PolicySnapshot]): String = {
    val number: Option[String] = policyNumberWithMap(result)
    // Some(number) supplies its String; None uses the fallback String.
    number.getOrElse("No policy number")
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

    println("--- Option.map: transform the contained value ---")
    println("Match, found: " + policyNumberWithMatch(found))
    println("Map, found: " + policyNumberWithMap(found))
    println("Match, missing: " + policyNumberWithMatch(missing))
    println("Map, missing: " + policyNumberWithMap(missing))

    println("--- getOrElse: choose a display value ---")
    println("Found display: " + policyNumberOrFallback(found))
    println("Missing display: " + policyNumberOrFallback(missing))


    val theResult = found.map(pol => pol.number).getOrElse("No Policy found!")
    print(theResult)
  }
}
