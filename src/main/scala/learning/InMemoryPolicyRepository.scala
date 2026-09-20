package learning

// Scala uses extends here, where Java would use implements.
// policies is a constructor input, not a public property (no val or var).
class InMemoryPolicyRepository(policies: List[PolicySnapshot])
    extends PolicyRepository {

  // override explicitly marks that we implement the inherited contract.
  // It is optional when implementing an abstract method, but useful for clarity.
  override def find(number: String): Option[PolicySnapshot] =
    policies.find(policy => policy.number == number)
}
