package learning

import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

object FutureForLesson {
  def run(): Unit = {
    println("--- Future for-comprehension: the same flatMap/map chain ---")
    val executor = Executors.newSingleThreadExecutor()
    // Same implicit-argument mechanism as our earlier repository lesson.
    // This val makes ec eligible for the generated map/flatMap argument lists.
    // It does not create another executor or ask a framework for one.
    implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
    val timeout = Duration(5, TimeUnit.SECONDS)

    try {
      val premiumFuture: Future[Int] = Future {
        PremiumLesson.calculatePremium(600, 3) // 550
      }(ec)

      val toInstallment: Int => Future[Int] = (premium: Int) => Future {
        premium / 10 // illustrative whole-euro calculation
      }(ec)

      // Explicit arguments first: first flatMap, then a final map.
      val withMethods: Future[String] =
        premiumFuture.flatMap((premium: Int) =>
          toInstallment(premium).map((installment: Int) =>
            premium.toString + " EUR / 10 = " + installment + " EUR"
          )(ec)
        )(ec)

      // The compiler supplies ec to the generated flatMap and map calls.
      // premium/ installment are successful Int values in the callbacks.
      // yield produces a plain String; the whole expression is Future[String].
      val withFor: Future[String] = for {
        premium <- premiumFuture
        installment <- toInstallment(premium)
      } yield premium.toString + " EUR / 10 = " + installment + " EUR"

      println("Caller continues after composing both versions")
      // Final console observations only, not blocking composition.
      val methodsResult: String = Await.result(withMethods, timeout)
      val forResult: String = Await.result(withFor, timeout)
      println("flatMap/map: " + methodsResult)
      println("for/yield: " + forResult)
      assert(methodsResult == "550 EUR / 10 = 55 EUR")
      assert(forResult == "550 EUR / 10 = 55 EUR")
      assert(methodsResult == forResult)
      assert(premiumFuture.isCompleted)
    } finally {
      executor.shutdown()
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow()
      }
    }

    // Both versions run: one shared premium calculation, two installment tasks.
    // Here the second task is created inside the dependent callback, after the
    // premium succeeds. for itself does NOT start, await, or parallelize work.
    // Futures created before a for may already be running; placement matters.
    // No guards/withFilter or Future-returning yield introduced in this lesson.
    // Failures propagate through the same methods; exercise them next.
  }
}
