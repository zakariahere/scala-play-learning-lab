package learning

object GenericMethodLesson {
  // Same operation, repeated only because the concrete types differ.
  def stringAsList(value: String): List[String] = List(value)
  def intAsList(value: Int): List[Int] = List(value)

  // Java shape (with java.util.List and java.util.Collections imported):
  // static <T> List<T> asList(T value) {
  //   return Collections.singletonList(value);
  // }
  // [A] introduces a TYPE parameter, like Java's <T>.
  // (value: A) receives a VALUE of that type; List[A] preserves the relationship.
  // A is a name chosen here, not a keyword or an abbreviation for Any.
  def asList[A](value: A): List[A] = List(value)

  def run(): Unit = {
    println("--- Generic methods: Java <T>, Scala [A] ---")

    // Explicit type arguments first: [String] supplies A; (...) supplies value.
    val explicitStrings: List[String] = asList[String]("POL-001")
    val explicitInts: List[Int] = asList[Int](12)
    println("Explicit String: " + explicitStrings)
    println("Explicit Int: " + explicitInts)

    // The compiler can infer A from each input here. Still statically typed.
    val inferredStrings = asList("POL-001")
    val inferredInts = asList(12)
    // These assignments verify the inferred result types at compile time.
    val stringsChecked: List[String] = inferredStrings
    val intsChecked: List[Int] = inferredInts
    println("Inferred String: " + stringsChecked)
    println("Inferred Int: " + intsChecked)

    // No extra overload is needed for an existing domain type.
    val policy = PolicySnapshot("POL-001", 600, 3)
    val policies: List[PolicySnapshot] = asList[PolicySnapshot](policy)
    println("Explicit PolicySnapshot: " + policies)

    assert(explicitStrings == List("POL-001"))
    assert(explicitInts == List(12))
    assert(explicitStrings == stringAsList("POL-001"))
    assert(explicitInts == intAsList(12))
    assert(stringsChecked == explicitStrings)
    assert(intsChecked == explicitInts)
    assert(policies == List(policy))
  }
}
