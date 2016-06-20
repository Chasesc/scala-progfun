package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

    val s1to10 = (x: Int) => x >= 1 && x <= 10
    val s1to100Evens = (x: Int) => x >= 1 && x <= 100 && x % 2 == 0
    val s1toInf = (x: Int) => x >= 1
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union(s: Set, t: Set): Set: returns a new Set containing all elements in s or t") {
    new TestSets {
      val s1to2 = union(s1, s2)
      assert(contains(s1to2, 1),   "union: 1 is an element of {1, 2}")
      assert(contains(s1to2, 2),   "union: 2 is an element of {1, 2}")
      assert(!contains(s1to2, 3),  "union: 3 is not an element of {1, 2}")

      val s1and2 = union(s1, s3)
      assert(contains(s1and2, 1),  "union: 1 is an element of {1, 3}")
      assert(!contains(s1and2, 2), "union: 2 is not an element of {1, 3}")
      assert(contains(s1and2, 3),  "union: 3 is an element of {1, 3}")
    }
  }

  test("intersect(s: Set, t: Set): Set: only contains elements in both s and t") {
    new TestSets {
      val sempty = intersect(s1, s2)
      assert(!contains(sempty, 1), "intersect: 1 is not an element of {}")
      assert(!contains(sempty, 2), "intersect: 2 is not an element of {}")
      assert(!contains(sempty, 3), "intersect: 3 is not an element of {}")

      val one = intersect(intersect(s1, s1), s1toInf)
      assert( contains(one, 1),    "intersect: 1 is an element of {1}")
      assert(!contains(one, 2),    "intersect: 2 is not an element of {1}")
      assert(!contains(one, 3),    "intersect: 3 is not an element of {1}")
    }
  }

  test("diff(s: Set, t: Set): Set: returns a set which contains all the elements of the set s that are not in the set t") {
    new TestSets {
      val sempty = diff(s1, s1)
      assert(!contains(sempty, 1), "diff: 1 is not an element of {}")
      assert(!contains(sempty, 2), "diff: 2 is not an element of {}")
      assert(!contains(sempty, 3), "diff: 3 is not an element of {}")

      val one = diff(s1, diff(s3, s3))
      assert( contains(one, 1),    "diff: 1 is an element of {1}")
      assert(!contains(one, 2),    "diff: 2 is not an element of {1}")
      assert(!contains(one, 3),    "diff: 3 is not an element of {1}")
    }
  }

  test("filter(s: Set, p: Int => Boolean): Set: returns the subset of s for which p holds.") {
    new TestSets {
      val s52to100Evens = filter(s1to100Evens, x => x >= 52)
      assert( contains(s52to100Evens, 52),  "filter: 52  is an element of {52, 54, ..., 100}")
      assert( contains(s52to100Evens, 100), "filter: 100 is an element of {52, 54, ..., 100}")
      assert(!contains(s52to100Evens, 120), "filter: 120 is not an element of {52, 54, ..., 100}")

      val two = filter(union(s1, s2), x => x != 1)
      assert(!contains(two, 1), "filter: 1 is not an element of {2}")
      assert( contains(two, 2), "filter: 2 is an element of {2}")
      assert(!contains(two, 3), "filter: 3 is not an element of {2}")
    }
  }

  test("forall(s: Set, p: Int => Boolean): Boolean: returns true if all elements in s satisfy p and false otherwise.") {
    new TestSets {
      assert(!forall(s => s >= 0, x => x > 1), "forall x in {0, 1, 2, ...} x > 1")

      assert( forall(s1, x => x == 1),         "forall x in {1} x = 1")
    }
  }

  test("exists(s: Set, p: Int => Boolean): Boolean: Returns whether there exists a bounded integer within s that satisfies p") {
    new TestSets {
      assert(exists(s => s > 0, x => x == 23),   "there exists x in {1, 2, 3, ...} s.t x = 23")

      assert(exists(s1to100Evens, x => x == 24), "there exists x in {2, 4, ..., 100} s.t x = 24 ")
    }
  }


  test("map(s: Set, f: Int => Int): Set: transform a set s by applying f to each element of s") {
    new TestSets {
      assert(!map(s1to10, (y: Int) => y / 2)(6),   "map: the set of ints from 1-10 mapped to y / 2 does not contain 6")

      assert( map(s1to10, (y: Int) => y * y)(100), "map: the set of ints from 1-10 mapped to their squares contains 100")
    }
  }


}
