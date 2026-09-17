package learning

object CaseClassLesson {
  def run(): Unit = {
    println("--- Case classes ---")

    val ordinaryA = new Policy("POL-001", 600, 3)
    val ordinaryB = new Policy("POL-001", 600, 3)
    // Scala == calls equals (null-safely). Policy has no custom equals.
    println("Ordinary class, equal values: " + (ordinaryA == ordinaryB))

    // The generated companion apply method constructs these instances.
    val original = PolicySnapshot("POL-001", 600, 3)
    val sameValues = PolicySnapshot("POL-001", 600, 3)
    println("Case class, equal values: " + (original == sameValues))
    // eq compares reference identity: these are still two separate objects.
    println("Case class, same instance: " + (original eq sameValues))
    println("Readable property: " + original.number)

    // A named argument: replace claimFreeYears; keep other current values.
    // copy constructs a new object and leaves original unchanged.
    val renewed = original.copy(claimFreeYears = 4)
    println("Original: " + original)
    println("Renewed: " + renewed)
    println("Original equals renewed: " + (original == renewed))
  }


}
