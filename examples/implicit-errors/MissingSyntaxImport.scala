package learningexamples

import learning.{InMemoryPolicyRepository, PolicyRepository, PolicySnapshot}

object MissingSyntaxImport {
  implicit val store: PolicyRepository =
    new InMemoryPolicyRepository(List(PolicySnapshot("POL-001", 600, 3)))

  // The repository is available, but String has no lookup method.
  // Missing here: import learning.PolicyNumberSyntax._
  val result = "POL-001".lookup
}
