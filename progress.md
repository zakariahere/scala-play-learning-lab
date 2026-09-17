# Learning progress

Updated: 2026-09-17

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

Introduce `Option.map` with an explicit one-parameter lambda, derived from the existing `match`. Follow the transformation from `Option[PolicySnapshot]` to `Option[String]`, preserving `None`, before introducing `getOrElse`. Leave `flatMap` and for-comprehensions for later lessons.

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

The repository contains the runnable learning project and public course notes. Play and Akka have not been added yet.
