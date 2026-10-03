package learning

import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Try}

object ScalaConsolidationLesson {
  // One synchronous business flow, shared by both entry points below.
  private def labelFromLookup(
      number: String, text: String, found: Option[PolicySnapshot]
  ): Either[PolicyInputError, String] = {
    val required: Either[PolicyInputError, PolicySnapshot] = found match {
      case Some(policy) => Right(policy)
      case None => Left(PolicyNotFound(number))
    }

    // These generators compose Either values, not Futures.
    for {
      policy <- required
      count <- TypedResultFlowLesson.readInstallments(text)
    } yield policy.number + ": " + policy.basePremium + " EUR / " + count +
      " = " + (policy.basePremium / count) + " EUR"
    // Fictional whole-euro BASE premium, not production payment scheduling.
  }

  def installmentLabelSync(
      number: String, text: String, repository: PolicyRepository
  ): Either[PolicyInputError, String] = {
    labelFromLookup(number, text, repository.find(number))
  }

  def installmentLabelAsync(
      number: String, text: String, repository: PolicyRepository
  )(implicit ec: ExecutionContext): Future[Either[PolicyInputError, String]] = {
    // Our in-memory lookup is scheduled on the supplied execution context.
    // This is not a real asynchronous database driver or a production I/O pool.
    val lookup: Future[Option[PolicySnapshot]] = Future {
      repository.find(number)
    }
    // The callback returns a plain Either, so Future.map is the right operation.
    lookup.map((found: Option[PolicySnapshot]) => {
      labelFromLookup(number, text, found)
    })
  }

  // Ordinary trait implementation, deliberately throwing for one demo branch.
  private class UnavailableRepository(problem: IllegalStateException)
      extends PolicyRepository {
    override def find(number: String): Option[PolicySnapshot] = throw problem
  }

  // Console checks only: never call this blocking helper from a Future callback.
  private def verifyBoth(
      number: String, text: String, expected: Either[PolicyInputError, String],
      repository: PolicyRepository
  )(implicit ec: ExecutionContext): Unit = {
    val synchronous = installmentLabelSync(number, text, repository)
    val asynchronous = Await.result(
      installmentLabelAsync(number, text, repository), Duration(5, TimeUnit.SECONDS)
    )
    assert(synchronous == expected)
    assert(asynchronous == expected)
    println(number + " / " + text + " -> sync and async: " + asynchronous)
  }

  def run(): Unit = {
    println("--- Scala consolidation: asynchronous work, typed business results ---")
    val executor = Executors.newSingleThreadExecutor()
    implicit val ec: ExecutionContext =
      ExecutionContext.fromExecutorService(executor)
    val repository: PolicyRepository = new InMemoryPolicyRepository(
      List(PolicySnapshot("POL-001", 600, 3))
    )
    val consoleLimit = Duration(5, TimeUnit.SECONDS)

    try {
      verifyBoth("POL-001", "12", Right("POL-001: 600 EUR / 12 = 50 EUR"), repository)
      verifyBoth("POL-999", "12", Left(PolicyNotFound("POL-999")), repository)
      verifyBoth("POL-001", "hello", Left(MalformedInstallments("hello")), repository)
      verifyBoth("POL-001", "0", Left(NonPositiveInstallments(0)), repository)
      verifyBoth("POL-001", "-1", Left(NonPositiveInstallments(-1)), repository)
      verifyBoth("POL-999", "hello", Left(PolicyNotFound("POL-999")), repository)

      val problem = new IllegalStateException("Demo repository unavailable")
      val unavailable: PolicyRepository = new UnavailableRepository(problem)
      val failed: Future[Either[PolicyInputError, String]] =
        installmentLabelAsync("POL-001", "12", unavailable)
      val recovered: Future[Either[PolicyInputError, String]] = failed.recoverWith {
        // A deliberate demo policy: use an explicitly supplied alternate repository.
        // Production recovery would need an appropriate, specific failure contract.
        case _: IllegalStateException =>
          installmentLabelAsync("POL-001", "12", repository)
      }

      val missing: Future[Either[PolicyInputError, String]] =
        installmentLabelAsync("POL-999", "12", repository)
      val missingAfterRecovery = missing.recoverWith {
        case _: IllegalStateException =>
          assert(false, "A returned Left is not a failed Future")
          installmentLabelAsync("POL-001", "12", repository)
      }

      val failedOutcome = Try(Await.result(failed, consoleLimit))
      val recoveredOutcome = Await.result(recovered, consoleLimit)
      val missingOutcome = Await.result(missingAfterRecovery, consoleLimit)
      assert(failedOutcome == Failure(problem))
      assert(recoveredOutcome == Right("POL-001: 600 EUR / 12 = 50 EUR"))
      assert(missingOutcome == Left(PolicyNotFound("POL-999")))
      println("Technical failure: " + failedOutcome)
      println("Alternate repository: " + recoveredOutcome)
      println("Business error remains: " + missingOutcome)

      // Render the inner Either only at the display boundary.
      val displayed: Future[String] = missing.map(
        (result: Either[PolicyInputError, String]) => TypedResultFlowLesson.display(result)
      )
      val displayText = Await.result(displayed, consoleLimit)
      assert(displayText == "Policy not found: POL-999")
      println("Display: " + displayText)
    } finally {
      executor.shutdown()
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow()
      }
    }
  }
}
