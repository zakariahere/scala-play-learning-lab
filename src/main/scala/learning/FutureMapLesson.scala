package learning

import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

object FutureMapLesson {
  def run(): Unit = {
    println("--- Future.map: describe what to do with the eventual value ---")
    val callerThread = Thread.currentThread()
    val executor = Executors.newSingleThreadExecutor()
    val ec = ExecutionContext.fromExecutorService(executor)
    val timeout = Duration(5, TimeUnit.SECONDS)

    try {
      val premiumFuture: Future[Int] = Future {
        val worker = Thread.currentThread()
        println("Calculation thread: " + worker.getName)
        assert(worker ne callerThread)
        PremiumLesson.calculatePremium(600, 3)
      }(ec)

      // Int => String is a function TYPE: accepts an Int, returns a plain String.
      // Defining this function does not execute its body.
      val toLabel: Int => String = (premium: Int) => {
        val mapperThread = Thread.currentThread()
        println("Transformation thread: " + mapperThread.getName)
        assert(mapperThread ne callerThread)
        assert(premium == 550)
        "Annual premium: " + premium + " EUR"
      }

      // Pass the function itself, NOT toLabel(550).
      // map returns another Future; it does not wait here for an Int or String.
      // The original computation is not rerun, nor is its Int changed to String.
      val labelFuture: Future[String] = premiumFuture.map(toLabel)(ec)
      println("Caller continues after wiring map: " + callerThread.getName)

      // One final blocking wait for this console demonstration only.
      // No Await inside the Future or transformation, or between the two steps.
      val label: String = Await.result(labelFuture, timeout)
      println("Final label: " + label)
      assert(label == "Annual premium: 550 EUR")
      assert(premiumFuture.isCompleted)
    } finally {
      executor.shutdown()
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow()
      }
    }

    // The same one-worker executor handles both calculation and transformation.
    // A Future or map does not imply a fresh thread for every operation.
    // The mapper needs the successful source result; caller-print order can vary.
    // Registration also works if the source Future has already completed.
    // map is not recovery: source failure skips this function, and a non-fatal
    // exception thrown by the function fails the resulting Future.
    // Failure behavior and Future-returning functions are separate next lessons.
  }
}
