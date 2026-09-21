package learning.examples

import learning._

// Intentionally fails: two equally suitable candidates in the same scope.
object AmbiguousRepository {
  def demonstrate(): String = {
    implicit val first: PolicyRepository = new InMemoryPolicyRepository(
      List(PolicySnapshot("POL-001", 600, 3))
    )
    implicit val second: PolicyRepository =
      new InMemoryPolicyRepository(List.empty[PolicySnapshot])

    ImplicitParameterLesson.describe("POL-001")
  }
}
