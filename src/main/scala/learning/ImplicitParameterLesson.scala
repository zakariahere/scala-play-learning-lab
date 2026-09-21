package learning

object ImplicitParameterLesson {
  // Only the second parameter list is implicit. The body is ordinary code.
  def describe(number: String)(implicit repository: PolicyRepository): String =
    repository.find(number)
      .map(policy => "Found policy " + policy.number)
      .getOrElse("Policy not found")

  def run(): Unit = {
    println("--- Implicit parameters: the compiler supplies an argument ---")

    // Makes this value eligible for implicit search at calls in this scope.
    // The name need not match the parameter name: its type is what matters here.
    implicit val localStore: PolicyRepository = new InMemoryPolicyRepository(
      List(PolicySnapshot("POL-001", 600, 3))
    )

    val explicit = describe("POL-001")(localStore)
    // The compiler inserts localStore as the second argument at this call site.
    val inferred = describe("POL-001")
    val missing = describe("POL-999")

    // An explicit argument can still select a different repository.
    // This ordinary val is not an implicit candidate.
    val emptyStore: PolicyRepository =
      new InMemoryPolicyRepository(List.empty[PolicySnapshot])
    val explicitlyEmpty = describe("POL-001")(emptyStore)

    println("Explicit argument: " + explicit)
    println("Compiler-supplied argument: " + inferred)
    println("Missing policy: " + missing)
    println("Explicit empty store: " + explicitlyEmpty)

    assert(explicit == "Found policy POL-001")
    assert(inferred == explicit)
    assert(missing == "Policy not found")
    assert(explicitlyEmpty == "Policy not found")
  }
}
