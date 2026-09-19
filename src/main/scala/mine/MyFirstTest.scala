package mine

object MyFirstTest {

  def main(args: Array[String]): Unit = {
    val zaKariaMaybeUser: Option[User] = User.lookUpUser("ZaKaria")
    println(zaKariaMaybeUser.isDefined)
    println(zaKariaMaybeUser.getOrElse("not found"))
    println(zaKariaMaybeUser.map(user => user.name).getOrElse("User not found"))
  }

}
