package learning

object VarianceLesson {
  // A caller can supply a List of any one of our error subtypes.
  def describeAll(errors: List[PolicyInputError]): List[String] =
    errors.map((error: PolicyInputError) => SealedErrorLesson.describe(error))

  def run(): Unit = {
    println("--- Variance: an element relationship versus a container relationship ---")

    val missing: PolicyNotFound = PolicyNotFound("POL-999")
    val error: PolicyInputError = missing
    println("Ordinary subtype assignment: " + error)

    // List is declared with +A: widening the element type preserves subtyping.
    val missingList: List[PolicyNotFound] = List(missing)
    val errorList: List[PolicyInputError] = missingList
    val messages: List[String] = describeAll(missingList)
    println("List through wider type: " + errorList)
    println("Descriptions: " + messages)

    // Box[A] has no variance annotation: it is invariant.
    val narrowBox: Box[PolicyNotFound] = new Box[PolicyNotFound](missing)
    // val rejected: Box[PolicyInputError] = narrowBox // intentionally fails
    // Constructing a NEW box with a wider type argument is allowed.
    val rebuilt: Box[PolicyInputError] =
      new Box[PolicyInputError](narrowBox.value)
    println("New invariant box, wider content type: " + rebuilt)

    // Compare a different class whose declaration explicitly allows covariance.
    val narrowReadOnly: CovariantBox[PolicyNotFound] =
      new CovariantBox[PolicyNotFound](missing)
    val wideReadOnly: CovariantBox[PolicyInputError] = narrowReadOnly
    val readError: PolicyInputError = wideReadOnly.value
    println("Covariant box through wider type: " + readError)
    println("Same List instance: " + (missingList eq errorList))
    println("Same covariant box instance: " + (narrowReadOnly eq wideReadOnly))

    assert(error == PolicyNotFound("POL-999"))
    assert(errorList == List(PolicyNotFound("POL-999")))
    assert(missingList eq errorList)
    assert(messages == List("Policy not found: POL-999"))
    assert(rebuilt.value == missing)
    assert(!(narrowBox eq rebuilt))
    assert(narrowReadOnly eq wideReadOnly)
    assert(readError == missing)

    // No conversion, copying or mutation occurs during the covariant assignments.
    // The broader static type does not change the actual contained object.
    // These assignments cannot be reversed without additional evidence.
    // A public var value: A would allow unsafe replacement; see compiler examples.
  }
}
