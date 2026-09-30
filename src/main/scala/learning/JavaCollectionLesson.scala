package learning

// Scala 2 wildcard import: enables the extension-style asScala/asJava methods.
// jdk is a Scala standard-library package here; no extra dependency is needed.
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

object JavaCollectionLesson {
  def run(): Unit = {
    println("--- Java collections: an adapter versus a snapshot ---")

    // Ordinary Java objects/methods, called directly from Scala.
    // Fully qualified names distinguish java.util.List from Scala's List.
    val javaNumbers: java.util.List[String] = new java.util.ArrayList[String]()
    javaNumbers.add("POL-001")
    javaNumbers.add("POL-002")

    // Explicit converter call first; the import enables the shorter second form.
    val explicitView: scala.collection.mutable.Buffer[String] =
      scala.jdk.javaapi.CollectionConverters.asScala(javaNumbers)
    val scalaView: scala.collection.mutable.Buffer[String] = javaNumbers.asScala
    // Buffer is an ordered, mutable Scala collection, not an immutable List.
    // These wrappers delegate to the Java list; they do not copy its elements.
    val snapshot: List[String] = scalaView.toList
    println("Initial snapshot: " + snapshot)
    assert(snapshot == List("POL-001", "POL-002"))

    // Changing Java is visible through both Scala adapters.
    javaNumbers.add("POL-003")
    println("After Java add, Scala sees: " + scalaView.toList)
    assert(scalaView.toList == List("POL-001", "POL-002", "POL-003"))
    assert(explicitView.toList == scalaView.toList)

    // update(index, value) replaces the element at that zero-based position.
    // This is the Scala counterpart of javaNumbers.set(0, "POL-010").
    scalaView.update(0, "POL-010")
    println("After Scala update, Java sees: " + javaNumbers)
    println("Earlier snapshot still contains: " + snapshot)
    assert(javaNumbers.get(0) == "POL-010")
    assert(explicitView(0) == "POL-010")
    assert(snapshot == List("POL-001", "POL-002"))

    val roundTrip: java.util.List[String] = scalaView.asJava
    println("Java -> Scala -> Java returns original instance: " + (roundTrip eq javaNumbers))
    assert(roundTrip eq javaNumbers)

    println("--- Immutable Scala List through a Java interface ---")
    val javaSnapshot: java.util.List[String] = snapshot.asJava
    assert(javaSnapshot.get(0) == "POL-001")
    // A Java List interface does NOT guarantee that writes are supported.
    // Use familiar Try to observe the runtime rejection without stopping the lab.
    val appendAttempt: Try[Boolean] = Try(javaSnapshot.add("POL-999"))
    val rejection: String = appendAttempt match {
      case Failure(_: UnsupportedOperationException) => "UnsupportedOperationException"
      case Failure(other) => throw other
      case Success(_) => throw new AssertionError("Immutable List adapter allowed add")
    }
    println("Appending through immutable List adapter: " + rejection)
    assert(rejection == "UnsupportedOperationException")
    assert(javaSnapshot.size() == 2)

    // When a Java API needs an independently writable list, explicitly copy.
    val writableCopy = new java.util.ArrayList[String](javaSnapshot)
    writableCopy.add("POL-999")
    println("Independent writable Java copy: " + writableCopy)
    assert(writableCopy.size() == 3)
    assert(snapshot == List("POL-001", "POL-002"))

    // toList/ArrayList copy the collection structure, not the objects inside.
    // Strings are immutable here; mutable elements would still be shared.
    // An adapter also adds no thread safety. Concurrency is a later lesson.
  }
}
