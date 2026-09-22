package learning

object EitherLesson {
  // Either has two type arguments: the Left payload, then the Right payload.
  // Here we use the convention Left = failure, Right = success.
  // We reuse the existing Option lookup and choose a message for its None case.
  def findPolicy(number: String): Either[String, PolicySnapshot] = {
    OptionLesson.findPolicy(number) match {
      case Some(policy) => Right(policy)
      case None => Left("Policy not found: " + number)
    }
  }

  def describePolicy(result: Either[String, PolicySnapshot]): String = {
    result match {
      // These are patterns: bind the contained value, just like Some(policy).
      case Right(policy) => "Found policy " + policy.number
      case Left(reason) => "Cannot continue: " + reason
    }
  }

  // Explicit version first: transform the success, keep the failure reason.
  def policyNumberWithMatch(
      result: Either[String, PolicySnapshot]
  ): Either[String, String] = {
    result match {
      case Right(policy) => Right(policy.number)
      case Left(reason) => Left(reason)
    }
  }

  def policyNumberWithMap(
      result: Either[String, PolicySnapshot]
  ): Either[String, String] = {
    // The lambda receives a PolicySnapshot and returns a plain String.
    // map wraps that String in Right; on Left it never calls the lambda.
    result.map((policy: PolicySnapshot) => policy.number)
  }

  def run(): Unit = {
    println("--- Either: a policy or a failure reason ---")
    val found: Either[String, PolicySnapshot] = findPolicy("POL-001")
    val missing: Either[String, PolicySnapshot] = findPolicy("POL-999")

    println("Option, missing: " + OptionLesson.findPolicy("POL-999"))
    println("Either, found: " + found)
    println("Either, missing: " + missing)
    println(describePolicy(found))
    println(describePolicy(missing))

    assert(found == Right(PolicySnapshot("POL-001", 600, 3)))
    assert(missing == Left("Policy not found: POL-999"))
    assert(describePolicy(found) == "Found policy POL-001")
    assert(describePolicy(missing) == "Cannot continue: Policy not found: POL-999")

    // Left is a returned value, not a thrown exception.
    // Either itself does not catch exceptions that a method might throw.
    println("Execution continues after the Left result.")

    println("--- Either.map: transform only the successful value ---")
    val foundWithMatch: Either[String, String] = policyNumberWithMatch(found)
    val missingWithMatch: Either[String, String] = policyNumberWithMatch(missing)
    val foundWithMap: Either[String, String] = policyNumberWithMap(found)
    val missingWithMap: Either[String, String] = policyNumberWithMap(missing)

    println("Match, found: " + foundWithMatch)
    println("Map, found: " + foundWithMap)
    println("Match, missing: " + missingWithMatch)
    println("Map, missing: " + missingWithMap)
    println("Original success still contains the policy: " + found)

    assert(foundWithMatch == Right("POL-001"))
    assert(missingWithMatch == Left("Policy not found: POL-999"))
    assert(foundWithMap == foundWithMatch)
    assert(missingWithMap == missingWithMatch)
    assert(found == Right(PolicySnapshot("POL-001", 600, 3)))

    // Verification guard: this assertion would fail if map ran on Left.
    val stillMissing: Either[String, String] =
      missing.map((policy: PolicySnapshot) => {
        assert(false, "map must not call its transformation on Left")
        policy.number
      })
    assert(stillMissing == missingWithMap)
  }
}
