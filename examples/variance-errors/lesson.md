# Invariance and covariance: compiler evidence

These files intentionally fail compilation and remain outside `src/`.
The working comparison is in `VarianceLesson.scala`; the original `Box[A]`
stays unchanged. A separate `CovariantBox[+A]` shows the declared alternative.

## 1. A subtype relationship does not automatically lift through Box

Start `sbt` from the repository root. At its prompt:

```text
set Compile / unmanagedSources += baseDirectory.value / "examples" / "variance-errors" / "InvariantAssignment.scala"
compile
exit
```

Verified with Scala 2.13.18:

```text
type mismatch;
 found   : learning.Box[learning.PolicyNotFound]
 required: learning.Box[learning.PolicyInputError]
Note: learning.PolicyNotFound <: learning.PolicyInputError, but class Box is invariant in type A.
You may wish to define A as +A instead. (SLS 4.5)
```

In this diagnostic, `<:` reads "is a subtype of". The assignment is rejected
before the program runs.

`Box[A]` is invariant by declaration, despite its readable immutable field.
To keep that declaration, construct a new `Box[PolicyInputError]` using the
contained value. That is NOT assigning the old narrower box to a wider type.

`List` declares `+A`, so assigning a `List[PolicyNotFound]` to
`List[PolicyInputError]` is allowed. Our separate read-only
`CovariantBox[+A]` allows the same direction. The `+` is on the type
declaration, not on `new CovariantBox[PolicyNotFound](...)`.

The runnable lesson checks that widening keeps the same List/box instance.
It does not copy the object or change its contents. A wider static type
does not guarantee that every error is specifically PolicyNotFound;
the reverse assignment is not generally valid.

## 2. Why not just put +A everywhere?

Start a fresh sbt process and enter:

```text
set Compile / unmanagedSources += baseDirectory.value / "examples" / "variance-errors" / "MutableCovariantBox.scala"
compile
exit
```

Verified diagnostic:

```text
covariant type A occurs in contravariant position in type A of value value_=
```

A public `var value: A` creates a getter AND a setter accepting `A`.
If a mutable box of PolicyNotFound could be seen as a mutable box of
PolicyInputError, that wider reference could replace its contents with
MalformedInstallments. The original narrow reference would then promise
the wrong kind of error.

This is hypothetical unsafe behavior; Scala rejects the class declaration.
"Contravariant position" here means an input position (the setter parameter).
Designing `-A` consumers and lower-bound method signatures is deferred.
`+A` is a compiler-checked promise, not an automatic consequence of using
`val`; it does not make arbitrary objects deeply immutable.

## Java comparison and normal execution

Java's `List<PolicyNotFound>` is not assignable to
`List<PolicyInputError>` either. A reading API commonly uses
`List<? extends PolicyInputError>`. That wildcard is a use-site choice;
Scala's `List[+A]` declares covariance on the type itself.

The `set` command changes only the running sbt session, not build.sbt.
`Compile / unmanagedSources` lists ordinary main source files; `+=` adds one.
Use a fresh session for each failure and for the normal run. When piping
`compile` followed by `exit` into sbt, the process can exit with code zero
despite the explicit compilation error; inspect the compiler output.

```powershell
sbt "runMain learning.PremiumLesson" test
```

Eight variance assertions execute through runMain; the nine existing named
ScalaTest tests run separately. Neither proves independent learner mastery.

Reference: [Scala variance guide](https://docs.scala-lang.org/tour/variances.html).
