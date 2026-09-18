package mine

object MyFirstTest {

  def main(args: Array[String]): Unit = {
    println(sayHello("Zakaria"))
  }

  def sayHello(name: String) : String = {
    return "Hello ".formatted(name)
  }

}
