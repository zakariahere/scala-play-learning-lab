# Scala / Play Learning Lab

**From Java and Spring to idiomatic Scala 2, one runnable lesson at a time.**

[![Scala 2.13.18](https://img.shields.io/badge/Scala-2.13.18-DC322F?logo=scala)](https://www.scala-lang.org/)
[![sbt 1.12.15](https://img.shields.io/badge/sbt-1.12.15-1274B8)](https://www.scala-sbt.org/)

A hands-on learning journey through Scala and sbt, ending with asynchronous Scala. Small insurance-policy examples connect familiar Java concepts to Scala's expressions, data modelling and optional values.

**Current stage:** Scala 2 language essentials through traits, implicits, `Either`, `Try`, generics, typed domain errors, introductory variance, Java collection adapters and Future/ExecutionContext basics with non-blocking map/flatMap composition, plus nine named ScalaTest tests covering returned results and thrown exceptions. This cursus ends after asynchronous Scala; Play 3 and Apache Pekko are outside its scope and are not installed dependencies. All policy data and pricing rules are fictional.

## Run the lab

Install a JDK and [sbt](https://www.scala-sbt.org/1.x/docs/Setup.html), then:

```sh
git clone https://github.com/zakariahere/scala-play-learning-lab.git
cd scala-play-learning-lab
sbt "runMain learning.PremiumLesson"
```

The project pins Scala and sbt independently. You do not need a separate Scala installation. The first run downloads the compiler and build dependencies. The latest lesson and test runs used Eclipse Adoptium JDK 21.0.12.1; this is a verified local environment, not a minimum-version requirement.

For a faster edit/run loop, start `sbt` once, enter `runMain learning.PremiumLesson`, edit a source file, and run that command again. Use `exit` to leave. IntelliJ IDEA users can open the root directory as an sbt project with the Scala plugin installed.

## Follow the lessons

The examples below run via `runMain`; the named test suite runs separately:

```sh
sbt test
sbt "testOnly learning.ResultFlowLessonSpec"
sbt "testOnly learning.ExceptionBoundarySpec"
```

ScalaTest 3.2.19 is test-scoped, matching the separate `sbtlearn` project. Nine named tests run across two suites: seven result-flow cases plus a focused returned-`Left` versus thrown-exception comparison. `intercept[NumberFormatException] { ... }` checks that its block throws the expected type and returns the exception for inspection; no exception or a different type fails the test. These are examples, not exhaustive coverage. `sbt test` does not automatically execute assertions inside lesson `run()` methods. See the [AnyFunSuite reference](https://www.scalatest.org/scaladoc/3.2.19/org/scalatest/funsuite/AnyFunSuite.html) and [exception assertion reference](https://www.scalatest.org/scaladoc/3.2.19/org/scalatest/Assertions.html).

| Step | Concept | Read the code |
| --- | --- | --- |
| 1 | `val`, types, `if` expressions and methods | [PremiumLesson.scala](src/main/scala/learning/PremiumLesson.scala) |
| 2 | Classes, constructor properties and instance methods | [Policy.scala](src/main/scala/learning/Policy.scala) |
| 3 | Case classes, value equality, reference identity and `copy` | [CaseClassLesson.scala](src/main/scala/learning/CaseClassLesson.scala), [PolicySnapshot.scala](src/main/scala/learning/PolicySnapshot.scala) |
| 4 | Companion objects, `apply` and named factories | [CompanionLesson.scala](src/main/scala/learning/CompanionLesson.scala) |
| 5 | `Option`, `Some`, `None`, pattern matching, `map` and `getOrElse` | [OptionLesson.scala](src/main/scala/learning/OptionLesson.scala) |
| 6 | Optional lookups with `flatMap` versus nested `Option` | [FlatMapLesson.scala](src/main/scala/learning/FlatMapLesson.scala) |
| 7 | For-comprehensions and their `flatMap`/`map` translation | [ForComprehensionLesson.scala](src/main/scala/learning/ForComprehensionLesson.scala) |
| 8 | Immutable `List`, `map`, `filter` and `find` returning `Option` | [ListLesson.scala](src/main/scala/learning/ListLesson.scala) |
| 9 | List for-comprehensions, guards, two generators, `flatMap` and dependent generators | [ListComprehensionLesson.scala](src/main/scala/learning/ListComprehensionLesson.scala) |
| 10 | Traits as repository contracts, implementations and inherited concrete methods | [TraitLesson.scala](src/main/scala/learning/TraitLesson.scala), [PolicyRepository.scala](src/main/scala/learning/PolicyRepository.scala), [InMemoryPolicyRepository.scala](src/main/scala/learning/InMemoryPolicyRepository.scala) |
| 11 | Multiple parameter lists: `describe(number)(repository)` | [ParameterListsLesson.scala](src/main/scala/learning/ParameterListsLesson.scala) |
| 12 | Implicit parameters: compiler-supplied repository arguments and explicit alternatives | [ImplicitParameterLesson.scala](src/main/scala/learning/ImplicitParameterLesson.scala) |
| 13 | Missing and ambiguous implicit arguments: compiler diagnostics and explicit fixes | [ImplicitSearchLesson.scala](src/main/scala/learning/ImplicitSearchLesson.scala), [compiler examples and instructions](examples/implicit-errors/lesson.md) |
| 14 | Implicit classes: ordinary wrappers, extension-style methods and the required import | [ImplicitClassLesson.scala](src/main/scala/learning/ImplicitClassLesson.scala) |
| 15 | Combining an extension method with an implicit parameter: `policyNumber.lookup` | [ImplicitClassLesson.scala](src/main/scala/learning/ImplicitClassLesson.scala) |
| 16 | Missing syntax import versus missing implicit repository: two compiler errors and independent repairs | [compiler examples and instructions](examples/implicit-errors/lesson.md), [ImplicitClassLesson.scala](src/main/scala/learning/ImplicitClassLesson.scala) |
| 17 | Either: Left carries a failure reason, Right carries a policy; consume both with match | [EitherLesson.scala](src/main/scala/learning/EitherLesson.scala) |
| 18 | Either.map: transform Right, preserve Left; compare with explicit match | [EitherLesson.scala](src/main/scala/learning/EitherLesson.scala) |
| 19 | Either.flatMap: chain a fallible contact lookup, compare nested map results and preserve distinct failure reasons | [EitherLesson.scala](src/main/scala/learning/EitherLesson.scala) |
| 20 | Either for-comprehension: dependent generators, a plain String yield and the equivalent flatMap/map chain | [EitherLesson.scala](src/main/scala/learning/EitherLesson.scala) |
| 21 | Try basics: capture a throwing conversion as Success or Failure, then handle both with match | [TryLesson.scala](src/main/scala/learning/TryLesson.scala) |
| 22 | Try.map: transform Success, skip Failure and capture a non-fatal exception from the callback | [TryLesson.scala](src/main/scala/learning/TryLesson.scala) |
| 23 | Try.flatMap: compose a Try-returning calculation, compare nested map results and preserve failures | [TryLesson.scala](src/main/scala/learning/TryLesson.scala) |
| 24 | Try for-comprehension: dependent generators, a plain String yield and equivalent flatMap/map calls | [TryLesson.scala](src/main/scala/learning/TryLesson.scala) |
| 25 | Try.recover: typed exception patterns, a display fallback and preserving unmatched failures | [TryLesson.scala](src/main/scala/learning/TryLesson.scala) |
| 26 | Try.recoverWith: a Try-returning fallback, explicit match and failed backup outcomes | [TryLesson.scala](src/main/scala/learning/TryLesson.scala) |
| 27 | Option / Either / Try together: explicit result conversion, validation and first-error behavior | [ResultFlowLesson.scala](src/main/scala/learning/ResultFlowLesson.scala) |
| 28 | A named ScalaTest test: arrange / act / assert, real failure output, test versus runMain | [ResultFlowLessonSpec.scala](src/test/scala/learning/ResultFlowLessonSpec.scala) |
| 29 | Named failure-path tests: expected Left values, parsing versus validation and first-error precedence | [ResultFlowLessonSpec.scala](src/test/scala/learning/ResultFlowLessonSpec.scala) |
| 30 | Returned Left versus thrown exception: intercept, type arguments and inspecting the caught exception | [ExceptionBoundarySpec.scala](src/test/scala/learning/ExceptionBoundarySpec.scala) |
| 31 | Testing consolidation: a second valid input, independent expected result and a verified failing/passing assertion | [ResultFlowLessonSpec.scala](src/test/scala/learning/ResultFlowLessonSpec.scala) |
| 32 | Generic methods: Java `<T>` versus Scala `[A]`, explicit type arguments, preserved result types and inference | [GenericMethodLesson.scala](src/main/scala/learning/GenericMethodLesson.scala) |
| 33 | Generic case classes: `Box[A]`, typed fields, explicit construction, companion apply, inference, equality and copy | [Box.scala](src/main/scala/learning/Box.scala), [GenericClassLesson.scala](src/main/scala/learning/GenericClassLesson.scala) |
| 34 | Sealed trait error family, final case classes, field patterns and a real exhaustiveness warning | [PolicyInputError.scala](src/main/scala/learning/PolicyInputError.scala), [SealedErrorLesson.scala](src/main/scala/learning/SealedErrorLesson.scala) |
| 35 | Typed Either errors: explicit lookup/parsing conversion, first-error composition and display-boundary rendering | [TypedResultFlowLesson.scala](src/main/scala/learning/TypedResultFlowLesson.scala) |
| 36 | Invariant Box versus covariant List and read-only box; real assignment/setter compiler errors | [VarianceLesson.scala](src/main/scala/learning/VarianceLesson.scala), [CovariantBox.scala](src/main/scala/learning/CovariantBox.scala), [compiler examples](examples/variance-errors/lesson.md) |
| 37 | Java/Scala collection adapters versus snapshots, shared mutation and unsupported writes | [JavaCollectionLesson.scala](src/main/scala/learning/JavaCollectionLesson.scala) |
| 38 | First Future: explicit ExecutionContext, worker versus caller, eager submission and a bounded console wait | [FutureBasicsLesson.scala](src/main/scala/learning/FutureBasicsLesson.scala) |
| 39 | Future.map: a plain transformation, explicit callback context and one final console wait | [FutureMapLesson.scala](src/main/scala/learning/FutureMapLesson.scala) |
| 40 | Future.flatMap: a Future-returning second step, nested map versus flat composition | [FutureFlatMapLesson.scala](src/main/scala/learning/FutureFlatMapLesson.scala) |
| 41 | Future for-comprehensions: explicit flatMap/map translation, implicit context and dependent creation | [FutureForLesson.scala](src/main/scala/learning/FutureForLesson.scala) |

`PremiumLesson.main` runs the learning examples in order. Each later lesson prints a labelled section; assertions check the expected results. The deliberately failing compiler examples live outside `src/` and are run separately using their linked instructions.

These steps group the runnable material; they are not blog part numbers or a claim of independent mastery. See [progress.md](progress.md) for the detailed session history.

**Resume here:** Future for-comprehension compared with explicit `flatMap`/`map` calls. `implicit val ec` supplies generated argument lists; `yield` returns a plain String inside an overall Future[String]. Four new runMain assertions pass; all nine existing named tests pass separately. Next: failed Futures and propagation through map/flatMap/for before recovery. Guided examples are not evidence of independent learner mastery.

A few results to look for:

```text
Claim-free years: 2
Annual premium: 600 EUR
Ordinary class, equal values: false
Case class, equal values: true
Case class, same instance: false
Found: Some(PolicySnapshot(POL-001,600,3))
Missing: None
Found policy POL-001
Policy not found
```

Try changing `claimFreeYears` in `PremiumLesson.main` from `2` to `3`. The first annual premium changes from 600 to 550 EUR. The other examples supply their own inputs and keep their results.

## A bridge from Java

| Familiar Java idea | Scala example in this lab |
| --- | --- |
| A final local binding | `val` prevents rebinding; it does not freeze a mutable object |
| A ternary expression | `if (...) ... else ...` produces a value |
| A method's return value | The body's final expression supplies the result |
| A static factory | A companion object can expose `apply` or a named factory |
| `equals` versus reference `==` | Scala `==` uses null-safe equality; `eq` compares reference identity |
| Data classes with generated methods | A case class provides equality, `toString` and shallow `copy` |
| `Optional<T>` | `Option[T]`, with `Some(value)` and `None` |
| A wrapper or static helper method | An imported implicit class can provide extension-style syntax |

Scala 2 still permits null. `Option` makes intended absence explicit; it does not ban null throughout a program.

## Maven to sbt: a working reference

These commands run from the repository root. sbt uses tasks and settings rather than Maven's lifecycle phases.

| Maven intent | sbt command |
| --- | --- |
| `mvn compile` | `sbt compile` |
| `mvn test` | `sbt test` (named suites under `src/test/scala`; lesson assertions still run via `runMain`) |
| `mvn clean package` | `sbt clean test package` |
| Run this application's entry point | `sbt "runMain learning.PremiumLesson"` |
| `mvn dependency:tree` | `sbt dependencyTree` |
| Resolve dependencies | `sbt update` |
| `mvn clean install`, targeting Maven local | `sbt clean test publishM2` |
| Publish for other local sbt projects | `sbt publishLocal` (local Ivy repository) |

`package` creates a normal JAR, not a bundled executable JAR. Publishing commands above are references for later lessons; running the lab does not require publishing anything.

There is no single direct replacement for `settings.xml`:

- Project settings and dependencies belong in `build.sbt`, the counterpart in role to `pom.xml`.
- User-wide sbt settings can live in `~/.sbt/1.0/global.sbt`.
- Repository configuration can use project `resolvers` or `~/.sbt/repositories` with the appropriate launcher configuration.
- Credentials are configured separately. Keep credential files and environment secrets out of Git.

See the official [publishing guide](https://www.scala-sbt.org/1.x/docs/Publishing.html), [global settings guide](https://www.scala-sbt.org/1.x/docs/Global-Settings.html), and [repository configuration guide](https://www.scala-sbt.org/1.x/docs/Proxy-Repositories.html). We will work through these responsibilities in dedicated sbt lessons.

## Project layout

```text
build.sbt                  Project settings and test-scoped ScalaTest dependency
project/build.properties   Pinned sbt version
src/main/scala/learning/   Runnable, commented examples
src/test/scala/learning/   Named ScalaTest suites
progress.md                Covered topics and where to resume
```

## Where this is going

- [x] Expressions, methods and classes
- [x] Case classes and companion objects
- [x] Optional values and pattern matching
- [x] `Option.map`, lambdas and defaults
- [x] Composing optional lookups with `flatMap`
- [x] For-comprehensions over Option
- [x] List construction and map
- [x] Collection filtering with Boolean predicates
- [x] Collection searching with find
- [x] List for-comprehensions: one generator, yield and an if guard (`ListComprehensionLesson.scala`)
- [x] Two List generators: `flatMap` + `map`, nested results and combination order
- [x] Dependent List generators: per-policy channels and empty branches
- [x] Traits: `PolicyRepository` contract, in-memory implementation and calling through the trait
- [x] Concrete trait methods: inherited `exists` reuses the implemented `find`
- [x] Multiple parameter lists: `describe(number)(repository)` with explicit arguments
- [x] Implicit parameters: compiler-supplied repository arguments and explicit alternatives
- [x] Missing and ambiguous implicits: [compiler examples and fixes](examples/implicit-errors/lesson.md)
- [x] Implicit classes: explicit wrapper calls, extension-style syntax and the required import (`ImplicitClassLesson.scala`)
- [x] Extension methods with implicit parameters: explicit wrapper, explicit repository and fully inferred calls
- [x] Missing syntax import versus missing implicit repository: separate diagnostics and runnable repairs
- [x] Either: success or a failure reason, starting from the familiar Option lookup and using match
- [x] Either.map: transform success while preserving the failure, compared with match
- [x] Either.flatMap: chain another operation that can return a failure, preserving its reason
- [x] Either for-comprehension: express the same chain with generators and yield
- [x] Try basics: represent success or a captured non-fatal exception, then use match
- [x] Try.map: transform success and capture a non-fatal exception from the transformation
- [x] Try.flatMap: compose with a method that already returns Try
- [x] Try for-comprehension: express the same chain and yield a display label
- [x] Try.recover: choose a fallback for a specific exception
- [x] Try.recoverWith: select a fallback operation that already returns Try
- [x] Option / Either / Try consolidation: lookup, parse, validate and compose
- [ ] Deeper sbt workflows
- [x] First named ScalaTest test and testOnly selection
- [x] Named failure-path tests for the result flow
- [x] Testing thrown exceptions with intercept
- [x] Small testing consolidation with a second valid input and independent expectation
- [x] Generic methods: explicit type arguments and inference
- [x] Generic case classes with typed field access
- [x] Sealed traits and named domain-error values
- [x] Typed domain errors carried by Either
- [x] Introductory invariance/covariance, reference widening and compiler restrictions
- [x] Practical Java collection interoperability: adapters, snapshots and mutation boundaries
- [x] First Future and explicit ExecutionContext; submission versus a bounded console wait
- [x] Future.map with explicit transformation context and no intermediate wait
- [x] Future.flatMap with a Future-returning next step and nested-map comparison
- [x] Future for-comprehensions, generated map/flatMap and implicit ExecutionContext
- [ ] Futures and asynchronous error handling
- [ ] Final synchronous/asynchronous Scala consolidation

The course targets Scala 2.13.18 and ends after asynchronous Scala. Play 3, persistence/integrations and Apache Pekko are possible separate follow-on courses, not requirements for finishing this cursus. Checked items mean introduced with runnable examples, not independently mastered.

## Read along

The companion [Scala/Play blog series](https://blog.zakaria.lu/topics/scala-play) explains the lessons from a Java/Spring developer's perspective.

See [progress.md](progress.md) to pick up the next lesson. The goal is to understand each abstraction through code we can run and change.

## Transform an optional value, then choose a fallback

```scala
result
  .map((policy: PolicySnapshot) => policy.number)
  .getOrElse("No policy number")
```

`map` transforms the contained policy into its number and preserves `None`.
`getOrElse` returns that number or evaluates the fallback when absent.
The types flow from `Option[PolicySnapshot]` to `Option[String]` to `String`.
OptionLesson includes equivalent match expressions so both paths can be compared.

The local Ivy repository mentioned above is a directory of built JARs and dependency
metadata, normally `~/.ivy2/local`. Publishing there lets another sbt project on the
same machine use your library. It does not upload anything to GitHub or Maven Central.
