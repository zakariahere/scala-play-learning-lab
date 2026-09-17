package learning

// A data representation, separate from our ordinary Policy for comparison.
// Case-class parameters in this list are val properties by default.
case class PolicySnapshot(number: String, basePremium: Int, claimFreeYears: Int)
