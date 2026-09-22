# Scala / Play Learning Lab

**From Java and Spring to idiomatic Scala 2, one runnable lesson at a time.**

[![Scala 2.13.18](https://img.shields.io/badge/Scala-2.13.18-DC322F?logo=scala)](https://www.scala-lang.org/)
[![sbt 1.12.15](https://img.shields.io/badge/sbt-1.12.15-1274B8)](https://www.scala-sbt.org/)

A hands-on learning journey through Scala, sbt and eventually Play. Small insurance-policy examples connect familiar Java concepts to Scala's expressions, data modelling and optional values.

**Current stage:** Scala 2 language essentials through traits, implicit parameters and implicit classes; now introducing `Either` for a success value or failure reason. Play 3 and Apache Pekko are future topics, not installed dependencies. All policy data and pricing rules are fictional.

## Run the lab

Install a JDK and [sbt](https://www.scala-sbt.org/1.x/docs/Setup.html), then:

```sh
git clone https://github.com/zakariahere/scala-play-learning-lab.git
cd scala-play-learning-lab
sbt "runMain learning.PremiumLesson"
```

The project pins Scala and sbt independently. You do not need a separate Scala installation. The first run downloads the compiler and build dependencies. This lab has been run with JDK 25.0.2; that is a verified local environment, not a minimum-version requirement.

For a faster edit/run loop, start `sbt` once, enter `runMain learning.PremiumLesson`, edit a source file, and run that command again. Use `exit` to leave. IntelliJ IDEA users can open the root directory as an sbt project with the Scala plugin installed.

## Follow the lessons

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

`PremiumLesson.main` runs the learning examples in order. Each later lesson prints a labelled section; assertions check the expected results. The deliberately failing compiler examples live outside `src/` and are run separately using their linked instructions.

These steps group the runnable material; they are not blog part numbers or a claim of independent mastery. See [progress.md](progress.md) for the detailed session history.

**Resume here:** `Either[String, PolicySnapshot]`, `Left`, `Right` and handling both cases with `match` are introduced and verified. Next: `Either.map`, transforming a successful value while preserving a failure reason; not yet implemented.

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
| `mvn test` | `sbt test` (no separate `src/test` suite yet; run the lesson assertions with `runMain`) |
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
build.sbt                  Project name, version and Scala version
project/build.properties   Pinned sbt version
src/main/scala/learning/   Runnable, commented examples
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
- [ ] Either.map: transform success while preserving the failure
- [ ] Testing and deeper sbt workflows
- [ ] Futures and asynchronous error handling
- [ ] Play routes, controllers, JSON and services
- [ ] Apache Pekko concepts and version-specific integration

The course targets Scala 2.13.18, Play 3 and Apache Pekko. Exact Play and Pekko patch versions will be selected when the framework module is introduced.

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
