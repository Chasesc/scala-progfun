package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))
  println("don't read this stop it")
  println(map((x: Int) => x > 0 && x < 11, (y: Int) => y / 2)(6))
}
