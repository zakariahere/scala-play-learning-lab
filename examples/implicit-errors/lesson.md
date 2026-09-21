# Missing syntax, missing arguments and ambiguous implicits

The four Scala sources in this folder intentionally fail compilation. They live outside `src/`, so the normal lab still compiles. From the repository root, start the sbt shell:

```powershell
sbt
```

At the sbt prompt, enter these lines for the missing case:

```text
set Compile / unmanagedSources += baseDirectory.value / "examples" / "implicit-errors" / "MissingRepository.scala"
compile
exit
```

Start `sbt` again and enter these lines for the ambiguous case:

```text
set Compile / unmanagedSources += baseDirectory.value / "examples" / "implicit-errors" / "AmbiguousRepository.scala"
compile
exit
```

Use a fresh sbt session for each case so the previous failing source is no longer included. Entering the commands at the sbt prompt also avoids Windows launcher argument-quoting issues.

`set` changes the build setting for that session; it does not edit `build.sbt`. `Compile / unmanagedSources` is the list of ordinary source files for the main compilation, and `+=` adds one file to it. `baseDirectory.value` gives the project root, with `/` joining path components.

Verified Scala 2.13.18 diagnostics (paths omitted):

```text
could not find implicit value for parameter repository: learning.PolicyRepository

ambiguous implicit values:
 both value second of type learning.PolicyRepository
 and value first of type learning.PolicyRepository
 match expected type learning.PolicyRepository
```

- MissingRepository has an ordinary `val store`, which is not eligible for implicit search. The implicit inside another method's body is also not available at this call site.
- AmbiguousRepository has two equally suitable `PolicyRepository` values in the same scope. The compiler does not select one by declaration order or inspect which contains the requested policy.
- Both are compile-time errors, before any policy lookup runs. A valid repository returning `None` is a different, runtime outcome.

Explicit fixes are in `ImplicitSearchLesson.scala`: pass the ordinary value, or explicitly select one candidate. Another fix for the missing case is marking the intended value `implicit`; another fix for ambiguity is leaving only the intended candidate eligible in that scope.

## Extension methods: two separate things can be missing

The working call uses two independent compiler conveniences:

```scala
import learning.{InMemoryPolicyRepository, PolicyRepository, PolicySnapshot}
import learning.PolicyNumberSyntax._

implicit val store: PolicyRepository =
  new InMemoryPolicyRepository(List(PolicySnapshot("POL-001", 600, 3)))

val result = "POL-001".lookup
// Some(PolicySnapshot(POL-001,600,3))
```

The syntax import makes the String-to-wrapper conversion available. The implicit
value supplies the repository argument to the wrapper's lookup method. Neither
step changes String or asks a Spring container for a bean.

### 1. Missing syntax import

Start a fresh sbt shell and enter:

```text
set Compile / unmanagedSources += baseDirectory.value / "examples" / "implicit-errors" / "MissingSyntaxImport.scala"
compile
exit
```

Verified Scala 2.13.18 diagnostic:

```text
value lookup is not a member of String
```

The repository exists and is implicit, but that does not make the extension
method available. Restore `import learning.PolicyNumberSyntax._`, or construct
the wrapper explicitly:

```scala
new learning.PolicyNumberSyntax.PolicyNumberOps("POL-001").lookup
```

This explicit wrapper call still lets the compiler supply the implicit repository.
Simply passing `store` to `"POL-001".lookup(store)` does not fix a missing syntax
import: the compiler still needs a way to find lookup on a String.

### 2. Missing implicit repository

Start another fresh sbt shell and enter:

```text
set Compile / unmanagedSources += baseDirectory.value / "examples" / "implicit-errors" / "MissingLookupRepository.scala"
compile
exit
```

Verified Scala 2.13.18 diagnostic:

```text
could not find implicit value for parameter repository: learning.PolicyRepository
```

The syntax import is present, so the compiler can find the extension. However,
`store` is an ordinary val, not an implicit candidate. Mark the intended value
`implicit`, or supply it explicitly:

```scala
"POL-001".lookup(store)
```

Both explicit repairs run in separate methods in `ImplicitClassLesson.scala`.
Separate method bodies ensure one example's local import or implicit value
does not silently fix the other example.

| Outcome | Meaning | When |
| --- | --- | --- |
| lookup is not a member of String | No applicable extension conversion is available in this example | Compilation |
| Could not find implicit repository | The method is available, but its argument cannot be inferred | Compilation |
| None | The call ran with a repository, but no matching policy was found | Runtime |

The first resembles Java's cannot-find-symbol error. The second is a compile-time
argument problem, not a Spring missing-bean failure during application startup.

After any expected failure, run the normal lesson with a fresh process:

```powershell
sbt "runMain learning.PremiumLesson"
```
