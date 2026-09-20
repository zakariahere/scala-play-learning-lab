package learning

object ParameterListsLesson {
  // One method with two parameter lists. Both names are in scope in the body.
  // Both arguments are still supplied explicitly; nothing is implicit yet.
  def describe(number: String)(repository: PolicyRepository): String =
    repository.find(number)
      .map(policy => "Found policy " + policy.number)
      .getOrElse("Policy not found")

  def run(): Unit = {
    println("--- Multiple parameter lists: same inputs, different grouping ---")
    val repository: PolicyRepository = new InMemoryPolicyRepository(
      List(PolicySnapshot("POL-001", 600, 3))
    )
    val emptyRepository: PolicyRepository =
      new InMemoryPolicyRepository(List.empty[PolicySnapshot])

    val oneList = TraitLesson.describe("POL-001", repository)
    val twoLists = describe("POL-001")(repository)
    val missing = describe("POL-999")(repository)
    val empty = describe("POL-001")(emptyRepository)

    println("One parameter list: " + oneList)
    println("Two parameter lists: " + twoLists)
    println("Missing policy: " + missing)
    println("Explicit empty repository: " + empty)

    assert(twoLists == "Found policy POL-001")
    assert(twoLists == oneList)
    assert(missing == "Policy not found")
    assert(empty == "Policy not found")
  }
}
