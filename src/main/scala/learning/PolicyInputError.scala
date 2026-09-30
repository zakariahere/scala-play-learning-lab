package learning

// The direct subtypes of a Scala 2 sealed trait must be declared in THIS file.
// The trait groups the alternatives; it does not need to declare any methods.
sealed trait PolicyInputError

// final prevents subclasses of these concrete alternatives.
// These are ordinary data values, not Throwable subclasses or thrown exceptions.
final case class PolicyNotFound(number: String) extends PolicyInputError
final case class MalformedInstallments(text: String) extends PolicyInputError
final case class NonPositiveInstallments(count: Int) extends PolicyInputError
