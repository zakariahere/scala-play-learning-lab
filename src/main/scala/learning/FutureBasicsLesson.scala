package learning

import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

object FutureBasicsLesson {
  def run(): Unit = {
    println("--- First Future: submit work, then observe its result ---")
    val callerThread = Thread.currentThread()
    println("Caller thread: " + callerThread.getName)

    // Familiar Java machinery: one worker, owned only by this small CLI lesson.
    val executor = Executors.newSingleThreadExecutor()
    val ec = ExecutionContext.fromExecutorService(executor)
    // Explicit duration avoids introducing the extension syntax 5.seconds yet.
    val timeout = Duration(5, TimeUnit.SECONDS)

    try {
      // Future.apply submits this block using the explicit second argument list.
      // The block is not evaluated first on the caller to obtain an Int.
      // No implicit lookup, global pool, artificial sleep or external I/O here.
      val premiumFuture: Future[Int] = Future.apply {
        val workerThread = Thread.currentThread()
        println("Worker thread: " + workerThread.getName)
        assert(workerThread ne callerThread)
        PremiumLesson.calculatePremium(600, 3) // final expression: 550
      }(ec)

      println("Caller continues after submission: " + callerThread.getName)
      // This is only a momentary observation; either true or false is valid.
      println("Already completed when checked? " + premiumFuture.isCompleted)

      // This deliberately blocks the CALLER, only at the console/demo boundary.
      // It does not start the task. If already complete it can return immediately.
      // The timeout limits this wait; it does not cancel the Future's work.
      // Do not copy this wait into a web handler or another Future on this pool.
      val premium: Int = Await.result(premiumFuture, timeout)
      println("Result received by caller: " + premium + " EUR")
      println("Completed after successful wait: " + premiumFuture.isCompleted)

      assert(premium == 550)
      assert(premiumFuture.isCompleted)
    } finally {
      // Like Java finally: clean up our own executor even if an assertion fails.
      // shutdown stops new submissions and lets submitted work finish.
      executor.shutdown()
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow()
      }
    }

    // A Future is a result handle, not a thread; its context chooses execution.
    // Worker/caller println ordering and thread names are not API guarantees.
    // Other ExecutionContexts need not use this dedicated worker arrangement.
    // The worker assertion is propagated by Await if it fails.
    // Future.map/flatMap and failure recovery are the next lessons.
  }
}
