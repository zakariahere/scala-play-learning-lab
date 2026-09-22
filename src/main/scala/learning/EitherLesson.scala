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
  }
}
