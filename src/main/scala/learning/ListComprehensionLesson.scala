package learning

object ListComprehensionLesson {
  def run(): Unit = {
    println("--- List for-comprehensions: transform, then add a guard ---")

    val policies: List[PolicySnapshot] = List(
      PolicySnapshot("POL-001", 600, 3),
      PolicySnapshot("POL-002", 600, 2),
      PolicySnapshot("POL-003", 500, 3)
    )

    // One generator: policy is one PolicySnapshot, not the whole List.
    // yield contributes a String for each policy; the result is List[String].
    val numbers: List[String] = for {
      policy <- policies
    } yield policy.number

    val numbersWithMap: List[String] =
      policies.map(policy => policy.number)

    // The guard decides whether this policy reaches yield. No else is needed.
    val experiencedNumbers: List[String] = for {
      policy <- policies
      if policy.claimFreeYears >= 3
    } yield policy.number

    // Scala 2 translates this simple guarded comprehension to withFilter + map.
    // withFilter defers selection until map, avoiding a filtered intermediate List.
    val experiencedWithMethods: List[String] = policies
      .withFilter(policy => policy.claimFreeYears >= 3)
      .map(policy => policy.number)

    // Same values for these pure functions, but filter builds an intermediate List.
    val experiencedWithFilter: List[String] = policies
      .filter(policy => policy.claimFreeYears >= 3)
      .map(policy => policy.number)

    val noMatches: List[String] = for {
      policy <- policies
      if policy.claimFreeYears >= 10
    } yield policy.number

    val emptyInput: List[String] = for {
      policy <- List.empty[PolicySnapshot]
    } yield policy.number

    println("One generator: " + numbers)
    println("Guard >= 3: " + experiencedNumbers)
    println("withFilter + map: " + experiencedWithMethods)
    println("Guard >= 10: " + noMatches)
    println("Empty input: " + emptyInput)

    assert(numbers == List("POL-001", "POL-002", "POL-003"))
    assert(numbers == numbersWithMap)
    assert(experiencedNumbers == List("POL-001", "POL-003"))
    assert(experiencedNumbers == experiencedWithMethods)
    assert(experiencedNumbers == experiencedWithFilter)
    assert(noMatches.isEmpty)
    assert(emptyInput.isEmpty)
  }
}
