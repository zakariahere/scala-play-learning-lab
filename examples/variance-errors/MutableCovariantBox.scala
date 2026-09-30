package learning

// Intentionally invalid: var creates both a getter and a setter accepting A.
final class MutableCovariantBox[+A](var value: A)
