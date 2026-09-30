package learning

object GenericClassLesson {
  def run(): Unit = {
    println("--- Generic case classes: one Box, different content types ---")

    // Explicit construction first; the type argument fixes the content type.
    val policyNumberBox: Box[String] = new Box[String]("POL-001")
    val countBox: Box[Int] = new Box[Int](12)
    val number: String = policyNumberBox.value
    val count: Int = countBox.value
    println("String box: " + policyNumberBox)
    println("String value: " + number)
    println("Int box: " + countBox)
    println("Int value: " + count)

    // Case-class companion apply permits construction without new.
    // Then inference lets us omit [String]; a typed assignment checks the result.
    val sameNumber = Box[String]("POL-001")
    val inferred = Box("POL-001")
    val inferredChecked: Box[String] = inferred
    println("Explicit and inferred boxes equal: " + (sameNumber == inferredChecked))

    // Reuse known case-class behavior: field equality and non-mutating copy.
    val changed: Box[String] = policyNumberBox.copy(value = "POL-002")
    println("Copied box: " + changed)
    println("Original box: " + policyNumberBox)

    val policy = PolicySnapshot("POL-001", 600, 3)
    val boxedPolicy: Box[PolicySnapshot] = Box[PolicySnapshot](policy)
    val unboxedPolicy: PolicySnapshot = boxedPolicy.value
    println("Domain box: " + boxedPolicy)
    println("Domain value number: " + unboxedPolicy.number)

    assert(number == "POL-001")
    assert(count == 12)
    assert(inferredChecked == policyNumberBox)
    assert(sameNumber == policyNumberBox)
    assert(!(sameNumber eq policyNumberBox))
    assert(changed.value == "POL-002")
    assert(policyNumberBox.value == "POL-001")
    assert(unboxedPolicy == policy)

    // This tiny Box only holds a value. It has no automatic map/flatMap,
    // absence/error handling, or asynchronous behavior just because it is generic.
  }
}
