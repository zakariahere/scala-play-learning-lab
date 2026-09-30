package learning

// Intentionally outside src: add this file only for the compiler demonstration.
object InvariantAssignment {
  val missing: Box[PolicyNotFound] =
    new Box[PolicyNotFound](PolicyNotFound("POL-999"))

  val errors: Box[PolicyInputError] = missing
}
