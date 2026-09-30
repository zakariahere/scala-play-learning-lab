package learning

// +A is declared here, not when constructing or referring to a box.
// If Child extends Parent, CovariantBox[Child] can be used as CovariantBox[Parent].
// val exposes the value for reading; there is no setter.
// Keep the original invariant case class Box[A] unchanged for comparison.
final class CovariantBox[+A](val value: A)
