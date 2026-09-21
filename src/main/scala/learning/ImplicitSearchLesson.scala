package learning

object ImplicitSearchLesson {
  def explicitWithOrdinaryValue(): String = {
    val store: PolicyRepository = new InMemoryPolicyRepository(
      List(PolicySnapshot("POL-001", 600, 3))
    )
    // No implicit search is needed when the argument is supplied explicitly.
    ImplicitParameterLesson.describe("POL-001")(store)
  }

  def selectExplicitly(): String = {
    implicit val first: PolicyRepository = new InMemoryPolicyRepository(
      List(PolicySnapshot("POL-001", 600, 3))
    )
    implicit val second: PolicyRepository =
      new InMemoryPolicyRepository(List.empty[PolicySnapshot])

    // Omitting the second argument list here would be ambiguous.
    // Explicit selection works even with both implicit values in scope.
    ImplicitParameterLesson.describe("POL-001")(second)
  }

  def run(): Unit = {
    println("--- Implicit search errors: explicit fixes ---")
    val ordinary = explicitWithOrdinaryValue()
    val selected = selectExplicitly()
    println("Ordinary val passed explicitly: " + ordinary)
    println("Second candidate selected explicitly: " + selected)
    assert(ordinary == "Found policy POL-001")
    assert(selected == "Policy not found")
  }
}
