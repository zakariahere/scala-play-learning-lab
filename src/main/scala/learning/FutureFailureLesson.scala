package learning

import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

object FutureFailureLesson {
  // Demo observation only: Await throws a failed Future's exception on this
  // caller, then familiar Try captures it so we can inspect/assert the outcome.
  // This does NOT recover or change the original Future. A timeout would also
  // be captured, so assertions below check the exact expected failure objects.
  private def observe[A](future: Future[A]): Try[A] =
    Try(Await.result(future, Duration(5, TimeUnit.SECONDS)))

  def run(): Unit = {
    println("--- Failed Futures: propagation and skipped dependent work ---")
    val executor = Executors.newSingleThreadExecutor()
    implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)

    try {
      // A deliberate technical failure, not a returned PolicyInputError value.
      val sourceProblem = new IllegalArgumentException("Demo premium calculation failed")
      val failedPremium: Future[Int] = Future {
        throw sourceProblem
      }
      println("Caller received a Future handle; failure is its eventual outcome")

      val mapped: Future[String] = failedPremium.map((premium: Int) => {
        assert(false, "map callback must be skipped after source failure")
        "Premium: " + premium
      })

      val composed: Future[String] = for {
        premium <- failedPremium
        installment <- {
          assert(false, "flatMap callback must skip creating the dependent Future")
          Future { premium / 10 }
        }
      } yield {
        assert(false, "yield must be skipped after source failure")
        "Installment: " + installment
      }

      // Two other places failure can originate after a successful source.
      val goodPremium: Future[Int] = Future { PremiumLesson.calculatePremium(600, 3) }
      val mapperProblem = new IllegalStateException("Demo label transformation failed")
      val failedMapping: Future[String] = goodPremium.map((premium: Int) => {
        throw mapperProblem
      })

      val innerProblem = new ArithmeticException("Demo installment calculation failed")
      val failedInner: Future[Int] = goodPremium.flatMap((premium: Int) =>
        Future { throw innerProblem }
      )
      val successful: Future[Int] = goodPremium.map((premium: Int) => premium / 10)

      // All waits remain outside workers/callbacks, at the console boundary.
      val mappedOutcome = observe(mapped)
      val composedOutcome = observe(composed)
      val mappingOutcome = observe(failedMapping)
      val innerOutcome = observe(failedInner)
      val successOutcome = observe(successful)
      println("Failed source -> map: " + mappedOutcome)
      println("Failed source -> for: " + composedOutcome)
      println("Throw inside map: " + mappingOutcome)
      println("Failed inner Future -> flatMap: " + innerOutcome)
      println("Independent success path: " + successOutcome)

      assert(mappedOutcome == Failure(sourceProblem))
      assert(composedOutcome == Failure(sourceProblem))
      assert(mappingOutcome == Failure(mapperProblem))
      assert(innerOutcome == Failure(innerProblem))
      assert(successOutcome == Success(55))
      assert(failedPremium.isCompleted) // completed does not mean successful
    } finally {
      executor.shutdown()
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow()
      }
    }

    // No catch/recover in the chain: failure is propagated, not repaired.
    // Ordinary non-fatal exceptions are the scope here, not fatal JVM failures.
    // A try/catch around submission alone cannot catch a later worker failure.
    // Skipping dependent creation does not cancel other tasks already started.
  }
}
