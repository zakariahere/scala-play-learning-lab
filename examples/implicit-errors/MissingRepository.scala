package learning.examples

import learning._

// Intentionally fails compilation. An ordinary val is not an implicit candidate.
object MissingRepository {
  def demonstrate(): String = {
    val store: PolicyRepository =
      new InMemoryPolicyRepository(List.empty[PolicySnapshot])

    ImplicitParameterLesson.describe("POL-001")
  }
}
