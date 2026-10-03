# Learning progress

Updated: 2026-10-03

## Course finish line (user decision 2026-09-29)

This cursus ends after asynchronous Scala. The agreed sequence, including testing basics, practical generics, typed errors, introductory variance/Java interop, Future composition/recovery/blocking boundaries and final consolidation, was completed with runnable examples on 2026-10-03. This records introduced and verified coverage, not independently assessed mastery. Deeper sbt and Scala topics are optional; Play, persistence and Pekko are separate possible follow-on courses. Historical stack notes below do not override this decision.

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

Completed the agreed Scala 2 teaching sequence with ScalaConsolidationLesson: one shared typed business flow runs synchronously or after a scheduled repository lookup. Future[Either[PolicyInputError, String]] distinguishes a returned business Left from a failed Future. Six input scenarios are independently checked against expected values in both versions; technical failure, explicit alternate-repository recovery, preserved Left and display mapping bring the total to 16 executed lesson assertions plus one skipped guard. All nine existing named tests pass separately. This completes introduced and assistant-verified course coverage, not independently assessed mastery. No required lessons remain in this cursus; optional practice or a separately requested Play/Pekko course may follow. Contravariance, complex bounds and additional interop APIs are deferred. This cursus ended after async and final consolidation, with frameworks outside scope. Prior AnyFunSuite code was AI-generated: do not infer understanding from files or checked progress boxes; explain unfamiliar testing syntax from first principles. Defer fixtures, mocks and matcher DSLs. On 2026-10-03, verified Part 15 published (public HTTP 200) and saved Part 16, Asynchronous Scala Without Blocking, as draft 015b60b5-9887-438d-8a42-20109f4f21ee. Its slug is scala-play-part-16-asynchronous-scala-without-blocking. Update/publish that existing draft rather than creating a duplicate; publication still needs explicit authorization. Next new article number is provisionally 17, subject to fresh verification. Keep type classes and deeper implicit-search precedence for later.

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

## Scala/Play Part 12 blog draft (2026-09-22)
- User invoked blog-article with zakaria-mascot. Verified Part 11 published and no existing Either/Part 12 article before creation.
- Saved and read back draft 510945ab-bcc0-41a4-bb51-dd840f19957b: "Scala/Play, Part 12: Scala Either for Java Developers - A Result or a Reason".
- Slug: scala-play-part-12-scala-either-for-java-developers-a-result-or-a-reason. Review at https://zakaria.lu/#blog-admin; public URL returns 404 while draft. Update this ID rather than POSTing another copy.
- Explains Left/Right, match, map, nested Either, flatMap and equivalent for-comprehensions using verified methods from source commit da4cdcb. Distinct failure reasons and the direct POL-002 fixture are explicit.
- 811 words of prose plus 14 code/output blocks. Linked the Scala 2.13.18 API reference and pinned runnable source. Re-ran the full lab successfully.
- Reviewed Systems in Motion cover, fresh canonical mascot parcel-depot illustration, map/flatMap diagram and for-comprehension translation diagram. Desktop/mobile preview checked; corrected the second diagram height before uploading.
- Readback verified draft status, complete text and all 14 code blocks unchanged. All four exact reviewed media uploads return HTTP 200 image/png.
- Local deliverables: C:/Users/Zakaria/Documents/Codex/2026-09-21/referenced-chatgpt-conversation-this-is-an/outputs/scala-play-12 (preview, payload, readback, graphics and generation prompt).
- The existing Scala/Play hub adopts scala-play tags. Registry and live hub still contain stale Akka wording and classes/constructors nextUp; no registry edit or deployment performed.
- No publication. Next new article is Part 13, subject to fresh verification. Next lesson remains Try basics; no additional mastery claim. Preserved uncommitted practice code.

## Session 31: Try basics - capture a throwing conversion (2026-09-24)
- Added TryLesson with import scala.util.{Failure, Success, Try}, parsePremium(text): Try[Int] = Try(text.toInt), and describePremium using match.
- Explained Success as carrying the Int and Failure as carrying the actual Throwable. Compared with familiar Java Integer.parseInt and try/catch; Try captures non-fatal exceptions, not every possible Throwable.
- Try evaluates the supplied expression at the call, within its exception-handling boundary. It is neither an asynchronous task nor a wrapper that can retroactively catch an earlier failed computation.
- Verified "600" yields Success(600), "hello" yields Failure(NumberFormatException), and execution continues. The match branches produce "Parsed premium: 600 EUR" or "Cannot parse premium: NumberFormatException".
- Six assertions also cover empty text, integer overflow and successful parsing of -1. Conversion success is not business validation; assertions avoid relying on JVM exception-message wording.
- sbt "runMain learning.PremiumLesson" passed all existing and new assertions on Scala 2.13.18, sbt 1.12.15 and the currently selected Eclipse Adoptium Java 21.0.12.1. Did not change the user's JDK configuration.
- Added TryLesson.run to the entry point and README step 21. Preserved the uncommitted claimFreeYears = 3 change; stage only the new invocation, not the learner's edit.
- Synced main with origin/main before edits. Status: introduced and assistant-verified; no learner practice or independent mastery claimed.
- Next: Try.map, transforming Success and capturing a non-fatal exception from the transformation, compared with the earlier Either behavior. No recovery or flatMap lesson yet.
- Blog: this opening Try lesson alone is too thin for a standalone Part 13. Accumulate relevant transformation/composition or recovery material before proposing a scope. No blog state checked and no article action performed; Part 12's recorded draft status may have changed.

## Session 32: Try.map - capture a throwing transformation (2026-09-24)
- Added installmentWithMatch and installmentWithMap taking Try[Int] and an explicit installments divisor. Used whole-euro integer division for the tiny example, not a production payment schedule.
- The typed callback (premium: Int) => premium / installments returns Int. Try.map wraps a normal return in Success and captures a thrown non-fatal exception as Failure.
- The explicit match uses case Success(premium) => Try(premium / installments), not Success(...), because construction of Success does not catch an exception during argument evaluation.
- Verified 600 / 12 gives Success(50), a pre-existing NumberFormatException is preserved, and 600 / 0 gives Failure(ArithmeticException). Parsing 600 succeeds before the division fails.
- Added describeInstallment, keeping conversion-error wording separate from calculation-error wording.
- Nine new assertions verify the normal outcome, match/map behavior, preserved existing failure, exception class on both throwing paths, skipped callback and unchanged original Success(600).
- Compared exception class names for independently thrown errors rather than equality between different Throwable objects. Callback guard additionally requires the original failure to survive, since Try.map would capture an assertion error if wrongly invoked.
- sbt "runMain learning.PremiumLesson" passed all assertions on Scala 2.13.18, sbt 1.12.15 and Eclipse Adoptium Java 21.0.12.1.
- README updated through step 22; main synchronized before edits. Preserved and excluded the learner's uncommitted claimFreeYears = 3 practice edit.
- Status: introduced and assistant-verified only; no learner exercise or independent mastery claimed.
- Next: Try.flatMap with a second method returning Try, versus nested Try from map. Defer recovery until composition is clear.
- Blog: accumulate composition/recovery material for a coherent Try article rather than repeating the Either article with renamed wrappers. No blog state check, draft or publication; next number remains provisionally 13 pending live verification.

## Session 33: Try.flatMap - compose a Try-returning calculation (2026-09-24)
- Added calculateInstallment(premium, installments): Try[Int] = Try(premium / installments); the callback now returns Try[Int], unlike the prior plain-Int callback.
- Added installmentWithNestedMap returning Try[Try[Int]], installmentWithFlatMap returning Try[Int], and installmentResultWithMatch returning this particular helper's Try directly.
- Verified map yields Success(Success(50)), Success(Failure(ArithmeticException)) and the original Failure(NumberFormatException); flatMap yields Success(50), Failure(ArithmeticException) and the original parse Failure.
- Explained that returning a Failure value is not throwing an exception. The inner Try catches division by zero, so the outer map successfully wraps the returned Failure.
- Eleven new assertions cover nested/flat results, explicit-match agreement, preservation of the original parse failure, skipped flatMap callback and non-fatal exception capture when the callback throws before returning a Try.
- The explicit-match example is equivalent for calculateInstallment, which already captures division exceptions; it is not presented as a general replacement for flatMap's callback exception handling.
- sbt "runMain learning.PremiumLesson" passed all existing and new assertions on Scala 2.13.18, sbt 1.12.15 and Eclipse Adoptium Java 21.0.12.1.
- Updated README through step 23 and both resume points. Synced main before editing and preserved the learner's uncommitted claimFreeYears = 3 practice change, excluded from this commit.
- Status: introduced and assistant-verified, not independent learner practice or mastery.
- Next: Try for-comprehension using the same parse/calculation chain, with flatMap/map translation and a plain String yield. Do not introduce recovery in that same small lesson.
- Blog: the Try material now includes exception boundaries and returned-failure versus thrown-exception behavior. Accumulate the readable-chain/recovery comparison before proposing the complete article scope. No blog state checked and no article action; numbering remains provisional until live verification.

## Session 34: Try for-comprehension - the same dependent chain (2026-09-24)
- Added installmentLabelWithMethods(text, installments): Try[String] and installmentLabelWithFor with the same return type.
- The first generator binds premium: Int from parsePremium; the second binds amount: Int from calculateInstallment and depends on premium.
- yield constructs the plain String "600 EUR / 12 = 50 EUR"; the final map wraps it in Success. No extra Success(...) or Try(...) is needed around the yielded label.
- Compared the for-comprehension with the explicit outer flatMap/inner map; both names remain available in the label expression.
- Verified six assertions: exact successful label and success equivalence, plus NumberFormatException and ArithmeticException for both implementations. Compared failure class names because independently evaluated operations create distinct Throwable objects.
- An initial Failure skips the dependent calculation and yield; a calculation Failure skips yield. The for syntax does not add a separate catch mechanism; behavior comes from the underlying Try operations.
- Initial run exposed two Scala 2.13 Int.+(String) deprecation warnings. Confirmed with session-only scalacOptions += "-deprecation", then used premium.toString before concatenation. Final full compile/run passes with no compiler warnings; build.sbt unchanged.
- sbt "runMain learning.PremiumLesson" passed all existing and new assertions on Scala 2.13.18, sbt 1.12.15 and Eclipse Adoptium Java 21.0.12.1.
- README updated through step 24 and both resume points updated. Synced main before edits; preserved and excluded claimFreeYears = 3 practice change.
- Status: introduced and assistant-verified, not independently practiced or mastered by the learner.
- Next: Try.recover with one specific exception and fallback value. Explain the typed case pattern before combining it with recovery; unmatched failures stay failures. Defer recoverWith.
- Blog: readable Try composition now covered; add the planned targeted recovery lesson to round out a distinct exception-handling article. No blog state checked or article action performed; provisional next number 13 requires live verification.

## Session 35: Try.recover - a targeted display fallback (2026-09-24)
- Added labelRecoveryWithMatch and labelWithRecovery to TryLesson; both accept and return Try[String].
- Explained case Failure(_: NumberFormatException) first: the typed pattern selects that exception type (including subtypes), while _ means the exception needs no local name. Compared with selecting a Java catch clause.
- recover receives the exception contained in Failure, not the Failure wrapper. Its selected handler returns a plain String, which recover wraps in Success.
- Verified the existing successful label is unchanged, NumberFormatException produces Success("Premium unavailable"), and ArithmeticException from division by zero remains a Failure.
- The fallback is display text, not a fabricated premium or installment. Recovery does not retry the calculation, mutate the original failure or unwrap the final Try.
- Nine assertions verify all three outcomes, equivalence with the explicit match, the original parse failure remaining intact, and skipped handlers for success and unmatched exceptions.
- sbt "runMain learning.PremiumLesson" passed all existing and new assertions without compiler warnings on Scala 2.13.18, sbt 1.12.15 and Eclipse Adoptium Java 21.0.12.1.
- Updated README through step 25 and both resume points. Synchronized main before edits; preserved and excluded the learner's claimFreeYears = 3 practice change.
- Status: introduced and assistant-verified only; no independent learner practice or mastery claimed.
- Next: Try.recoverWith, selecting a fallback operation that already returns Try. Not implemented or taught in this lesson.
- Blog readiness: sufficient coherent material to propose "Scala Try for Java Developers: Capture, Compose and Recover" covering exception boundaries, map/flatMap, for-comprehensions and targeted recovery. recoverWith can add a short companion comparison but is not required to justify an article. No blog action or live state check; verify existing posts and provisional Part 13 numbering first.

## Scala/Play Part 13 blog draft (2026-09-24)
- User invoked blog-article with zakaria-mascot. Verified Part 12 is published and no existing Try/Part 13 article before creation.
- Saved draft 3a04344a-0bf7-4656-8ea5-54972cee5e42: "Scala/Play, Part 13: Scala Try for Java Developers - Capture, Compose and Recover".
- Slug: scala-play-part-13-scala-try-for-java-developers-capture-compose-and-recover. Review at https://zakaria.lu/#blog-admin. Update this ID rather than creating another draft.
- Covers completed Try lessons only: exception boundary, Success/Failure, explicit match, map, nested Try versus flatMap, for-comprehension translation, and targeted recover. Does not introduce recoverWith or asynchronous behavior.
- 808 prose words plus 12 code blocks, pinned to runnable TryLesson source commit 9f8061c. Re-ran the full lab successfully with Scala 2.13.18, sbt 1.12.15 and Java 21.0.12.1; no warnings.
- Used Systems in Motion templates for the cover and two explanatory diagrams. Generated a new canonical mascot railway-switch scene with the built-in image tool and both approved references; identity/style visually reviewed.
- Desktop and 390px mobile previews checked. All four local images load; no page-width or figure overflow. Admin readback verified draft status, full text and all 12 code blocks unchanged; all four uploaded PNG assets return HTTP 200 image/png.
- Local deliverables: C:/Users/Zakaria/Documents/Codex/2026-09-21/referenced-chatgpt-conversation-this-is-an/outputs/scala-play-13 (preview, payload, readback, source templates, images, prompt and draft details).
- Target hub: Scala/Play, which adopts scala-play tags. Registry and live hub still have stale Akka wording and classes/constructors nextUp; no site edits or deployment authorized or performed.
- No publication and no new learner mastery claim. Next lesson remains recoverWith; next new article number is provisionally 14, requiring fresh live verification. Preserved uncommitted practice code.

## Session 36: Try.recoverWith - a fallback that can fail (2026-09-29)
- Added labelWithBackupMatch and labelWithBackup, both accepting Try[String], an explicitly supplied backup text and an installment count.
- Explicit match first: Success is kept, NumberFormatException selects installmentLabelWithFor on the backup, and other failures stay failures.
- recoverWith uses the handler's Try[String] directly; recover's prior handler returned a plain String. No extra Success wrapper, unwrapping or guaranteed successful fallback.
- Backup text represents an explicit alternative for the same fictional premium, not an invented charge. This is not automatic retry, persistence, network access or async work.
- Fifteen assertions cover successful backup, backup NumberFormatException, backup ArithmeticException, match equivalence, preserved original outcomes, skipped handlers and exactly one selected fallback attempt even when that returns another NumberFormatException.
- The match is equivalent for this helper, which captures its own exceptions; it is not a general replacement for recoverWith's callback exception handling.
- Full sbt runMain learning.PremiumLesson passed on Scala 2.13.18, sbt 1.12.15, Java 21.0.12.1. README updated through step 26. Checked main equal to origin/main before edits; preserved the claimFreeYears = 3 practice change.
- Status: introduced and assistant-verified only; no independent learner practice or mastery claimed.
- Next: a small consolidation scenario choosing Option, Either and Try by what each result means, before formal tests. Do not start Future or Play in the same lesson.
- Blog: this is a short companion to Part 13, not enough for a separate article. No article write, live status check or publication; verify the existing article before any requested addition.
- The restored six-page desk cheatsheet covers through session 35 (recover). No PDF update was requested in this teaching turn; add recoverWith on its next revision.

## Session 37: Option, Either and Try in one flow (2026-09-29)
- Added ResultFlowLesson, reusing OptionLesson.findPolicy. requirePolicy explicitly maps Some to Right and None to Left with a policy-not-found reason.
- readInstallments captures text.toInt in Try, then explicitly matches Success/Failure into Either[String, Int]. A successfully parsed zero or negative count fails the fictional positive-count rule.
- installmentLabel composes two Either-returning methods with a for-comprehension. The wrappers are not mixed automatically; the explicit match expressions choose the outcome representation.
- The label uses basePremium (600 for POL-001), not the claim-free discounted premium. Whole-euro integer division remains a tiny example, not production payment scheduling.
- Fourteen assertions cover required/missing policy, valid count, malformed/empty/overflow text, zero/negative counts, successful label and distinct errors. A guard confirms the count step is skipped after policy failure; both bad inputs report only the first error, not accumulated errors.
- Full sbt "runMain learning.PremiumLesson" passed with Scala 2.13.18, sbt 1.12.15 and Java 21.0.12.1.
- README updated through step 27 and entry-point invocation added. Main matched origin/main before edits. Stage only the new entry-point invocation, preserving and excluding the learner's claimFreeYears = 3 edit.
- Status: introduced and assistant-verified consolidation, not independent learner practice or demonstrated mastery.
- Next: one named automated test for ResultFlowLesson and explain what sbt test runs. Inspect existing sbt study work and build configuration first; no library/version chosen yet. Keep Future, Play and typed error hierarchies for later.
- Blog: useful consolidation alongside Parts 12-13, not yet a separate article. No blog action or live-state verification; numbering remains provisional. PDF still covers session 35; not regenerated this turn.

## Session 38: first named test in this lab; AnyFunSuite from zero (2026-09-29)
- Inspected sbtlearn build.sbt, MainSpec.scala, LESSON.md and outputs/progress.md. It contains ScalaTest 3.2.19 and an AnyFunSuite arithmetic example.
- User corrected the teaching assumption: that code was AI-generated and AnyFunSuite must be explained as unknown. Do not infer mastery from generated files, passing code or previous checked boxes.
- Added test-scoped ScalaTest 3.2.19 to this lab, retaining the existing version for consistency rather than claiming it is the latest. Checked official 3.2.19 AnyFunSuite documentation.
- Added src/test/scala/learning/ResultFlowLessonSpec.scala with exactly one named happy-path test. Calls the real workflow, comparing the whole Either to Right("POL-001: 600 EUR / 12 = 50 EUR"); inputs do not depend on PremiumLesson practice values.
- Teach AnyFunSuite as a library base class for a group of named tests; extends inherits testing methods. test is a method, not a Scala keyword. Its name argument and code block supply the test registration; the runner later executes the body. A suite needs no application main.
- Familiar assertion remains the check; ScalaTest provides named reporting and diagnostics. Separate arrange / act / assert comments are ordinary comments, not framework syntax. Spec in the class name is a convention, not the discovery mechanism.
- Deliberately expected 51 EUR once: sbt test ran 1 test, failed with actual 50 versus expected 51 and exited 1. Restored the correct 50 EUR expectation; sbt test and testOnly learning.ResultFlowLessonSpec both ran 1 successful test.
- Full runMain learning.PremiumLesson also passes. Existing lesson assertions remain in run() and do not automatically run under sbt test; the new suite is not comprehensive coverage.
- README updated through step 28, current Java environment corrected to 21.0.12.1 and obsolete no-test-suite wording removed. No production logic or practice code changed.
- Status: introduced and assistant-verified; AnyFunSuite familiarity explicitly not assumed, independent learner mastery not claimed.
- Next: individually named tests for missing policy, malformed count, non-positive count and first-error behavior, after the suite/test distinction is clear. No fixtures, mocks or extra test DSL.
- Blog and PDF unchanged. This is the beginning of testing material, not yet a standalone new article; verify numbering/live article state before future blog work.

## Session 39: named tests for expected failure results (2026-09-29)
- Added five tests to the same AnyFunSuite: missing policy, malformed installment text, zero count, negative count and policy-not-found precedence when both inputs are invalid.
- Each test calls ResultFlowLesson.installmentLabel with its own literal inputs and compares the entire Either against the expected Left. No production logic changed.
- Teaching distinction: application rejection is not test failure. A Left is a returned value; the test passes when the expected rejection is returned. A failed equality assertion is a failed test.
- Separate parsing and validation cases: "hello" cannot be read as Int, whereas "0" and "-1" parse successfully but violate the fictional positive-count rule.
- First-error test checks the externally observable error choice. It does not by itself prove whether a method was called; prior runMain guards separately demonstrate short-circuiting.
- sbt test reported six tests succeeded, zero failed. Full runMain learning.PremiumLesson also passed on Scala 2.13.18, sbt 1.12.15 and Java 21.0.12.1.
- Verified main synchronized before edits. Preserved both user changes: claimFreeYears = 3 in PremiumLesson and removed whitespace before the happy-path test block in ResultFlowLessonSpec. Exclude both from the focused commit.
- Updated README through step 29 and both progress checkpoints. Status: introduced and assistant-verified, not independent learner practice or mastery.
- Next: a small distinction between returned Left and thrown exception using ScalaTest intercept, with unfamiliar syntax explained before the example. Do not jump into mocks, fixtures or matcher DSLs.
- Blog/PDF unchanged. Testing material is growing toward a coherent article; no live blog status/numbering checks, draft creation or publication this turn.

## Session 40: returned errors versus thrown exceptions (2026-09-29)
- Added ExceptionBoundarySpec with two tests: readInstallments("hello") returns Left; raw "hello".toInt throws NumberFormatException.
- intercept is inherited from ScalaTest, not a keyword. [NumberFormatException] is a type argument; the block supplies code executed inside its checking boundary. The caught exception is returned. No exception or a wrong type fails the test; subclasses of the expected type also match.
- Inspected the returned exception's nonempty message without asserting exact JVM wording. The throwing expression must be inside the block; intercept around readInstallments would fail because it returns normally.
- Verified sbt test: eight tests pass across two suites. testOnly learning.ExceptionBoundarySpec: two pass. Full runMain learning.PremiumLesson passes. No production logic or dependencies changed.
- Preserved and excluded user edits in PremiumLesson and ResultFlowLessonSpec. README updated through step 30; both progress files updated. Introduced and assistant-verified only, not independent learner mastery.
- Next: small testing consolidation, then practical generics and sealed domain errors before Future/ExecutionContext. Defer mocks, fixtures and matcher DSLs.
- User scope decision: end this cursus after asynchronous Scala and final consolidation. Play, persistence/integrations, Pekko and production deployment are outside this cursus. sbt remains woven into lessons.
- Blog: testing can support a coherent article after consolidation (named tests, expected Left, exception assertions and sbt selection). No blog draft, publication or live status/numbering check; retain existing Part 13 identity and provisional Part 14 gate. PDF unchanged.

## Session 41: testing consolidation - a second successful input (2026-09-29)

- Added one test in ResultFlowLessonSpec for POL-001 with six installments: Right("POL-001: 600 EUR / 6 = 100 EUR"). Uses the fictional base premium, not the discounted annual premium.
- Reused arrange/act/assert without new syntax, framework or dependencies. Work out expected values independently; do not call the production method again to compute its own expectation.
- A second successful input distinguishes an actual calculation from an implementation that always produces the original 50 EUR amount. This is an example, not exhaustive coverage.
- Deliberately used an incorrect 101 EUR expectation: testOnly ran seven tests, six passed and one failed with actual 100 versus expected 101, exit 1. Restored 100 EUR; sbt test ran nine passing tests across two suites. Full runMain learning.PremiumLesson passed.
- Main matched origin/main before editing. Preserved both existing practice edits; stage only the added test hunk, not the user's happy-path whitespace change or PremiumLesson claimFreeYears.
- Updated README through step 31 and both checkpoints. Guided consolidation and assistant verification only; no independent learner exercise or mastery claimed.
- Next: practical generic methods, Java <T> versus Scala [A], explicit type arguments before inference. No mocks/fixtures/DSL detour. Finish line remains asynchronous Scala.
- Blog suggestion now justified: "Testing Scala Results: Values, Errors and Exceptions" covering named tests, independent expected results, Right/Left, intercept and red/green diagnostics. Number provisionally 14 only after fresh verification; no blog read, write or publication this turn. PDF unchanged.

## Scala/Play Part 14 blog draft (2026-09-29)

- User invoked blog-article with zakaria-mascot. Live admin confirmed Part 13 published and no existing Part 14/testing article before a single creation.
- Saved draft 33d6cb35-fcb4-428f-98ca-aedc7bc8b7da: "Scala/Play, Part 14: Testing Scala Results - Values, Errors and Exceptions". Slug: scala-play-part-14-testing-scala-results-values-errors-and-exceptions.
- Review at https://zakaria.lu/#blog-admin; public URL is 404 until publication. Update this existing ID; do not POST another copy. No publication performed.
- 939 prose words plus eight code blocks: AnyFunSuite from zero, named tests, Right/Left assertions, parsing versus validation, first-error observation, intercept and returned exception inspection, independent expected values and observed red/green output. Source pinned to b5267d7d498f635a437d443f2e701ecd53bc63eb.
- Re-ran sbt test (nine passing tests in two suites) and full runMain learning.PremiumLesson on Scala 2.13.18, sbt 1.12.15 and Java 21.0.12.1.
- Systems in Motion cover and two explanatory PNG diagrams; new canonical navy-hoodie mascot testing-station scene generated with the built-in image tool and both approved references. All visuals inspected.
- Local desktop and 390px mobile preview checked; fixed long inline path overflow. All four images load. Admin readback preserves full text, all code, links, alt text, tags and cover. Uploaded content hashes match reviewed PNGs; all media URLs return HTTP 200 image/png.
- Target existing Scala/Play hub through scala-play adoption tag after publication; no registry edit required for membership. Hub blurb still mentions Akka and nextUp still says classes/constructors. Extra tag-adopted Cake Pattern post also offsets the hub's displayed ordinal from authored part titles. No site edit or deployment performed.
- Local artifacts: C:/Users/Zakaria/Documents/Codex/2026-09-21/referenced-chatgpt-conversation-this-is-an/outputs/scala-play-14 (article, preview, payload/readback, visuals, prompt and verification).
- Both practice edits preserved. No new lesson or independent mastery claim; resume at generic methods. Course ends after asynchronous Scala. PDF unchanged. Next new article number provisionally 15, requiring fresh live verification.

## Session 42: generic methods - Java <T>, Scala [A] (2026-09-30)

- Added GenericMethodLesson, invoked by PremiumLesson. Started with stringAsList and intAsList, then removed type-only duplication with asList[A](value: A): List[A].
- Compared Java static <T> List<T> asList(T value) using Collections.singletonList with the Scala method. A is a declared type-parameter name, not Any or a runtime value; the same A relates input and result element type.
- Explicit asList[String]("POL-001") and asList[Int](12) first; then calls without type arguments. Separate List[String]/List[Int] assignments compile-check the inferred result types.
- Reused PolicySnapshot as a third explicit type without an additional overload. No cast, dynamic type selection, implicit parameter or type class involved.
- Seven new assertions verify expected values, agreement with concrete helpers, inferred/explicit agreement and the domain-type example. Full sbt runMain learning.PremiumLesson passed; all nine existing ScalaTest tests also passed. The new assertions run under runMain, not automatically under sbt test.
- Verified main equal to origin/main before edits. Preserved and excluded claimFreeYears = 3 and the user's test whitespace edit. Updated README through step 32 and both progress checkpoints.
- Introduced and assistant-verified only; no independent learner exercise or demonstrated mastery claimed.
- Next: a simple generic case class, reusing case-class knowledge, before sealed traits/typed domain errors. Keep variance and bounds out of this opening lesson; finish the cursus after asynchronous Scala.
- No blog or PDF update. One opening generics example is not enough for another article; accumulate coherent material. Part 14's last verified state is draft on 2026-09-29, not rechecked today; update that article rather than duplicating. Next new article provisionally Part 15 after fresh verification.

## Session 43: generic case classes - typed contents (2026-09-30)

- Before this lesson, clarified the learner's List equality question against Scala 2.13.18 source: List.equals compares corresponding elements using ==, in order; eq checks reference identity. This was an explanation, not an independently completed exercise.
- Added Box[A](value: A) and GenericClassLesson. Compared with the Java record Box<T>(T value) data-holder shape. A belongs to the class; the case-class value field is a readable val of type A.
- Started with new Box[String] and new Box[Int], then generated companion apply and inferred Box("POL-001"). Typed assignments verify String, Int and Box[String] access/inference at compile time.
- Box[PolicySnapshot] holds an existing domain object; .value returns PolicySnapshot directly with no cast or Option-style presence check. This simple Box provides no automatic map/flatMap, error handling or asynchronous behavior.
- Eight new runMain assertions cover field values, inferred/explicit equality, independent-object value equality versus identity, copy replacement, unchanged original and the domain value. The copy example keeps the same String content type; no general type-changing-copy rule or variance taught.
- Full sbt runMain learning.PremiumLesson and all nine existing ScalaTest tests passed on Scala 2.13.18, sbt 1.12.15 and Java 21.0.12.1. New lesson assertions run under runMain, not automatically under sbt test.
- Main synchronized before edits. Preserve and exclude claimFreeYears = 3 and the test whitespace practice edit. README updated through step 33; both progress checkpoints updated.
- Introduced and assistant-verified, not independent learner practice or mastery. Next: sealed traits and named error cases before composing typed Either; explain the new sealed keyword first.
- No blog/PDF action. Accumulate coherent domain-modeling material before proposing another article; Part 14 last verified draft on 2026-09-29, not checked today. Next new article number provisionally 15 subject to live verification. Course still ends after asynchronous Scala.

## Session 44: sealed traits and named domain errors (2026-09-30)

- Added PolicyInputError.scala: sealed trait PolicyInputError plus final case classes PolicyNotFound(number: String), MalformedInstallments(text: String), NonPositiveInstallments(count: Int).
- Explain trait as shared type; no abstract method is required here. Scala 2 sealed restricts direct subclasses to the same source file, not merely the same package. final prevents further subclassing of each concrete alternative. Compare the intent to a Java sealed interface, without claiming identical declaration rules.
- Added SealedErrorLesson.describe(error): String with one branch per case. Patterns bind the existing fields, rather than constructing values. Structured error data is separate from display wording; these values are not Throwable subclasses.
- Temporarily omitted the NonPositiveInstallments branch: sbt compile succeeded with a non-exhaustive-match warning naming NonPositiveInstallments(_). Restored the branch before execution; final compilation had no warning. Current build does not make these warnings fatal. A catch-all would conceal this particular missing-case warning.
- Six assertions verify all three displayed errors, negative-count display, equality of same case/data and inequality of different data. Full runMain learning.PremiumLesson and all nine ScalaTest tests pass on the existing Scala 2.13.18/sbt 1.12.15/Java 21.0.12.1 stack.
- Error case-class fields do not validate themselves: NonPositiveInstallments names an outcome but its constructor does not enforce count <= 0. A subsequent validation method must select it appropriately.
- Original String-based ResultFlowLesson and its tests unchanged. No typed Either flow introduced yet; teach the model first. Next: explicit Either[PolicyInputError, ...] results and then reuse existing composition, preserving the old flow for comparison.
- README step 34 and both trackers updated. Main matched origin/main before edits; preserve and exclude both existing practice changes. Introduced and assistant-verified only, not independent learner mastery.
- Blog/PDF unchanged. Combine this model with its typed result flow before proposing a coherent domain-errors article. Part 14 last verified draft on 2026-09-29; recheck before any article action. Course ends after asynchronous Scala.

## Session 45: Either carrying typed domain errors (2026-09-30)

- Added TypedResultFlowLesson alongside the unchanged ResultFlowLesson. requirePolicy returns Either[PolicyInputError, PolicySnapshot], converting None explicitly into Left(PolicyNotFound(number)).
- readInstallments returns Either[PolicyInputError, Int]. Try is limited to text.toInt; Success <= 0 becomes NonPositiveInstallments(count), Failure becomes MalformedInstallments(text). Failure(_) intentionally ignores the original exception and preserves the input in this narrowly scoped parsing model; this is not a general unexpected-failure handling policy.
- Both methods share the error type, so the familiar for-comprehension composes them into Either[PolicyInputError, String]. No automatic Option/Try mixing, casts, string inspection, error accumulation or asynchronous behavior.
- display matches Right(label) or Left(error), delegating the latter to SealedErrorLesson.describe. Error identity/data survive until this display choice; malformed-input messages now show original text, not the old exception class name.
- Sixteen outcome assertions cover lookup, parsing, empty/overflow text, valid result, each typed error, negative input, first-error precedence and display. An additional assert(false) guard inside the second generator is correctly skipped after a missing policy.
- Full runMain learning.PremiumLesson passes on the existing Scala 2.13.18/sbt 1.12.15/Java 21.0.12.1 stack; all nine existing named ScalaTest tests pass. The new typed-flow assertions execute under runMain, not automatically under sbt test.
- Whole-euro base-premium division remains an illustrative label, not a payment schedule. PolicyInputError values are returned data, not thrown exceptions.
- README step 35 and both trackers updated; main synchronized before edits. Preserved and excluded claimFreeYears = 3 and test whitespace practice edits.
- Introduced and assistant-verified only; no independent learner mastery claimed. Next: focused invariance/covariance comparison using Box[A] and List[+A], then useful Java interop and asynchronous Scala. No advanced type classes.
- Blog readiness: sealed error family plus typed Either now supports a coherent article, "From String Errors to Typed Scala Results", with prior generics as brief context. Suggest only; verify live posts and provisional Part 15 numbering before any draft. Part 14 last verified draft on 2026-09-29; blog/PDF unchanged this turn.

## Blog milestone: Scala/Play Part 15 (2026-09-30)

- Verified Part 14 published; no existing Part 15 before creation. Saved Part 15, From String Errors to Typed Scala Results, as draft 166a9ce6-da75-4236-b300-160862b49a33.
- Slug: scala-play-part-15-from-string-errors-to-typed-scala-results. Review at https://zakaria.lu/#blog-admin; public URL returns expected 404 while draft. Update this draft, never POST a duplicate.
- Scope: sealed error family, explicit Option/Try conversion to typed Either, first-error composition and separate display rendering. Generics provide brief context. No new lesson, independent mastery, framework or asynchronous implementation claimed.
- Article has 767 prose words, eight code blocks, Systems in Motion cover, canonical mascot scene generated with the built-in image tool and two explanatory diagrams. Reviewed PNGs and desktop/mobile layout; all four uploaded images return HTTP 200 image/png and match reviewed hashes. Saved text, code, links, images, title, excerpt, tags and cover match the payload.
- Re-ran sbt "runMain learning.PremiumLesson" test: lesson assertions and nine existing named tests pass. Kept the distinction between runMain assertions and the existing ScalaTest suites.
- Tagged scala-play for automatic adoption by the existing Scala/Play hub when published. Hub blurb still mentions Akka and nextUp still says classes/constructors; reported, not edited or deployed. Part numbering follows article titles, not hub ordinals.
- Local review sources: outputs/scala-play-15 under the 2026-09-21 referenced-chatgpt-conversation-this-is-an workspace. Publication and PDF update not performed. Next lesson remains bounded Box[A]/List[+A] variance, then practical Java interop and async; cursus ends after async.

## Session 46: invariance and covariance (2026-09-30)

- Started with ordinary PolicyNotFound-to-PolicyInputError subtyping, then compared List[PolicyNotFound] widening with invariant Box[PolicyNotFound]. Existing Box[A] stays unchanged; val alone does not infer covariance.
- Added explicit read-only final class CovariantBox[+A](val value: A). The plus is declaration-site covariance, not construction syntax or runtime conversion. Wider assignments keep the same object and do not change its contents or guarantee deep immutability.
- VarianceLesson describes a narrow List through a method accepting List[PolicyInputError]. Eight assertions check value preservation, messages, same-reference List/box widening and the distinct newly constructed wider invariant box.
- Verified two real Scala 2.13.18 compilation failures in examples/variance-errors: invariant Box assignment and a covariant public var setter. Documented exact diagnostics and fresh sbt-session reproduction; shell exit status can be zero after compile failure followed by exit, so compiler output is the evidence.
- Explained setter unsafety with the existing sibling error alternatives. Java bridge: invariant List<T> and use-site ? extends, versus Scala declaration-site +A. No casts, contravariant consumer lesson or lower-bound signatures introduced.
- sbt "runMain learning.PremiumLesson" test passes: eight new runMain assertions and all nine existing named ScalaTest tests; no new named suite. Introduced/assistant-verified only, not independent learner mastery.
- README step 36 and both trackers updated. main matched origin/main before edits. Preserve and exclude user practice changes in build.sbt (blank line), PremiumLesson (claimFreeYears = 3) and ResultFlowLessonSpec (whitespace).
- Next: focused Java collection interoperability, then async and final consolidation. Course ends after asynchronous Scala. Blog/PDF unchanged; Part 15 last verified draft, next new number provisionally 16 after live verification. Keep accumulating useful Java/Scala boundary material before proposing another article.

## Session 47: Java and Scala collection boundaries (2026-09-30)

- Added JavaCollectionLesson, called by PremiumLesson. Began with real java.util.ArrayList[String] and add/get calls, then explicit scala.jdk.javaapi.CollectionConverters.asScala before import-enabled javaNumbers.asScala.
- Explained Scala 2 wildcard import scala.jdk.CollectionConverters._, standard-library availability without extra dependencies, and mutable.Buffer as an ordered mutable collection rather than immutable List.
- Demonstrated live adapter sharing in both directions: Java add visible in Scala, Scala update visible in Java; round-trip asJava returns original Java list reference. Adapters do not add thread safety.
- Created snapshot with toList before mutation and verified unchanged contents afterward. Snapshot/copy refers to collection structure, not deep copying element objects; String elements here are immutable.
- Adapted immutable Scala List via asJava, observed UnsupportedOperationException from add inside Try, and kept the normal run successful. Java List's interface does not guarantee writable operations. Created an independent writable java.util.ArrayList copy explicitly.
- Twelve assertions pass through sbt "runMain learning.PremiumLesson"; all nine existing named ScalaTest tests pass via test. No new test suite and no new independent mastery claim. Prior user Understood acknowledges variance, not an assessed exercise.
- README step 37 and both trackers updated. main synchronized before edits; user build.sbt blank line, claimFreeYears = 3 and test whitespace edits preserved and excluded.
- Next: Future/ExecutionContext basics, then composition/failure handling and blocking boundaries, ending with final consolidation. No additional prerequisite chapters or frameworks added. Blog/PDF untouched; Part 15 last verified draft, future numbering requires live verification. Keep accumulating material rather than generating a thin interop article automatically.

## Session 48: the first Future and ExecutionContext (2026-09-30)

- Added FutureBasicsLesson and main invocation. One Future computes existing calculatePremium(600, 3), returning 550; caller and worker names are printed, and a guard checks different Thread references.
- Used a familiar Java Executors.newSingleThreadExecutor adapted with ExecutionContext.fromExecutorService. Passed ec explicitly in Future.apply { body }(ec); no implicit lookup or global pool introduced yet. Future is a result handle, not a thread; execution behavior depends on its context.
- Explained scheduling when Future.apply is called, versus caller waiting later. The body is supplied for execution by the context rather than evaluated first to form an Int argument. Multiple argument lists and companion apply connect to earlier lessons.
- isCompleted immediately after submission is observational only: either value is valid. Actual first run printed caller continuation and false before worker output, then 550 and true. Names and ordering are not guarantees; no sleep-based timing claim.
- Await.result with Duration(5, TimeUnit.SECONDS) is an explicitly blocking console-only boundary. Timeout limits waiting, not task lifetime; Await does not start the computation. Do not copy into request handling or nested work on the same pool. Non-blocking map composition follows next.
- finally shuts down the owned executor and waits up to five seconds for termination, with shutdownNow fallback. No shared/global executor is shut down. Executor lifecycle is Java-style demo scaffolding, not a per-request production design.
- Three assertions (one worker-thread guard and two caller result checks) pass via runMain; sbt test also passes all nine existing named tests. No new asynchronous test suite, assessed mastery, failure-recovery exercise or framework code.
- README step 38 and both progress files updated; branch main synchronized first. Preserve/exclude user build.sbt blank line, claimFreeYears = 3 and test whitespace changes.
- Next: Future.map, then flatMap/for-comprehensions, failures/recovery, blocking boundaries and final consolidation. Async remains the finish line. Blog/PDF unchanged; accumulate a coherent async chapter before suggesting an article. Part 15 status is only last verified draft; verify live before future article work.

## Session 49: Future.map without an intermediate wait (2026-09-30)

- Added FutureMapLesson and main invocation. Existing FutureBasicsLesson preserved, including user's Future.apply-to-Future shorthand edit.
- Defined toLabel: Int => String explicitly before passing it to premiumFuture.map(toLabel)(ec). Explained function type, lambda body and passing the function rather than calling it now. A plain String return becomes the successful value of a new Future[String].
- No Await between calculation and map, or inside either body. One final bounded console wait receives Annual premium: 550 EUR. Source computation is not rerun or mutated by mapping.
- Explicit ec passed to both operations, backed by one owned Java executor. Calculation and transformation run off the caller; observed same pool worker in this run. No new-thread-per-stage assumption; caller continuation print order is nondeterministic.
- Five runMain assertions passed: two off-caller thread guards, mapper input 550, exact resulting label, source completion. All nine existing ScalaTest tests pass separately. No new named async suite or independent mastery claim.
- Mentioned map applies on success, skips the function on source failure and captures non-fatal callback failure into the result; these failure paths are reserved for a later runnable lesson. Registration on an already-completed source remains valid.
- README step 39 and both trackers updated; main matched origin/main before edits. Preserved/excluded user edits in build.sbt, FutureBasicsLesson.scala, PremiumLesson.scala and ResultFlowLessonSpec.scala.
- Next: Future.flatMap, then for-comprehensions, failures/recovery, blocking boundaries and final consolidation. No framework prerequisite added. Blog/PDF unchanged; Part 15 only last verified draft and future numbering still requires live checks. Async basics plus composition will form a coherent later article.

## Session 50: Future.flatMap and nested map results (2026-09-30)

- Added FutureFlatMapLesson and main invocation. One premiumFuture computes 550; a separately defined Int => Future[Int] function submits a toy whole-euro installment calculation (550 / 10 = 55), not external I/O or production scheduling.
- Explicit type annotations prove map produces Future[Future[Int]] and flatMap produces Future[Int]. The outer map succeeds with a future handle; this is not proof of inner success/completion. The flat result follows the second future's outcome without blocking a worker.
- Both comparison branches are wired before console waits. The source is reused, not rerun; the installment function deliberately runs once per comparison branch. Explained this duplicate demonstration output rather than attributing it to flatMap.
- All work and callbacks use explicit ec on one owned Java executor. No Await inside tasks/callbacks. Two console waits inspect nested outer/inner results, and one console wait observes the flat result; each wait has a five-second limit. No sleep, pending-state or callback-order assertion.
- Five outcome assertions pass through runMain; all nine existing named tests pass separately. No new asynchronous suite or independent learner mastery claimed. Failure propagation mentioned, with runnable failure/recovery work deferred.
- README step 40 and both trackers updated; main synchronized before edits. Four user changes preserved/excluded: build.sbt blank line, FutureBasicsLesson companion shorthand, PremiumLesson claimFreeYears = 3 and test whitespace.
- Next: Future for-comprehensions, then failure handling/recovery, blocking boundaries and final consolidation. Async remains the finish line. Blog/PDF unchanged; async foundations/composition now accumulating toward a coherent article, with for-comprehension comparison still useful before drafting. Part 15 only last verified draft; any article action requires fresh state/numbering checks.

## Session 51: Future for-comprehensions (2026-09-30)

- Added FutureForLesson and main invocation. Shared premiumFuture produces 550; toInstallment returns Future[Int] producing 55. Compared explicit premiumFuture.flatMap(...toInstallment(...).map(...)(ec))(ec) with two-generator for/yield.
- Introduced implicit val ec: ExecutionContext as the already-known compiler-supplied argument mechanism. Same owned Java executor, not another pool or framework lookup. Future creation retains explicit (ec); generated map/flatMap calls receive ec implicitly.
- Explained generator-bound values are Int and yield is a plain String, while the complete expression has type Future[String]. No Await hidden in arrows; no guards, extra Future in yield or advanced syntax.
- Dependent toInstallment call is inside the first success callback. Futures constructed before a for may already be running; for itself does not determine eager submission/parallelism. Both comparison versions run, so the dependent task runs twice while sharing the source.
- Four assertions verify both exact labels, result equality and source completion. Final sbt "runMain learning.PremiumLesson" test passes all lesson assertions and nine existing named tests. Replaced deprecated leading Int-plus-String concatenation with explicit toString before final verification.
- One bounded final console wait per comparison result, no wait in tasks/callbacks; owned executor shut down in finally. No timing-order assumptions or new test suite.
- README step 41 and both trackers updated; main synchronized first. Preserved/excluded four user edits: build.sbt blank line, FutureBasicsLesson shorthand, PremiumLesson practice value and test whitespace.
- User reported easy understanding of flatMap based on earlier containers: reported understanding, not independent mastery. Next: real failed Future propagation and skipped work, then recovery, blocking boundaries and final consolidation.
- Blog readiness: async basics, explicit ExecutionContext, map, flatMap and for now support a coherent article on composing asynchronous results. Suggest only; live article status/numbering must be rechecked before draft. Part 15 last verified draft, next new number provisionally 16. Blog/PDF unchanged; course still ends after async.

## Session 52: failed Futures and propagation (2026-09-30)

- Added FutureFailureLesson and main invocation. A Future[Int] body throws a deliberate IllegalArgumentException; creation returns a result handle rather than throwing that worker error directly on the caller. Ordinary non-fatal exceptions only; not a universal claim about fatal JVM failures or submission errors.
- A map callback and for-comprehension dependent generator/yield contain assert(false) guards. Exact original-failure comparisons pass, proving these guards did not execute; the second Future is not created after the source fails.
- Exercised a successful source whose map function throws IllegalStateException, and whose flatMap function returns an inner Future failing with ArithmeticException. Derived results retain the corresponding failure. Separate success map returns 55, showing a failed branch does not poison the shared successful source.
- Private generic observe[A] uses Try(Await.result(future, five-second duration)) at the console boundary. Await rethrows the stored ordinary failure; Try captures it for comparison. This is not Future recovery. Exact exception-object equality distinguishes expected failure from timeout/guard failures.
- Six executed outcome assertions plus three skipped guards; all runMain examples and all nine existing named ScalaTest tests pass. No new named suite, fatal-error exercise or independently assessed mastery.
- Explained isCompleted is true for failure too. No cancellation of already-started independent work, no intermediate waits in callbacks, no sleeps or timing assumptions. Owned executor cleaned up in finally.
- README step 42 and both trackers updated; main synchronized before edits. Preserved/excluded five user edits: build.sbt blank line, FutureBasicsLesson shorthand, FutureForLesson line break before (ec), PremiumLesson practice value and test whitespace.
- Next: Future.recover matching a specific exception and returning a plain fallback; then recoverWith, blocking boundaries and final consolidation. Async stays the finish line. Blog/PDF untouched; accumulated async chapter is article-ready when requested, but live state/numbering must be verified first. Part 15 remains last verified draft only.


## Session 53: Future.recover and a specific display fallback (2026-09-30)

- Added FutureRecoverLesson and main invocation. Reused the familiar Try.recover pattern with Future[String]: case _: NumberFormatException returns the plain String "Premium unavailable". Explained the case matches the exception itself, not a Failure wrapper; the overall result remains Future[String].
- Successful label remains "Annual premium: 550 EUR"; a deliberately thrown NumberFormatException selects the display fallback. An unrelated IllegalStateException stays failed, and the original failed Future retains the same exception object. No invented monetary fallback or catch-all error suppression.
- A map attached after recovery yields "Screen: Premium unavailable", demonstrating that the recovered branch can continue on its success path. recover does not retry the source or change its outcome. The handler uses the familiar implicit ExecutionContext; registering recovery is not a blocking wait.
- Five exact outcome assertions pass through sbt "runMain learning.PremiumLesson"; all nine existing named tests pass separately. Generated runnable checks are not evidence of independently demonstrated mastery. No new test framework or async suite introduced.
- Bounded Try/Await observations stay on the console caller, outside tasks/callbacks; executor cleanup remains in finally. No sleeps, callback-order assumptions or timing assertions.
- README step 43 and both trackers updated after synchronized main inspection. Six practice edits preserved/excluded: build.sbt blank line, FutureBasicsLesson shorthand, FutureFailureLesson blank line, FutureForLesson line break, PremiumLesson practice value and test whitespace.
- Next: recoverWith for a fallback returning Future, then blocking boundaries and final consolidation. Async remains the course finish line. Blog/PDF unchanged; async material is coherent enough for an article when requested, with live state/numbering verification required first. Part 15 remains last verified draft only.


## Session 54: Future.recoverWith and a fallible asynchronous backup (2026-09-30)

- Added FutureRecoverWithLesson and main invocation. First explained recover's plain String versus recoverWith's Future[String] handler return. readBackupLabel(text) schedules a toy numeric conversion and label construction using the familiar implicit ExecutionContext; not external I/O or a real backup service.
- labelWithBackup calls readBackupLabel inside the matching NumberFormatException case. Explicit backup text "550" produces "Annual premium: 550 EUR"; "still invalid" produces a new NumberFormatException from the backup. The resulting type stays Future[String], with no Await used for composition.
- Five outcome assertions verify successful backup, failed backup, unchanged successful source, unmatched original failure and unchanged failed source. The backup exception is checked by type and distinct reference, not JVM message wording; a timeout cannot pass. Two assert(false) guards in unused handler bodies remain skipped.
- Even when the backup fails with the same matching exception type, this recoverWith does not apply itself recursively or retry. The original failure is not mutated. A further handler would be an explicit separate decision, not introduced as a retry framework.
- All lesson assertions pass via sbt "runMain learning.PremiumLesson"; all nine existing named tests pass separately. No independent learner mastery inferred. Bounded Try/Await is console observation only; owned executor cleanup in finally; no sleeps or ordering assumptions.
- README step 44 and both trackers updated. main matched origin/main after fetch before edits. Preserved/excluded all six practice edits in build.sbt, FutureBasicsLesson.scala, FutureFailureLesson.scala, FutureForLesson.scala, PremiumLesson.scala and ResultFlowLessonSpec.scala.
- Next: blocking boundaries and thread-pool starvation, then final consolidation to finish the Scala cursus. Play/Pekko remain separate follow-ons. Blog/PDF untouched; the async material supports a coherent article when requested, but live state and numbering must be checked first. Part 15 remains last verified draft only.


## Session 55: blocking boundaries and thread-pool starvation (2026-10-03)

- Added FutureBlockingLesson and main invocation. A deliberately bad Future body queues another Future on the same one-worker executor, then calls Await.result on it with a 200 ms limit. The occupied worker cannot run its queued dependency; this forces timeout structurally, not by relying on a slow operation or a guessed print order.
- Try captures the worker-side TimeoutException. The body verifies the dependent Future is still incomplete while it continues to occupy the only worker, then returns its handle. The resulting Future[Future[Int]] is an inspection harness using previously covered nesting, not the recommended composition pattern.
- Console caller observes the outer and then inner Future with five-second limits. The queued task subsequently succeeds with 55, verifying that timing out a wait did not cancel it. Without the deliberate finite timeout, this dependency cycle could remain stuck.
- Compared a non-blocking Future(550).flatMap(premium => Future(premium / 10)) equivalent using calculatePremium: it succeeds with 55 on the same one-worker pool. Four assertions execute via runMain; all nine existing named tests pass separately. No independent learner mastery inferred.
- Explained the Java single-thread ExecutorService plus get analogy, worker occupancy versus asynchronous submission, and why console-end Await is different from waiting inside shared-worker code. More workers do not remove the general starvation risk when all workers wait on queued dependencies.
- Official Scala documentation checked: scala.concurrent.blocking is a context-dependent notification, not a non-blocking adapter; fixed Java pools do not compensate by adding workers. Dedicated, managed execution contexts for unavoidable blocking I/O were discussed only, with no production pool sizing or I/O implementation claimed. flatMap cannot make blocking calls inside its callback non-blocking.
- README step 45 and both trackers updated. main matched origin/main after fetch. Six existing practice edits preserved/excluded in build.sbt, FutureBasicsLesson.scala, FutureFailureLesson.scala, FutureForLesson.scala, PremiumLesson.scala and ResultFlowLessonSpec.scala. All owned executor cleanup stays in finally; no sleeps or infinite waits.
- Next: final synchronous/asynchronous consolidation, then the Scala cursus is complete. Play/Pekko are separate follow-ons. Blog/PDF unchanged; async material is article-ready when requested, subject to fresh live state/numbering verification. Part 15 is only last verified draft, not a claim about its current publication status.


## Session 56: final synchronous/asynchronous consolidation (2026-10-03)

- Added ScalaConsolidationLesson and main invocation. Reused PolicyRepository, InMemoryPolicyRepository, PolicySnapshot, the sealed PolicyInputError family, TypedResultFlowLesson.readInstallments and its display boundary. No new framework, dependency, effect library or transformer abstraction.
- A shared labelFromLookup turns Option into typed Either and uses an Either for-comprehension to validate/count and produce the existing whole-euro BASE-premium label. The synchronous entry point calls it directly; the asynchronous entry point schedules repository.find and maps its Option into the same business flow.
- Explicitly explained Future[Either[PolicyInputError, String]] outside-in. Future.map receives an Option and returns a plain Either; the inner for composes Either, not Future. No mixed-container for magic or claim that flatMap automatically unwraps different container types.
- Six scenarios (valid, missing, malformed, zero, negative, missing plus malformed) produce the exact expected results in both sync and async versions: 12 assertions. A deliberately throwing repository verifies technical failure, recoverWith using an explicitly supplied alternate repository, and that a returned Left bypasses recovery: three more assertions and one skipped guard.
- Future.map still executes for the successful Future carrying Left and delegates to the familiar Either display function. One display assertion brings the total to 16. All lesson examples and all nine existing named tests pass via sbt "runMain learning.PremiumLesson" test. No new named suite or independent learner mastery claimed.
- All bounded Await calls stay in console verification code; async callbacks contain no wait. Owned single-worker executor cleanup remains in finally. Scheduled in-memory lookup is a toy example, not a real non-blocking database driver. Demo IllegalStateException recovery is not a blanket production error policy.
- README step 46, final checklist and BOTH progress files updated, including stale top-level dates/remaining-roadmap text. The agreed synchronous/asynchronous Scala teaching sequence is complete; advanced sbt/Scala and Play/Pekko are optional follow-ons, not retroactively added requirements.
- main matched origin/main after fetch before edits. Six existing practice changes remain preserved/excluded in build.sbt, FutureBasicsLesson.scala, FutureFailureLesson.scala, FutureForLesson.scala, PremiumLesson.scala and ResultFlowLessonSpec.scala.
- Blog/PDF unchanged. Proposed next coherent article scope: asynchronous Scala without blocking, from Future/ExecutionContext through composition/recovery and the final business-versus-technical-error distinction. Verify current blog state and numbering before any draft; Part 15 remains last verified draft only, not a current publication claim.


## Session 57: Scala/Play Part 16 article draft (2026-10-03)

- User explicitly invoked blog-article with zakaria-mascot. Verified Part 15 published and no Part 16 existed before creating a draft; no publication authorization inferred.
- Saved draft 015b60b5-9887-438d-8a42-20109f4f21ee: Scala/Play, Part 16: Asynchronous Scala Without Blocking. Slug scala-play-part-16-asynchronous-scala-without-blocking; review https://zakaria.lu/#blog-admin. Public article HTTP 404 is expected until authorized publication; update/publish this existing ID, do not POST a duplicate.
- About 920 prose words, eight code blocks, Systems in Motion cover, canonical mascot dispatch illustration and three explanatory figures: method pairs, worker starvation and Future[Either] outcomes. Target hub Scala/Play adopts scala-play tag. Hub registry still has stale Akka blurb and classes/constructors nextUp; flagged, not edited or deployed.
- Canonical image generated using built-in image generation and both approved identity/style references. All PNGs visually reviewed; desktop/mobile preview layout and image loading checks passed. Saved text, code, links, metadata and image references match the source; five content-addressed image URLs match reviewed hashes and return HTTP 200.
- Reran sbt runMain and all nine existing tests successfully against lesson source commit 56868d0. Also extracted the article's six executable Scala blocks into an external smoke harness, compiled via a temporary sbt setting, and passed eight assertions. Windows sbt.bat quoting rejected the initial command before execution; invoking the existing sbt launcher via Java worked. No persistent build changes.
- Local artifacts: C:\Users\Zakaria\Documents\Codex\2026-09-21\referenced-chatgpt-conversation-this-is-an\outputs\scala-play-16 (article.html/json, preview.html, source templates, five PNGs, prompt, smoke harness and QA/readback reports). No PDF update. Course remains complete at introduced/assistant-verified coverage, not independently assessed mastery.
- Next article number provisionally 17 only after fresh live verification. User practice edits preserved; only repository progress tracking committed for this article task.
