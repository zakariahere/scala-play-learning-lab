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

  // Fictional data, matching the earlier Option flatMap lesson.
  // This operation can fail too, so it returns Either rather than plain String.
  def findContactEmail(policy: PolicySnapshot): Either[String, String] = {
    if (policy.number == "POL-001") Right("customer@example.com")
    else Left("Contact email not found: " + policy.number)
  }

  def emailWithMap(
      result: Either[String, PolicySnapshot]
  ): Either[String, Either[String, String]] = {
    // map adds Right around the callback's result, even when that result is Left.
    result.map((policy: PolicySnapshot) => findContactEmail(policy))
  }

  def emailWithMatch(
      result: Either[String, PolicySnapshot]
  ): Either[String, String] = {
    result match {
      // The second lookup already returns Either: return it directly.
      case Right(policy) => findContactEmail(policy)
      case Left(reason) => Left(reason)
    }
  }

  def emailWithFlatMap(
      result: Either[String, PolicySnapshot]
  ): Either[String, String] = {
    // Same branches as emailWithMatch; no extra Right around the second result.
    result.flatMap((policy: PolicySnapshot) => findContactEmail(policy))
  }

  def contactLabelWithMethods(
      result: Either[String, PolicySnapshot]
  ): Either[String, String] = {
    result.flatMap((policy: PolicySnapshot) => {
      findContactEmail(policy).map((email: String) => {
        policy.number + " -> " + email
      })
    })
  }

  def contactLabelWithFor(
      result: Either[String, PolicySnapshot]
  ): Either[String, String] = {
    for {
      // On Right, policy is a PolicySnapshot, not an Either.
      policy <- result
      // This dependent lookup runs only after the first Right.
      email <- findContactEmail(policy)
    } yield policy.number + " -> " + email
    // yield produces a plain String; the final map wraps it in Right.
    // A Left at either step preserves that reason and skips yield.
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

    println("--- Either.flatMap: a second lookup that can fail ---")
    // A direct fixture for an existing policy without an email.
    // findPolicy still knows only POL-001; we do not pretend it finds POL-002.
    val noEmail: Either[String, PolicySnapshot] =
      Right(PolicySnapshot("POL-002", 600, 2))

    val nestedFound = emailWithMap(found)
    val nestedNoEmail = emailWithMap(noEmail)
    val nestedMissing = emailWithMap(missing)
    val flatFound = emailWithFlatMap(found)
    val flatNoEmail = emailWithFlatMap(noEmail)
    val flatMissing = emailWithFlatMap(missing)

    println("map, both found: " + nestedFound)
    println("map, no email: " + nestedNoEmail)
    println("map, no policy: " + nestedMissing)
    println("flatMap, both found: " + flatFound)
    println("flatMap, no email: " + flatNoEmail)
    println("flatMap, no policy: " + flatMissing)

    assert(nestedFound == Right(Right("customer@example.com")))
    assert(nestedNoEmail == Right(Left("Contact email not found: POL-002")))
    assert(nestedMissing == Left("Policy not found: POL-999"))
    assert(flatFound == Right("customer@example.com"))
    assert(flatNoEmail == Left("Contact email not found: POL-002"))
    assert(flatMissing == Left("Policy not found: POL-999"))
    assert(emailWithMatch(found) == flatFound)
    assert(emailWithMatch(noEmail) == flatNoEmail)
    assert(emailWithMatch(missing) == flatMissing)

    // As with map, an initial Left skips the callback altogether.
    val skippedContactLookup: Either[String, String] =
      missing.flatMap((policy: PolicySnapshot) => {
        assert(false, "flatMap must skip the contact lookup on Left")
        findContactEmail(policy)
      })
    assert(skippedContactLookup == flatMissing)

    println("--- Either for-comprehension: the same flatMap/map chain ---")
    val labelWithMethods = contactLabelWithMethods(found)
    val labelWithFor = contactLabelWithFor(found)
    val noEmailLabel = contactLabelWithFor(noEmail)
    val noPolicyLabel = contactLabelWithFor(missing)

    println("Methods, both found: " + labelWithMethods)
    println("For, both found: " + labelWithFor)
    println("For, no email: " + noEmailLabel)
    println("For, no policy: " + noPolicyLabel)

    assert(labelWithMethods == Right("POL-001 -> customer@example.com"))
    assert(labelWithFor == labelWithMethods)
    assert(noEmailLabel == Left("Contact email not found: POL-002"))
    assert(noPolicyLabel == Left("Policy not found: POL-999"))
    assert(contactLabelWithMethods(noEmail) == noEmailLabel)
    assert(contactLabelWithMethods(missing) == noPolicyLabel)
  }
}
