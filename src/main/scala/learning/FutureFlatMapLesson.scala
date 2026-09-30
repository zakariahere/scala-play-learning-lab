package learning

import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

object FutureFlatMapLesson {
  def run(): Unit = {
    println("--- Future.flatMap: the next function returns a Future too ---")
    val executor = Executors.newSingleThreadExecutor()
    val ec = ExecutionContext.fromExecutorService(executor)
    val timeout = Duration(5, TimeUnit.SECONDS)

    try {
      // One submitted source computation, reused by both comparisons below.
      val premiumFuture: Future[Int] = Future {
        println("Premium calculation thread: " + Thread.currentThread().getName)
        PremiumLesson.calculatePremium(600, 3) // 550
      }(ec)

      // Unlike the previous Int => String function, this returns Future[Int].
      // Calling it submits a new task; that task's final expression is an Int.
      // Toy whole-euro calculation only, not a production payment schedule.
      val toInstallment: Int => Future[Int] = (premium: Int) => Future {
        println("Installment calculation thread: " + Thread.currentThread().getName)
        premium / 10 // 550 / 10 = 55
      }(ec)

      // map treats the returned Future[Int] as an ordinary result value.
      val nested: Future[Future[Int]] = premiumFuture.map(toInstallment)(ec)

      // flatMap connects completion to that returned Future instead.
      val flat: Future[Int] = premiumFuture.flatMap(toInstallment)(ec)
      println("Caller continues after wiring both comparisons")

      // Console inspection ONLY: awaiting the outer nested future gives a
      // Future[Int], not an Int. The inner may already be complete, or not.
      val innerFuture: Future[Int] = Await.result(nested, timeout)
      val nestedResult: Int = Await.result(innerFuture, timeout)
      // The flat version exposes the final Int with one console wait.
      val flatResult: Int = Await.result(flat, timeout)

      println("map: outer result is Future[Int]; inner result = " + nestedResult)
      println("flatMap: final result = " + flatResult)
      assert(nestedResult == 55)
      assert(flatResult == 55)
      assert(nestedResult == flatResult)
      assert(premiumFuture.isCompleted)
      assert(innerFuture.isCompleted)
    } finally {
      executor.shutdown()
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow()
      }
    }

    // Both branches are intentionally run for comparison: toInstallment is
    // called twice, once per branch. That is not flatMap duplicating work.
    // No Await inside a Future or callback; even one worker can run this chain.
    // Callback/print order is not a contract; dependencies, not speed, matter.
    // flatMap is not recovery: source failure skips the function; inner failure
    // fails the flat result. A successful outer nested future alone does not
    // establish inner success. We will exercise failures in a later lesson.
  }
}
