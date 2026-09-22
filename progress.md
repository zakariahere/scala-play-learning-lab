# Learning progress

Updated: 2026-09-22

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

Introduced an Either for-comprehension over the existing policy/contact chain, beside its flatMap/map equivalent. policy is a PolicySnapshot, email is a String, and yield builds a plain String inside an Either[String, String]. Verified the success label and both distinct Left reasons with six assertions; examples are assistant-verified, not independent learner mastery. The planned Either article scope is now complete and worth proposing; live blog state and numbering must be verified before drafting. Next language lesson: Try basics, wrapping an operation that can throw, with Success/Failure and a Java try/catch comparison. Defer recovery/composition, typed error hierarchies, type classes and deeper implicit-search precedence.

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


## Session 21: Multiple parameter lists (2026-09-20)
- Added ParameterListsLesson.describe(number)(repository), preserving TraitLesson.describe(number, repository) for comparison.
- Explained one method, two parameter lists, both names available in the body, and explicit arguments at this stage.
- runMain learning.PremiumLesson passed: same found result for both signatures, missing result and explicit empty repository.
- Next: implicit parameter list and an implicit val; explain compile-time argument insertion using the same example. Defer partial application and currying terminology.
- Blog numbering: user-written sbt article is Part 8; traits is Part 9 (saved as draft in prior session). Next new article is Part 10. Accumulate implicit-parameter material before suggesting it.


## Session 22: Implicit parameters (2026-09-21)
- Added ImplicitParameterLesson with implicit PolicyRepository parameter list and ordinary method body.
- localStore is an implicit val; name intentionally differs from repository to show this is type-directed argument search.
- Compared describe(number)(localStore) with describe(number); explicit emptyStore still selects a different repository.
- Explained compile-time argument insertion, distinct from runtime Spring container lookup. Ordinary val alone is not an implicit candidate.
- runMain learning.PremiumLesson passed: explicit/inferred found results, missing policy, explicit empty repository; prior assertions also passed.
- Next: demonstrate missing/ambiguous candidates using compiler diagnostics before implicit classes. Do not equate a missing policy with a missing implicit dependency.
- Blog: accumulate search/error material before suggesting Part 10; Part 8 is the user's sbt article and Part 9 is traits.


## Session 23: Missing and ambiguous implicit candidates (2026-09-21)
- Added intentionally failing examples outside src under examples/implicit-errors, plus reproducible sbt-shell instructions and verified diagnostics.
- MissingRepository has ordinary val store, not an implicit candidate. Compiler reports could not find implicit value for repository.
- AmbiguousRepository has two equally suitable PolicyRepository implicit vals in the same scope; compiler reports both candidates. Declaration order/data contents do not resolve this ambiguity.
- Added runnable ImplicitSearchLesson: ordinary value passed explicitly, and second candidate explicitly selected despite two implicit values in scope.
- Both expected compilation failures verified; subsequent normal runMain learning.PremiumLesson passed all assertions. sbt set modifications were session-only; build.sbt unchanged.
- Windows launcher stripped quotes from initial command-line set; documented sbt interactive commands instead, which were verified via standard input.
- Next: implicit classes. Part 10 now worth suggesting: Scala 2 Implicit Parameters - What the Compiler Supplies, and Why It Refuses. No draft or publication in this lesson.

## Session 24: Implicit classes (2026-09-21)
- Added PolicyNumberSyntax.PolicyNumberOps, an implicit class wrapping one String and defining asPolicyLabel.
- Demonstrated the ordinary equivalent first: new PolicyNumberSyntax.PolicyNumberOps(policyNumber).asPolicyLabel.
- Imported PolicyNumberSyntax._ before the extension-style policyNumber.asPolicyLabel call; the import makes the implicit wrapper conversion eligible in that lexical scope.
- Explained the Scala 2 compiler rewrite as wrapper construction followed by the same method call. The original String class is not modified.
- Java comparison: an explicit wrapper or static utility remains visible in Java; Scala 2 can offer fluent call syntax through an imported implicit conversion.
- runMain learning.PremiumLesson passed and both forms asserted the exact result Policy POL-001.
- Status: syntax and mechanism introduced and execution verified by the assistant; learner practice or demonstrated mastery has not yet occurred.
- Blog: Part 10 already covers the preceding implicit-parameter material. This single lesson is not enough for Part 11, so no draft or publication was created.

## Session 25: Extension method with an implicit parameter (2026-09-21)
- Learner said "GOT IT" after the expanded wrapper-first explanation. Record self-reported understanding of implicit classes, not independent demonstrated mastery.
- Teaching preference clarified: do not use llm-teacher. Explain from familiar explicit code, trace concrete values, then introduce the shortcut. Keep housekeeping brief and avoid routine quizzes.
- Extended PolicyNumberOps with lookup(implicit repository: PolicyRepository): Option[PolicySnapshot] = repository.find(number).
- Separated the two compiler conveniences: String-to-wrapper conversion makes lookup available; argument insertion supplies the repository to that method.
- Compared new PolicyNumberSyntax.PolicyNumberOps(policyNumber).lookup(localStore), policyNumber.lookup(localStore), and policyNumber.lookup.
- Verified exact Some(PolicySnapshot("POL-001", 600, 3)) for all three forms; missing number and explicit empty repository yield None. The wrapper stores the number; the repository is a method argument, not stored in this wrapper.
- sbt "runMain learning.PremiumLesson" passed all new and existing assertions on Scala 2.13.18. Preserved the learner's uncommitted claimFreeYears = 3 edit.
- New combined example is introduced and assistant-verified; no learner practice or independent mastery claimed.
- Blog readiness: enough coherent material to propose Part 11, "Scala 2 Implicit Classes: Where Did That Method Come From?" Scope: Java wrapper equivalent, extension syntax/import, compiler conversion, and combining an extension with an implicit repository argument. Next lesson's two missing-input diagnostics would strengthen it.
- Suggested only: no blog draft or publication. Last recorded Part 10 draft is 1f26a27c-fd2c-4e6f-b6dd-e382d0b1285d; live blog state must be verified before any article creation. Preserve Part 8 sbt, Part 9 traits, Part 10 implicit parameters, Part 11 next.

## Scala/Play Part 11 blog draft (2026-09-21)
- Live admin verified Part 10 published and no existing Part 11 before creating the draft.
- Saved and read back draft 4b4540f3-c58d-46e4-82b7-c66f81e9f385: "Scala/Play, Part 11: Scala 2 Implicit Classes - Where Did That Method Come From?"
- Slug: scala-play-part-11-scala-2-implicit-classes-where-did-that-method-come-from. Review at https://zakaria.lu/#blog-admin. Update this existing post rather than creating a duplicate.
- Explains the ordinary wrapper first, the syntax import and generated conversion, then the implicit repository argument; includes the three verified calls from source commit 7b43025.
- Branded cover, fresh canonical mascot science-museum scene and two explanatory diagrams reviewed and uploaded; all four media responses HTTP 200. All 11 code blocks verified unchanged in readback.
- Local artifacts: C:/Users/Zakaria/Documents/Codex/2026-09-21/referenced-chatgpt-conversation-this-is-an/outputs/scala-play-11. Desktop/mobile preview checked.
- Target Scala/Play hub adopts the scala-play tag. Existing hub blurb still mentions Akka and nextUp still refers to classes/constructors; noted for a separate registry change, not edited or deployed here.
- Draft only; no publication. Next new article number is 12. Next lesson remains missing syntax import versus missing repository in the combined example.

## Session 26: Missing extension syntax versus missing repository (2026-09-21)
- Added MissingSyntaxImport.scala and MissingLookupRepository.scala outside src, under examples/implicit-errors; normal compilation excludes them.
- Verified each in a fresh sbt session with a session-only unmanagedSources addition on Scala 2.13.18.
- MissingSyntaxImport has an implicit repository but no syntax import: compiler reports "value lookup is not a member of String".
- MissingLookupRepository imports the syntax but has an ordinary val store: compiler reports "could not find implicit value for parameter repository: learning.PolicyRepository".
- Extended ImplicitClassLesson with separate-method repairs: explicitly construct the wrapper while inferring its repository, or use extension syntax while passing an ordinary repository explicitly.
- Both repairs return Some(PolicySnapshot(POL-001,600,3)); sbt "runMain learning.PremiumLesson" passed all new and existing assertions.
- Distinguished both compilation failures from a valid repository lookup returning None at runtime. Java comparison: unknown method versus missing method argument; not Spring runtime container resolution.
- Updated the compiler-example instructions and README lesson index (step 16). No build settings or dependencies changed.
- Status: introduced and assistant-verified only. No learner exercise or independently demonstrated mastery claimed.
- Synced main with origin/main before edits. Preserved the learner's uncommitted claimFreeYears = 3 practice edit, excluded from the lesson commit.
- Next: Either, retaining a failure reason where Option only expresses absence; part of the existing everyday-functional-Scala roadmap. Do not introduce type classes or deeper implicit precedence yet.
- Blog: useful follow-up material for existing Part 11, not enough distinct material for Part 12. No blog state checked, draft edited, article created or publication performed. Verify live state before a future blog action; next new article number remains 12.

## Session 27: Either basics - a policy or a failure reason (2026-09-22)
- Added EitherLesson.findPolicy returning Either[String, PolicySnapshot], reusing OptionLesson.findPolicy through an explicit match.
- Explained the two type arguments in order: Left payload String, Right payload PolicySnapshot; one alternative per result, not both.
- Followed the conventional Left = failure and Right = success interpretation. Either is general-purpose; its left type need not be an exception.
- Some(policy) becomes Right(policy); None becomes Left("Policy not found: " + number). This message is explicitly chosen by our code, not recovered from hidden information inside None.
- Added describePolicy with Right(policy)/Left(reason) patterns; bindings have types PolicySnapshot/String, and each branch returns String.
- Compared Option's None with Left(Policy not found: POL-999). Found case is Right(PolicySnapshot(POL-001,600,3)).
- Left is ordinary returned data, not throw; Either does not automatically catch exceptions. Option remains suitable where absence needs no reason.
- Ran sbt "runMain learning.PremiumLesson" successfully on Scala 2.13.18; all four new assertions and existing assertions passed.
- Added the run invocation and README lesson step 17. Synced main first; retained the learner's claimFreeYears = 3 edit separately from the lesson changes.
- Status: introduced and assistant-verified, not independently practiced or mastered by the learner.
- Next: Either.map, with an explicit match equivalent, transforming the Right policy into its number while preserving Left. No composition, Try or typed error hierarchy yet.
- Blog: beginning a new coherent error-modelling topic; this first Either lesson alone is too thin for Part 12. Accumulate transformation/composition examples before suggesting an article. No blog reads, drafts or publication this turn; verify live blog state and numbering before any future article action.

## Session 28: Either.map - transform success, preserve failure (2026-09-22)
- Added policyNumberWithMatch and policyNumberWithMap to EitherLesson, each taking Either[String, PolicySnapshot] and returning Either[String, String].
- Explained the explicit branches first: Right(policy) becomes Right(policy.number); Left(reason) retains the same reason.
- The typed lambda (policy: PolicySnapshot) => policy.number returns a plain String, not Right(String). map handles the Right wrapping.
- Explained right-biased map in Scala 2.13: it transforms Right, not Left, even when both payload types are String. Compared with familiar Option.map and Java Optional.map.
- Verified match/map equivalence for both cases: Right(POL-001) and Left(Policy not found: POL-999). Asserted the original successful result still contains its unchanged PolicySnapshot.
- Added a guard assertion inside a Left.map callback; the full run succeeds, confirming the callback is skipped. The final result retains the original failure reason.
- sbt "runMain learning.PremiumLesson" passed all existing and new assertions. Updated README step 18 and resume point.
- Synced main with origin/main before editing. The learner's uncommitted claimFreeYears = 3 practice edit remains untouched and excluded from this lesson commit.
- Status: introduced and assistant-verified only; no independent learner practice or mastery claimed.
- Next: Either.flatMap, using a second operation that returns Either; compare with nested Either from map before introducing a for-comprehension.
- Blog plan discussed with learner: Part 12 working title "Scala Either for Java Developers: A Result or a Reason", building from motivation through map, flatMap and a for-comprehension. Accumulate the composition examples first; no article created or edited and no live blog state checked this turn. Verify state and numbering before drafting.

## Session 29: Either.flatMap - chain a second fallible lookup (2026-09-22)
- Added findContactEmail returning Either[String, String], with the same fictional contact data as the earlier Option lesson: POL-001 has customer@example.com; other supplied policies have no contact email.
- Added emailWithMap, emailWithMatch and emailWithFlatMap. The explicit match returns the second lookup's Either directly on Right and preserves the first failure on Left.
- Compared map's Either[String, Either[String, String]] with flatMap's Either[String, String]. map wraps the callback's entire result in Right; flatMap uses that returned Either without another wrapper.
- Verified all three scenarios: Right(Right(email)) versus Right(email); Right(Left(contact reason)) versus Left(contact reason); initial Left(policy reason) preserved by both.
- POL-002 is explicitly a direct Right(PolicySnapshot(...)) fixture for an existing policy without email, not an invented result of the existing findPolicy method, which still only finds POL-001.
- Asserted the exact six outputs and match/flatMap equivalence in all three cases. A guard assertion in the initial Left.flatMap callback verified that the contact lookup is skipped.
- sbt "runMain learning.PremiumLesson" passed all existing and new assertions. Updated README lesson index through step 19 and the resume point.
- Synced main before edits; preserved and excluded the learner's uncommitted claimFreeYears = 3 edit. No dependency or build-setting changes.
- Status: introduced and assistant-verified only; no learner exercise or independent mastery claimed.
- Next: Either for-comprehension over this same policy/contact chain, compared with flatMap/map; yield a display label. Do not add typed errors or Try in that lesson.
- Blog: the Either material now has a coherent practical core (motivation, match, map, flatMap, distinct failure reasons). The planned for-comprehension comparison will complete the proposed Part 12 scope. No article action this turn; verify live state and numbering before offering or preparing the draft.

## Session 30: Either for-comprehension - the same chain in readable syntax (2026-09-22)
- Added contactLabelWithMethods and contactLabelWithFor to EitherLesson, reusing the same policy and contact-email results.
- Explained the two generators: policy <- result binds a PolicySnapshot on Right; email <- findContactEmail(policy) binds a String on Right and depends on the first bound value.
- yield builds the plain String policy.number + " -> " + email. The whole expression returns Either[String, String], not a plain String or nested Either.
- Showed the corresponding outer flatMap and inner map with typed lambdas, matching the earlier Option for-comprehension lesson.
- An initial Left skips the contact lookup and yield; a Left from the contact lookup skips yield and remains the result. No exception catching, loops over a collection, guards or asynchronous work introduced.
- Verified six assertions: exact successful label Right(POL-001 -> customer@example.com), exact contact/policy Left reasons, and equivalence of both implementations for all three scenarios.
- POL-002 remains the explicit existing-policy fixture from Session 29; did not change findPolicy or invent additional repository data.
- sbt "runMain learning.PremiumLesson" passed all existing and new assertions. Updated README through step 20 and both resume points.
- Synced main before edits and left the learner's claimFreeYears = 3 practice change untouched and outside the focused lesson commit.
- Status: introduced and assistant-verified, not independently practiced or mastered by the learner.
- Next language lesson: Try basics, Success/Failure and a Java try/catch comparison for a throwing operation. Defer recovery/composition and typed error hierarchies.
- Blog readiness: the planned Either scope is now complete: motivation, Left/Right, match, map, flatMap, distinct failure reasons and equivalent for-comprehension. Worth proposing "Scala Either for Java Developers: A Result or a Reason", provisionally Part 12 pending live-state verification. No live blog read, draft creation/edit or publication this turn; verify existing posts and numbering before any drafting action.
