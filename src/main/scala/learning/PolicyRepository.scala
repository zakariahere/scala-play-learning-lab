package learning

// A contract, comparable to a Java interface.
trait PolicyRepository {
  // No body: a concrete implementation must supply this method.
  def find(number: String): Option[PolicySnapshot]

  // A concrete method: implementations inherit this body.
  // find dispatches to the implementation on the current repository object.
  def exists(number: String): Boolean = find(number).isDefined
}
