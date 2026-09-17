package learning

object CompanionLesson {
  def run(): Unit = {
    println("--- Companion objects ---")

    val explicit: Policy = Policy.apply("POL-004", 600, 3)
    val shorthand: Policy = Policy("POL-004", 600, 3)
    println("Explicit apply premium: " + explicit.annualPremium + " EUR")
    println("Shorthand apply premium: " + shorthand.annualPremium + " EUR")
    println("Same instance: " + (explicit eq shorthand))

    val starter = Policy.withoutClaimFreeHistory("POL-005", 600)
    println("Factory claim-free years: " + starter.claimFreeYears)
    println("Factory premium: " + starter.annualPremium + " EUR")
  }
}
