package learning

import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

object FutureRecoverLesson {
  def labelWithRecovery(result: Future[String])(
      implicit ec: ExecutionContext
  ): Future[String] = {
    // The case matches the exception, not a Failure wrapper.
    // _: NumberFormatException checks its type without naming the exception.
    // The handler returns a plain String, not Future[String].
    result.recover {
      case _: NumberFormatException => "Premium unavailable"
    }
  }

  // Observation only, on the console caller: no Await inside async callbacks.
  // A timeout is captured too, but cannot satisfy the exact assertions below.
  private def observe[A](future: Future[A]): Try[A] =
    Try(Await.result(future, Duration(5, TimeUnit.SECONDS)))

  def run(): Unit = {
    println("--- Future.recover: a plain fallback for a specific failure ---")
    val executor = Executors.newSingleThreadExecutor()
    implicit val ec: ExecutionContext =
      ExecutionContext.fromExecutorService(executor)

    try {
      val goodLabel: Future[String] = Future { "Annual premium: 550 EUR" }
      // Deliberately thrown demo errors keep their identity testable.
      val parseProblem = new NumberFormatException("Demo invalid premium text")
      val failedLabel: Future[String] = Future { throw parseProblem }
      val otherProblem = new IllegalStateException("Demo service unavailable")
      val otherFailure: Future[String] = Future { throw otherProblem }

      val unchanged: Future[String] = labelWithRecovery(goodLabel)
      val recovered: Future[String] = labelWithRecovery(failedLabel)
      val unhandled: Future[String] = labelWithRecovery(otherFailure)

      // Recovery makes this derived branch successful, so map can run again.
      val displayed: Future[String] = recovered.map((label: String) => {
        "Screen: " + label
      })

      val successOutcome = observe(unchanged)
      val recoveredOutcome = observe(recovered)
      val unhandledOutcome = observe(unhandled)
      val originalOutcome = observe(failedLabel)
      val displayedOutcome = observe(displayed)

      println("Existing success: " + successOutcome)
      println("Matching failure recovered: " + recoveredOutcome)
      println("Unmatched failure: " + unhandledOutcome)
      println("Original remains failed: " + originalOutcome)
      println("Downstream map: " + displayedOutcome)

      assert(successOutcome == Success("Annual premium: 550 EUR"))
      assert(recoveredOutcome == Success("Premium unavailable"))
      assert(unhandledOutcome == Failure(otherProblem))
      assert(originalOutcome == Failure(parseProblem))
      assert(displayedOutcome == Success("Screen: Premium unavailable"))
    } finally {
      executor.shutdown()
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow()
      }
    }
  }
}
