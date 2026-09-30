package learning

import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

object FutureRecoverWithLesson {
  // Toy backup input supplied explicitly for this fictional premium.
  // This schedules a local conversion, not a real network or database call.
  def readBackupLabel(text: String)(implicit ec: ExecutionContext): Future[String] = {
    Future {
      val premium: Int = text.toInt
      "Annual premium: " + premium + " EUR"
    }
  }

  def labelWithBackup(result: Future[String], backupText: String)(
      implicit ec: ExecutionContext
  ): Future[String] = {
    result.recoverWith {
      // Return Future[String], not the plain String used by recover.
      // Calling the method HERE starts backup work only for a matching failure.
      case _: NumberFormatException => readBackupLabel(backupText)
    }
  }

  private def observe[A](future: Future[A]): Try[A] =
    Try(Await.result(future, Duration(5, TimeUnit.SECONDS)))

  def run(): Unit = {
    println("--- Future.recoverWith: the backup returns a Future too ---")
    val executor = Executors.newSingleThreadExecutor()
    implicit val ec: ExecutionContext =
      ExecutionContext.fromExecutorService(executor)

    try {
      val sourceProblem = new NumberFormatException("Demo invalid primary text")
      val failedLabel: Future[String] = Future { throw sourceProblem }
      val goodLabel: Future[String] = Future { "Annual premium: 600 EUR" }
      val otherProblem = new IllegalStateException("Demo unrelated failure")
      val otherFailure: Future[String] = Future { throw otherProblem }

      val backupWorked: Future[String] = labelWithBackup(failedLabel, "550")
      val backupFailed: Future[String] = labelWithBackup(failedLabel, "still invalid")

      // Deliberately failing guards prove these handler bodies are skipped.
      val unchanged: Future[String] = goodLabel.recoverWith {
        case _: NumberFormatException =>
          assert(false, "Success must not start the backup")
          readBackupLabel("550")
      }
      val unhandled: Future[String] = otherFailure.recoverWith {
        case _: NumberFormatException =>
          assert(false, "An unmatched failure must not start the backup")
          readBackupLabel("550")
      }

      val workedOutcome = observe(backupWorked)
      val failedOutcome = observe(backupFailed)
      val successOutcome = observe(unchanged)
      val unhandledOutcome = observe(unhandled)
      val originalOutcome = observe(failedLabel)

      println("Backup succeeds: " + workedOutcome)
      println("Backup fails: " + failedOutcome)
      println("Existing success: " + successOutcome)
      println("Unmatched failure: " + unhandledOutcome)
      println("Original remains failed: " + originalOutcome)

      assert(workedOutcome == Success("Annual premium: 550 EUR"))
      // Same matching exception TYPE, but a new error from the backup.
      // This recoverWith does not recursively handle its own backup failure.
      assert(failedOutcome match {
        case Failure(error: NumberFormatException) => error ne sourceProblem
        case _ => false
      })
      assert(successOutcome == Success("Annual premium: 600 EUR"))
      assert(unhandledOutcome == Failure(otherProblem))
      assert(originalOutcome == Failure(sourceProblem))
    } finally {
      executor.shutdown()
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow()
      }
    }
  }
}
