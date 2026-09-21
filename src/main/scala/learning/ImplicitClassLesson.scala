package learning

object PolicyNumberSyntax {
  // "implicit class" makes this one-argument wrapper eligible for an
  // automatic conversion when a String does not already have the method.
  implicit class PolicyNumberOps(private val number: String) {
    def asPolicyLabel: String = "Policy " + number

    // The wrapper stores the number; each method call receives a repository.
    // This is a separate implicit mechanism from converting String to the wrapper.
    def lookup(implicit repository: PolicyRepository): Option[PolicySnapshot] =
      repository.find(number)
  }
}

object ImplicitClassLesson {
  // Separate method bodies keep each example's imports and values local.
  def withoutSyntaxImport(): Option[PolicySnapshot] = {
    implicit val store: PolicyRepository =
      new InMemoryPolicyRepository(List(PolicySnapshot("POL-001", 600, 3)))

    // No syntax import: construct the wrapper ourselves.
    // The method's repository argument can still be inferred.
    new PolicyNumberSyntax.PolicyNumberOps("POL-001").lookup
  }

  def withoutImplicitRepository(): Option[PolicySnapshot] = {
    import PolicyNumberSyntax._
    val store: PolicyRepository =
      new InMemoryPolicyRepository(List(PolicySnapshot("POL-001", 600, 3)))

    // Automatic wrapper conversion, but an explicitly supplied argument.
    // store does not have to be implicit when we pass it ourselves.
    "POL-001".lookup(store)
  }

  def run(): Unit = {
    println("--- Implicit classes: extension-style methods ---")

    val policyNumber: String = "POL-001"

    // This is the ordinary wrapper call: create the wrapper, then call its method.
    val explicitWrapper =
      new PolicyNumberSyntax.PolicyNumberOps(policyNumber).asPolicyLabel

    // The import brings the implicit wrapper conversion into this scope.
    import PolicyNumberSyntax._

    // String has no asPolicyLabel method. Scala can therefore rewrite this call
    // to PolicyNumberOps(policyNumber).asPolicyLabel at the call site.
    val extensionStyle = policyNumber.asPolicyLabel

    println("Explicit wrapper: " + explicitWrapper)
    println("Extension style: " + extensionStyle)

    assert(explicitWrapper == "Policy POL-001")
    assert(extensionStyle == explicitWrapper)

    println("--- Extension method with an implicit repository ---")
    val policy = PolicySnapshot("POL-001", 600, 3)
    implicit val localStore: PolicyRepository =
      new InMemoryPolicyRepository(List(policy))

    // First supply both the wrapper and the method argument explicitly.
    val fullyExplicit =
      new PolicyNumberSyntax.PolicyNumberOps(policyNumber).lookup(localStore)
    // Only wrapper conversion is automatic here; we still choose the repository.
    val explicitRepository = policyNumber.lookup(localStore)
    // Now the compiler also supplies localStore to the lookup method.
    val inferredRepository = policyNumber.lookup
    val missing = "POL-999".lookup

    val emptyStore: PolicyRepository =
      new InMemoryPolicyRepository(List.empty[PolicySnapshot])
    val explicitlyEmpty = policyNumber.lookup(emptyStore)

    println("Explicit wrapper and repository: " + fullyExplicit)
    println("Extension, explicit repository: " + explicitRepository)
    println("Extension, inferred repository: " + inferredRepository)
    println("Missing policy: " + missing)
    println("Explicit empty repository: " + explicitlyEmpty)

    assert(fullyExplicit == Some(policy))
    assert(explicitRepository == fullyExplicit)
    assert(inferredRepository == fullyExplicit)
    assert(missing == None)
    assert(explicitlyEmpty == None)

    println("--- Missing syntax versus missing implicit argument: repairs ---")
    val explicitWrapperRepair = withoutSyntaxImport()
    val explicitArgumentRepair = withoutImplicitRepository()
    println("No syntax import, explicit wrapper: " + explicitWrapperRepair)
    println("Ordinary repository, explicit argument: " + explicitArgumentRepair)
    assert(explicitWrapperRepair == Some(policy))
    assert(explicitArgumentRepair == Some(policy))
  }
}
