package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */

  /*
  pascal's triangle without dynamic programming / memoization.
  This is very inefficient due to calculating the same thing multiple times,
  but this will only be used for very small values.
  */
  def naive_pascal(c: Int, r: Int): Int = {
    if(c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  def pascal(c: Int, r: Int): Int = naive_pascal(c, r)
  
  /**
   * Exercise 2
   */

  def generalized_balance(chars: List[Char], left_char: Char, right_char: Char): Boolean = {
    def doesMatchExist(chars: List[Char], unmatched_left: Int, unmatched_right: Int): Boolean = {
      if(chars.isEmpty) unmatched_left == unmatched_right
      else if(unmatched_left < unmatched_right) false
      else {
        if (chars.head == left_char)
          doesMatchExist(chars.tail, unmatched_left + 1, unmatched_right)
        else if (chars.head == right_char)
          doesMatchExist(chars.tail, unmatched_left, unmatched_right + 1)
        else
          doesMatchExist(chars.tail, unmatched_left, unmatched_right)
      }
    }

    doesMatchExist(chars, 0, 0)
  }

  /*
  returns true if the parentheses are balanced and false otherwise

  examples:
  balance("(if (zero? x) max (/ 1 x))".toList) => true
  balance("I told him (that it’s not (yet) done). (But he wasn’t listening)".toList) => true
  balance(":)".toList) => false
  balance("())(".toList) => false
  */
  def balance(chars: List[Char]): Boolean = generalized_balance(chars, '(', ')')
  
  /**
   * Exercise 3
   */

  /*
  Again, this could be vastly improved using dynamic programming / memoization, but this will
  only be called using small values.
  */
  def countChange(money: Int, coins: List[Int]): Int = {
    if(money < 0 || coins.isEmpty) 0
    else if(money == 0) 1
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
