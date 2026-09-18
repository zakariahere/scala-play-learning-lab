package learning

object FlatMapLesson {
  // Fictional contact lookup: only POL-001 has an email in this example.
  def findContactEmail(policy: PolicySnapshot): Option[String] = {
    if (policy.number == "POL-001") Some("customer@example.com")
    else None
  }

  def emailWithMap(policy: Option[PolicySnapshot]): Option[Option[String]] = {
    policy.map((p: PolicySnapshot) => findContactEmail(p))
  }

  def emailWithMatch(policy: Option[PolicySnapshot]): Option[String] = {
    policy match {
      // The lookup already returns an Option: return it directly.
      case Some(p) => findContactEmail(p)
      case None => None
    }
  }

  def emailWithFlatMap(policy: Option[PolicySnapshot]): Option[String] = {
    policy.flatMap((p: PolicySnapshot) => findContactEmail(p))
  }

  def run(): Unit = {
    println()
    println("--- flatMap: a second optional lookup ---")
    val found = OptionLesson.findPolicy("POL-001")
    // Direct fixture: a policy exists, but the contact lookup has no email.
    val noEmail: Option[PolicySnapshot] = Some(PolicySnapshot("POL-002", 600, 2))
    val missing = OptionLesson.findPolicy("POL-999")

    println("map, both found: " + emailWithMap(found))
    println("map, no email: " + emailWithMap(noEmail))
    println("map, no policy: " + emailWithMap(missing))
    println("flatMap, both found: " + emailWithFlatMap(found))
    println("flatMap, no email: " + emailWithFlatMap(noEmail))
    println("flatMap, no policy: " + emailWithFlatMap(missing))

    // Check the explicit match and flatMap agree for all three cases.
    assert(emailWithMatch(found) == emailWithFlatMap(found))
    assert(emailWithMatch(noEmail) == emailWithFlatMap(noEmail))
    assert(emailWithMatch(missing) == emailWithFlatMap(missing))
  }
}
