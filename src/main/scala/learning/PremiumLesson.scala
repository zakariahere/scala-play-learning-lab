package learning

// An object is a singleton: Scala creates its single instance for us.
object PremiumLesson {

  // JVM entry point, comparable to Java's public static void main(String[] args).
  // def declares a method; Array[String] means an array of strings.
  // Unit means this method returns no meaningful result (like Java's void).
  def main(args: Array[String]): Unit = {
    val policyNumber: String = "POL-001"
    val basePremium: Int = 600 // Whole euros, for this fictional example.
    val claimFreeYears: Int = 2 // Try changing this to 2 and running again.

    // The chosen branch produces the value assigned to annualPremium.
    val annualPremium: Int =
      if (claimFreeYears >= 3) {
        val discount: Int = 50
        basePremium - discount // The block's final expression is its result.
      } else {
        basePremium
      }

    // println writes a line to the console, like System.out.println in Java.
    println("Policy: " + policyNumber)
    println("Claim-free years: " + claimFreeYears)
    println("Annual premium: " + annualPremium + " EUR")

    // The same rule, now extracted into a method and called with two inputs.
    println("Method, 3 years: " + calculatePremium(600, 3) + " EUR")
    println("Method, 2 years: " + calculatePremium(600, 2) + " EUR")

    // Classes lesson: two separate instances, each holding its own values.
    val firstPolicy: Policy = new Policy("POL-001", 600, 3)
    val secondPolicy: Policy = new Policy("POL-002", 600, 2)
    val thirdPolicy = new Policy("POL-003", 500, 3)
    println("First policy: " + firstPolicy.number)
    println("First policy premium: " + firstPolicy.annualPremium + " EUR")
    println("Second policy premium: " + secondPolicy.annualPremium + " EUR")
    println("Third policy number " + thirdPolicy.number)
    println("Third policy premium " + thirdPolicy.annualPremium)
    println() // End the line after the print calls above.
    CaseClassLesson.run()
    CompanionLesson.run()
    OptionLesson.run()
  }

  // Parameters have name: Type syntax; the Int after ')' is the result type.
  // '=' introduces the body. Its final expression supplies the returned value.
  def calculatePremium(basePremium: Int, claimFreeYears: Int): Int = {
    if (claimFreeYears >= 3) {
      val discount: Int = 50
      basePremium - discount
    } else {
      basePremium
    }
  }
}
