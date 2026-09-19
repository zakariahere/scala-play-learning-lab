# Learning progress

Updated: 2026-09-19

## Covered with runnable examples

- `val` versus `var`, static type inference and expression results.
- Methods with typed parameters and a final-expression return value.
- Classes, primary constructors, readable `val` properties and instance methods.
- Case-class equality, reference identity with `eq`, shallow `copy` and named arguments.
- Companion objects, explicit `apply` and named factories.
- `Option[PolicySnapshot]`, `Some` and `None` for an optional lookup.
- Consuming optional results with `match`; branch-local pattern bindings and result values.

The examples have been compiled and run. Coverage records what has been introduced, not a claim that every topic has been independently mastered.

## Resume here

List construction and map introduced and run. Next: filter using a Boolean predicate to retain matching policies, then find returning Option. Defer List flatMap and for-comprehensions until these are clear.

## Teaching approach

- Small steps anchored in Java/Spring comparisons.
- Runnable code alongside explanations.
- Explain new syntax before using it; avoid constant quizzes.
- Include sbt equivalents for Maven commands and settings.xml responsibilities.
- Suggest a new Scala/Play article when a coherent set of lessons is ready.

## Blog milestones

The [Scala/Play series](https://blog.zakaria.lu/topics/scala-play) accompanies the lab:

1. From Java to Scala with a runnable sbt project.
2. Classes, case classes and what equality means.
3. A policy lookup that might find nothing (draft prepared).

## Repository

Public home: https://github.com/zakariahere/scala-play-learning-lab

The repository contains the runnable learning project and public course notes. Play and Pekko have not been added yet.

## Stack update (2026-09-17)

Target Play 3 with Apache Pekko; retain Scala 2.13.18. This replaces the earlier Akka assumption. Exact Play and Pekko patch versions will be selected when those dependencies are introduced.


## sbt clarification: local Ivy repository (2026-09-17)
- Explained an artifact repository as storage for built JARs and dependency metadata, distinct from Git source storage.
- publishLocal writes to ~/.ivy2/local by default; publishM2 writes to Maven local (~/.m2/repository by default).
- Purpose: let another project on the same machine depend on a locally built library. These local tasks do not upload to a remote service.
- Explanation only; no publishing command executed. Main lesson resumes at Option.map.

## Session 9: Option.map (2026-09-18)
- Added policyNumberWithMatch and policyNumberWithMap side by side in OptionLesson.scala.
- Introduced explicit lambda (policy: PolicySnapshot) => policy.number: contained PolicySnapshot input, String output; map handles Some wrapping and preserves None without calling the lambda.
- Distinguished prior describePolicy returning String from this transformation returning Option[String].
- Verified runMain learning.PremiumLesson: both implementations yield Some(POL-001) for found and None for missing.
- Preserved learner's mine/MyFirstTest.scala. Use explicit runMain to select the lesson when multiple main methods exist.
- Next: clarify lambda/type inference as needed, then getOrElse to choose a default String. Defer flatMap/for-comprehensions. No new blog article yet; accumulate defaults/composition for a coherent next part.

## Session 10: getOrElse (2026-09-18)
- Added policyNumberOrFallback, using an intermediate Option[String] then getOrElse("No policy number") to return a plain String.
- Explained Some returns its contained value; None supplies the fallback, without modifying the original Option. Default expression is evaluated only for None.
- Verified runMain learning.PremiumLesson: Found display: POL-001; Missing display: No policy number.
- Showed equivalent match and chained map/getOrElse in the lesson; preserve optional data until a display/default decision is needed.
- Part 4 now has enough material to suggest: From Pattern Matching to map and getOrElse. Suggested only, not drafted or published.

## Blog update (2026-09-18)
Part 3 is confirmed published. Part 4, From Pattern Matching to map and getOrElse, is saved as a draft with a fresh mascot scene and explanatory diagram. Next lesson remains optional composition and flatMap.

## Session 11: flatMap and optional composition (2026-09-18)
- Added FlatMapLesson.scala, invoked by PremiumLesson; preserved learner edits in OptionLesson and mine/.
- findContactEmail returns Option[String]. map consequently yields Option[Option[String]]; flatMap returns the lookup result directly, matching the explicit Some/None match.
- Three verified scenarios: both found; existing policy without email (explicit POL-002 fixture); missing policy. map outputs Some(Some(email)), Some(None), None. flatMap outputs Some(email), None, None.
- Explained that the callback is skipped on None and flattening loses the distinction between missing policy and missing email. No error modelling deep dive yet.
- runMain learning.PremiumLesson passed, including match/flatMap equivalence assertions. Included in the repository synchronization following this lesson.
- Blog: accumulate the for-comprehension comparison before proposing Part 5; Part 4 remains last-known draft.

Repository synchronization: included the flatMap lesson, updated entry point and README, progress notes, and the learner-owned mine/MyFirstTest.scala practice file unchanged.

## Session 12: for-comprehensions (2026-09-18)
- Added ForComprehensionLesson.scala with contactLabelWithMethods and contactLabelWithFor; entry point runs both.
- Two simple generators translate to outer flatMap and inner map. policy <- result binds a PolicySnapshot; email <- findContactEmail(policy) binds a String; yield builds a String and the overall expression returns Option[String].
- Both bound names are in scope for yield. None skips subsequent callback bodies; no unsafe get, mutation or asynchronous behavior introduced.
- Checked both methods against expected values for present policy/email, missing email (explicit fixture) and missing policy. runMain learning.PremiumLesson passed all six assertions.
- Part 5 now worth suggesting: From flatMap to for-comprehensions: composing optional lookups. Suggested only, no draft or publication.
- Lesson, README and public progress included in the repository synchronization for this session.

Blog milestone (2026-09-19): Part 4 confirmed published. Part 5, From flatMap to For-Comprehensions, saved as a draft with mascot and two diagrams. Learner reports enjoying comprehensions. Next: collections after any remaining questions.


## Session 13: List construction and map (2026-09-19)
- Added ListLesson.scala with three fictional PolicySnapshot values and an explicit one-policy-to-String lambda.
- Explained ordered immutable Scala List, companion factory syntax, type parameter, same-length ordered transformation and preservation of the input list.
- Contrasted Option.map (zero or one value) with List.map (each element); Java Streams comparison uses stream().map(...).toList(), while Scala List.map directly builds the result eagerly.
- Added typed empty List example: List.empty[PolicySnapshot], yielding List() without callback evaluation.
- runMain learning.PremiumLesson passed, including expected ordered numbers and empty-result assertions.
- Learner independently added User case class/companion lookup and Option.map/getOrElse practice in mine/. Inspected and preserved unchanged; compiled with the project. Practice main was not executed in this lesson.
- Next: filter, then find. No new blog proposal yet; accumulate collection operations.
