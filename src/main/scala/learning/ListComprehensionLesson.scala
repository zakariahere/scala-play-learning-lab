package learning

object ListComprehensionLesson {
  def run(): Unit = {
    println("--- List for-comprehensions: transform, then add a guard ---")

    val policies: List[PolicySnapshot] = List(
      PolicySnapshot("POL-001", 600, 3),
      PolicySnapshot("POL-002", 600, 2),
      PolicySnapshot("POL-003", 500, 3)
    )

    // One generator: policy is one PolicySnapshot, not the whole List.
    // yield contributes a String for each policy; the result is List[String].
    val numbers: List[String] = for {
      policy <- policies
    } yield policy.number

    val numbersWithMap: List[String] =
      policies.map(policy => policy.number)

    // The guard decides whether this policy reaches yield. No else is needed.
    val experiencedNumbers: List[String] = for {
      policy <- policies
      if policy.claimFreeYears >= 3
    } yield policy.number

    // Scala 2 translates this simple guarded comprehension to withFilter + map.
    // withFilter defers selection until map, avoiding a filtered intermediate List.
    val experiencedWithMethods: List[String] = policies
      .withFilter(policy => policy.claimFreeYears >= 3)
      .map(policy => policy.number)

    // Same values for these pure functions, but filter builds an intermediate List.
    val experiencedWithFilter: List[String] = policies
      .filter(policy => policy.claimFreeYears >= 3)
      .map(policy => policy.number)

    val noMatches: List[String] = for {
      policy <- policies
      if policy.claimFreeYears >= 10
    } yield policy.number

    val emptyInput: List[String] = for {
      policy <- List.empty[PolicySnapshot]
    } yield policy.number

    println("One generator: " + numbers)
    println("Guard >= 3: " + experiencedNumbers)
    println("withFilter + map: " + experiencedWithMethods)
    println("Guard >= 10: " + noMatches)
    println("Empty input: " + emptyInput)

    assert(numbers == List("POL-001", "POL-002", "POL-003"))
    assert(numbers == numbersWithMap)
    assert(experiencedNumbers == List("POL-001", "POL-003"))
    assert(experiencedNumbers == experiencedWithMethods)
    assert(experiencedNumbers == experiencedWithFilter)
    assert(noMatches.isEmpty)
    assert(emptyInput.isEmpty)

    println("--- Two generators: each policy, then each channel ---")

    // These are labels only: this lesson sends no notifications.
    val channels: List[String] = List("email", "sms")

    // For each policy, visit BOTH channels in their list order.
    // policy and channel are both available to the yield expression.
    val notifications: List[String] = for {
      policy <- policies
      channel <- channels
    } yield policy.number + " via " + channel

    // The inner map builds one List[String] per policy.
    // The outer flatMap concatenates those lists into one List[String].
    val notificationsWithMethods: List[String] = policies.flatMap { policy =>
      channels.map { channel =>
        policy.number + " via " + channel
      }
    }

    // Using map on the outside instead preserves the separate inner lists.
    val grouped: List[List[String]] = policies.map { policy =>
      channels.map { channel =>
        policy.number + " via " + channel
      }
    }

    val noNotifications: List[String] = for {
      policy <- policies
      channel <- List.empty[String]
    } yield policy.number + " via " + channel

    println("Two generators: " + notifications)
    println("flatMap + map: " + notificationsWithMethods)
    println("map + map (nested): " + grouped)
    println("No channels: " + noNotifications)

    val expected = List(
      "POL-001 via email", "POL-001 via sms",
      "POL-002 via email", "POL-002 via sms",
      "POL-003 via email", "POL-003 via sms"
    )
    assert(notifications == expected)
    assert(notificationsWithMethods == expected)
    assert(grouped == List(
      List("POL-001 via email", "POL-001 via sms"),
      List("POL-002 via email", "POL-002 via sms"),
      List("POL-003 via email", "POL-003 via sms")
    ))
    assert(noNotifications.isEmpty)
  }
}
