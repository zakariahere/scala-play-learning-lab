package learningexamples

import learning.{InMemoryPolicyRepository, PolicyRepository, PolicySnapshot}
import learning.PolicyNumberSyntax._

object MissingLookupRepository {
  // An ordinary val is usable explicitly, but is not an implicit candidate.
  val store: PolicyRepository =
    new InMemoryPolicyRepository(List(PolicySnapshot("POL-001", 600, 3)))

  // The extension is available, but its repository argument cannot be inferred.
  val result = "POL-001".lookup
}
