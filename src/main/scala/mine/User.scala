package mine

case class User(name: String, age: Int) {}

object User {
  def lookUpUser(name: String): Option[User] = {
    name.toLowerCase() match {
      case "zakaria" => Some(User("Zakaria", 20))
      case "amine" => Some(User("Amine", 20))
      case _ => None
    }
  }
}

