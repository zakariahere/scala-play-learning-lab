package learning

// Comparable Java data-holder shape: record Box<T>(T value) {}
// [A] belongs to the class; value is a readable val property of type A.
// A is a type parameter, not a runtime value or the type Any.
case class Box[A](value: A)
