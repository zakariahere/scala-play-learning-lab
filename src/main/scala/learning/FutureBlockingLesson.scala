package learning

import java.util.concurrent.{Executors, TimeUnit, TimeoutException}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Try}

object FutureBlockingLesson {
  // Future { blockingCall() } moves the wait to a worker; it does not remove it.
  // scala.concurrent.blocking is an advisory notification, not an async adapter.
  // The fixed one-worker Java executor here does not add workers in response.
  // Real unavoidable blocking I/O needs a suitably managed execution context;
  // this demo's executor lifecycle is for a console lesson, not per-request use.
  def run(): Unit = {
    println("--- Blocking inside a Future: one worker waiting on itself ---")
    val executor = Executors.newSingleThreadExecutor()
    implicit val ec: ExecutionContext =
      ExecutionContext.fromExecutorService(executor)
    val consoleLimit = Duration(5, TimeUnit.SECONDS)

    try {
      // Deliberate anti-pattern, bounded for this demonstration only.
      // Both Future bodies use the SAME executor, with exactly ONE worker.
      val blockedAttempt: Future[Future[Int]] = Future {
        val queuedInstallment: Future[Int] = Future { 550 / 10 }

        // This worker cannot execute queuedInstallment while it is waiting.
        // Try catches the expected timeout so we can inspect what happens next.
        val attemptedValue: Try[Int] = Try {
          Await.result(queuedInstallment, Duration(200, TimeUnit.MILLISECONDS))
        }
        assert(attemptedValue match {
          case Failure(_: TimeoutException) => true
          case _ => false
        })
        // Deterministic here: this body STILL occupies the pool's only worker.
        // Do not copy this pending-state assertion into concurrent multi-worker code.
        assert(!queuedInstallment.isCompleted)
        println("Worker wait: timed out; dependent task is still queued")

        // Return its handle, releasing the worker when this body finishes.
        // The nested type is only for inspecting the demonstration, not a fix.
        queuedInstallment
      }

      // These waits are on the console caller, NOT the executor's only worker.
      val queuedAfterTimeout: Future[Int] = Await.result(blockedAttempt, consoleLimit)
      val afterTimeout: Int = Await.result(queuedAfterTimeout, consoleLimit)
      assert(afterTimeout == 55)
      println("After worker released: " + afterTimeout + " (timeout did not cancel it)")

      // Normal composition: return the next Future instead of blocking for its value.
      val composed: Future[Int] = Future {
        PremiumLesson.calculatePremium(600, 3)
      }.flatMap((premium: Int) => {
        Future { premium / 10 }
      })

      val composedValue: Int = Await.result(composed, consoleLimit)
      assert(composedValue == 55)
      println("flatMap on the same one-worker pool: " + composedValue)
    } finally {
      executor.shutdown()
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow()
      }
    }
  }
}
