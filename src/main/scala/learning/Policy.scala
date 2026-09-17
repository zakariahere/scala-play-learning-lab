package learning

// The primary constructor is declared beside the class name.
// Each val parameter becomes a readable property with no setter.
class Policy(
              val number: String,
              val basePremium: Int,
              val claimFreeYears: Int
            ) {
  // This method uses the values stored on THIS policy instance.
  // No argument list: call policy.annualPremium, not policy.annualPremium().
  def annualPremium: Int = {
    if (claimFreeYears >= 3) {
      val discount: Int = 50
      basePremium - discount
    } else {
      basePremium
    }
  }
}

// Same name and same source file as the class: this is its companion object.
// One singleton factory, capable of creating many separate Policy instances.
object Policy {
  def apply(number: String, basePremium: Int, claimFreeYears: Int): Policy = {
    new Policy(number, basePremium, claimFreeYears)
  }

  // Fictional example: this factory creates a policy with zero claim-free years.
  // A real business would need its own rules for a customer's prior history.
  def withoutClaimFreeHistory(number: String, basePremium: Int): Policy = {
    new Policy(number, basePremium, 0)
  }
}
