package learning

object PolicyNumberSyntax {
  // "implicit class" makes this one-argument wrapper eligible for an
  // automatic conversion when a String does not already have the method.
  implicit class PolicyNumberOps(private val number: String) {
    def asPolicyLabel: String = "Policy " + number
  }
}

object ImplicitClassLesson {
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
  }
}
