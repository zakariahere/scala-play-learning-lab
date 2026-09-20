package learning

object TraitLesson {
  // The caller needs the contract, not the concrete storage class.
  // The dependency is passed explicitly as an ordinary method argument.
  def describe(number: String, repository: PolicyRepository): String =
    repository.find(number)
      .map(policy => "Found policy " + policy.number)
      .getOrElse("Policy not found")

  def run(): Unit = {
    println("--- Traits: a repository contract and its implementation ---")

    val policies = List(
      PolicySnapshot("POL-001", 600, 3),
      PolicySnapshot("POL-002", 600, 2)
    )

    // Declared type: PolicyRepository. Actual object: InMemoryPolicyRepository.
    val repository: PolicyRepository = new InMemoryPolicyRepository(policies)
    val emptyRepository: PolicyRepository =
      new InMemoryPolicyRepository(List.empty[PolicySnapshot])

    val found = describe("POL-001", repository)
    val missing = describe("POL-999", repository)
    val empty = describe("POL-001", emptyRepository)

    println("Through the trait: " + found)
    println("Missing number: " + missing)
    println("Empty repository: " + empty)

    assert(repository.find("POL-001") == Some(PolicySnapshot("POL-001", 600, 3)))
    assert(found == "Found policy POL-001")
    assert(missing == "Policy not found")
    assert(empty == "Policy not found")
  }
}
