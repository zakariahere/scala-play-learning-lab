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
  }
}
