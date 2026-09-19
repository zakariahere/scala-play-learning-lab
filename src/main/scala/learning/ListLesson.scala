package learning

object ListLesson {
  def run(): Unit = {
    println("--- List: several policies, one transformation ---")

    // List(...) constructs an ordered, immutable Scala List.
    // These are three independent fictional policy snapshots.
    val policies: List[PolicySnapshot] = List(
      PolicySnapshot("POL-001", 600, 3),
      PolicySnapshot("POL-002", 600, 2),
      PolicySnapshot("POL-003", 500, 3)
    )

    // The lambda receives ONE policy and returns ONE String.
    // List.map applies it to each element and preserves order.
    val numbers: List[String] =
      policies.map((policy: PolicySnapshot) => policy.number)

    println("Policy count: " + policies.size)
    println("Numbers: " + numbers)
    println("Original policies: " + policies)

    // Same transformation on an empty list: there is nothing to call it on.
    val noPolicies: List[PolicySnapshot] = List.empty[PolicySnapshot]
    val noNumbers: List[String] =
      noPolicies.map((policy: PolicySnapshot) => policy.number)
    println("Empty result: " + noNumbers)

    assert(numbers == List("POL-001", "POL-002", "POL-003"))
    assert(noNumbers.isEmpty)

    println("--- filter: keep policies matching a condition ---")
    // A predicate is a function returning Boolean: true means keep this element.
    val experiencedPolicies: List[PolicySnapshot] =
      policies.filter((policy: PolicySnapshot) => policy.claimFreeYears >= 3)
    val experiencedNumbers: List[String] =
      experiencedPolicies.map((policy: PolicySnapshot) => policy.number)

    // Using the same predicate with map returns the decisions, not the policies.
    val decisions: List[Boolean] =
      policies.map((policy: PolicySnapshot) => policy.claimFreeYears >= 3)

    println("Predicate results with map: " + decisions)
    println("Matching policies: " + experiencedPolicies)
    println("Matching numbers: " + experiencedNumbers)
    println("Original policy count: " + policies.size)

    val noMatches: List[PolicySnapshot] =
      policies.filter((policy: PolicySnapshot) => policy.claimFreeYears >= 10)
    println("No matching policies: " + noMatches)

    assert(experiencedPolicies == List(
      PolicySnapshot("POL-001", 600, 3),
      PolicySnapshot("POL-003", 500, 3)
    ))
    assert(decisions == List(true, false, true))
    assert(noMatches.isEmpty)

    println("--- find: the first matching policy, if any ---")
    val found: Option[PolicySnapshot] =
      policies.find((policy: PolicySnapshot) => policy.number == "POL-002")
    val missing: Option[PolicySnapshot] =
      policies.find((policy: PolicySnapshot) => policy.number == "POL-999")

    // Both POL-001 and POL-003 satisfy this condition; find returns the first.
    val firstExperienced: Option[PolicySnapshot] =
      policies.find((policy: PolicySnapshot) => policy.claimFreeYears >= 3)

    // After find, map is Option.map, not List.map.
    val display: String = found
      .map((policy: PolicySnapshot) => policy.number)
      .getOrElse("Policy not found")
    val missingDisplay: String = missing
      .map((policy: PolicySnapshot) => policy.number)
      .getOrElse("Policy not found")

    println("Found by number: " + found)
    println("Missing by number: " + missing)
    println("First experienced policy: " + firstExperienced)
    println("Found display: " + display)
    println("Missing display: " + missingDisplay)

    assert(found == Some(PolicySnapshot("POL-002", 600, 2)))
    assert(missing == None)
    assert(firstExperienced == Some(PolicySnapshot("POL-001", 600, 3)))
    assert(noPolicies.find((policy: PolicySnapshot) => policy.number == "POL-002") == None)
  }
}
