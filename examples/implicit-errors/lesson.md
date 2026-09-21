# Missing and ambiguous implicit arguments

These two sources intentionally fail compilation. They live outside `src/`, so the normal lab still compiles. From the repository root, start the sbt shell:

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

After either expected failure, run the normal lesson with a fresh process:

```powershell
sbt "runMain learning.PremiumLesson"
```
