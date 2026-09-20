package learning

// A contract, comparable to a Java interface.
trait PolicyRepository {
  // No body: a concrete implementation must supply this method.
  def find(number: String): Option[PolicySnapshot]
}
