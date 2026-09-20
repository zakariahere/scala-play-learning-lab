# Learning progress

Updated: 2026-09-20

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

Traits introduced with abstract find and concrete inherited exists. Next: multiple parameter lists using the repository, then implicit parameters and implicit classes. Learner reports understanding the trait contract lesson; preserve pace.

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

## Session 14: List.filter (2026-09-19)
- Extended ListLesson with claimFreeYears >= 3 predicate; true keeps a policy, false excludes it from the result.
- filter returns List[PolicySnapshot], retaining original elements and relative order; original list remains unchanged.
- Contrasted map with the same Boolean function (List(true,false,true)) against filter (POL-001 and POL-003).
- Demonstrated filter then map to get selected numbers and >= 10 producing an empty list.
- runMain learning.PremiumLesson passed; assertions verify exact selected policies/order, predicate results, and no-match case.
- Next lesson: find returning Option; enough material for a collections article after that lesson, not yet proposed.

## Session 15: List.find returning Option (2026-09-19)
- Extended ListLesson with number-based find for POL-002 and POL-999; returns Some(snapshot) or None.
- Contrasted filter (all matches in List) with find (first match in Option). find stops after the first match and does not validate uniqueness.
- Demonstrated claimFreeYears >= 3 returns POL-001 although POL-003 also qualifies; selection follows list order.
- Composed find result with Option.map and getOrElse to display a number or fallback. No unsafe extraction.
- runMain learning.PremiumLesson passed; assertions cover found, missing, first of multiple matches and empty input.
- Part 6 ready to suggest: Scala Collections for Java Developers: map, filter and find. Suggested only; no draft created.


## Session 16: List for-comprehensions and guards (2026-09-19)
- Added ListComprehensionLesson and connected it to PremiumLesson.
- One generator with yield returns List[String], equivalent to List.map.
- Guard selects policies with at least three claim-free years; translates to withFilter followed by map, giving POL-001 and POL-003.
- Distinguished withFilter from filter: deferred selection without a filtered intermediate List; same results here with pure callbacks.
- runMain learning.PremiumLesson passed, including expected order, method equivalence, no matches and empty-input assertions.
- Next: two List generators and flatMap, after this explanation. Accumulate that material before suggesting another article.
- Blog Part 6 was saved as a draft in the preceding session; this lesson does not publish it.


## Session 17: Two List generators and flatMap (2026-09-19)
- Extended ListComprehensionLesson with three policies and two channel labels (email, sms). No notifications are sent.
- Two generators produce six strings in policy-first, channel-second order; compared comprehension to outer flatMap and inner map.
- Contrasted outer map yielding List[List[String]] with flatMap concatenating the per-policy Lists into List[String].
- Empty channel List yields no combinations. Explained independent Lists form every combination, not positional pairing.
- runMain learning.PremiumLesson passed all assertions, including exact combination order, nested groups and empty channels.
- Next: dependent second generator and an empty branch. Part 7 now has a coherent possible angle: List for-comprehensions, guards and flatMap. Suggest only; no draft or publication in this lesson.


## Session 18: Dependent List generators (2026-09-19)
- Added channelsFor(policy): List[String] with fictional fixtures: POL-001 has email/sms, POL-002 none, POL-003 sms.
- Second generator calls channelsFor(policy), using the first bound value; compared directly with flatMap/map.
- Verified ordered result contains three labels; empty middle branch contributes nothing and processing continues for POL-003.
- runMain learning.PremiumLesson passed all assertions. No messages are sent.
- Updated near-term order per workplace needs: traits/repository contract, multiple parameter lists, implicit parameters, implicit classes. Traits and implicits are previewed but not yet taught in depth.
- No new blog proposal: this is a short continuation of Part 7. Next lesson is traits.


## Session 19: Traits as contracts (2026-09-20)
- Added PolicyRepository trait declaring find(number): Option[PolicySnapshot], with no method body.
- InMemoryPolicyRepository extends the trait and implements find using List.find. Explained extends versus Java implements and explicit override (optional for abstract implementation).
- TraitLesson.describe accepts PolicyRepository; runtime object supplies the implementation. Explicit ordinary dependency passing, with no framework wiring.
- Compared declared trait type to actual class, preserving existing PolicySnapshot and earlier lessons.
- runMain learning.PremiumLesson passed: found policy, missing number and empty repository; exact lookup result asserted too.
- Next: concrete trait method, then multiple parameter lists leading to implicit parameters and implicit classes. Do not rush into trait linearization or type classes.
- No new article yet: accumulate shared trait behavior and implementation examples before suggesting a coherent part.


## Session 20: Concrete methods in traits (2026-09-20)
- Learner reports understanding the preceding trait-contract lesson.
- Added PolicyRepository.exists(number): Boolean = find(number).isDefined.
- InMemoryPolicyRepository unchanged: inherits exists, whose find call dispatches to its implementation.
- Compared with Java interface default methods; explained abstract versus concrete, and override required when replacing a concrete method.
- runMain learning.PremiumLesson passed: exists true for known policy, false for missing and empty repository; earlier assertions also passed.
- Next: multiple parameter lists, then implicit parameters. Trait foundations now support a possible Part 8: Scala Traits for Java Developers - Contracts and Shared Behavior. Suggested only, no article created.
