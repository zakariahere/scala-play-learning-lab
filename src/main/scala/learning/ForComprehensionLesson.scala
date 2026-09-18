package learning

object ForComprehensionLesson {
  def contactLabelWithMethods(result: Option[PolicySnapshot]): Option[String] = {
    result.flatMap((policy: PolicySnapshot) => {
      FlatMapLesson.findContactEmail(policy).map((email: String) => {
        policy.number + " -> " + email
      })
    })
  }

  def contactLabelWithFor(result: Option[PolicySnapshot]): Option[String] = {
    for {
      // policy is a PolicySnapshot inside the remaining expression.
      policy <- result
      // email is a String; this lookup runs only if a policy exists.
      email <- FlatMapLesson.findContactEmail(policy)
    } yield policy.number + " -> " + email
    // yield supplies the final map body: String inside Option[String].
  }

  def run(): Unit = {
    println("--- For-comprehensions: flatMap and map in another form ---")
    val found = OptionLesson.findPolicy("POL-001")
    val missing = OptionLesson.findPolicy("POL-999")
    // Direct fixture, as in FlatMapLesson: existing policy without contact email.
    val noEmail: Option[PolicySnapshot] = Some(PolicySnapshot("POL-002", 600, 2))

    println("Methods, both found: " + contactLabelWithMethods(found))
    println("For, both found: " + contactLabelWithFor(found))
    println("For, no email: " + contactLabelWithFor(noEmail))
    println("For, no policy: " + contactLabelWithFor(missing))

    val expected = Some("POL-001 -> customer@example.com")
    assert(contactLabelWithMethods(found) == expected)
    assert(contactLabelWithFor(found) == expected)
    assert(contactLabelWithMethods(noEmail) == None)
    assert(contactLabelWithFor(noEmail) == None)
    assert(contactLabelWithMethods(missing) == None)
    assert(contactLabelWithFor(missing) == None)
  }
}
