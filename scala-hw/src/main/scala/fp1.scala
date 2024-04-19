  // GENERATED
/* INSTRUCTIONS
 *
 * Complete the exercises below.  For each "EXERCISE" comment, add code
 * immediately below the comment.
 *
 * Please see README.md for instructions, including compilation and testing.
 *
 * GRADING
 *
 * 1. Submissions MUST compile using SBT with UNCHANGED configuration and tests
 *    with no compilation errors.  Submissions with compilation errors will
 *    receive 0 points.  Note that refactoring the code will cause the tests to
 *    fail.
 *
 * 2. You MUST NOT edit the SBT configuration and tests.  Altering it in your
 *    submission will result in 0 points for this assignment.
 *
 * 3. You MUST NOT use while loops or (re)assignment to variables (you can use
 *    "val" declarations, but not "var" declarations).  You must use recursion
 *    instead.
 *
 * 4. You may declare auxiliary functions if you like.
 *
 * SUBMISSION
 *
 * 1. Submit this file on D2L before the deadline.
 *
 * 2. Late submissions will not be permitted because solutions will be discussed
 *    in class.
 *
 */
import java.util.NoSuchElementException
object fp1:

  // Here is a utility function for logging recursive functions.
  // It may be helpful while debugging
  // Don't use this in the solutions you include in your final submission.
  def log[X](prefix: String)(computeResult: => X) =
    println(prefix)
    val result = computeResult
    println(s"$prefix : $result")
    result
  // Note: computeResult is a "by-name" parameter.  We will discuss these
  // later in the course.  Short version: a by-name parameter is non-strict;
  // it is reevaluated every time it is used in the function body.

  // EXAMPLE: here is the definition of the factorial function.
  def fact(n: Int): Int =
    if n <= 1 then 1
    else n * fact(n - 1)

  // EXAMPLE: factorial with logging
  def factLog(n: Int): Int =
    log(s"fact($n)"):
      if n <= 1 then 1
      else n * factLog(n - 1)


  // Note that the fact computes as follows (leaving out some steps):
  //
  // fact (5)
  // --> if 5 <= 1 then 1 else 5 * fact (5 - 1)
  // --> if false then 1 else 5 * fact (5 - 1)
  // --> 5 * fact (5 - 1)
  // --> 5 * fact (4)
  // --> 5 * (if 4 <= 1 then 1 else 4 * fact (4 - 1))
  // --> 5 * (4 * fact (3))
  // --> 5 * (4 * (3 * fact (2))
  // --> 5 * (4 * (3 * (2 * fact (1)))
  // --> 5 * (4 * (3 * (2 * (if 1 <= 1 then 1 else 1 * fact (1 - 1)))))
  // --> 5 * (4 * (3 * (2 * (if true then 1 else 1 * fact (1 - 1)))))
  // --> 5 * (4 * (3 * (2 * 1)))
  // --> 5 * (4 * (3 * 2))
  // --> 5 * (4 * 6)
  // --> 5 * 24
  // --> 120
  //
  // We can get the same answer with less work by starting at the base case and
  // computing up:
  //
  // fact (1) --> 1
  // fact (2) --> 2 * fact (1) --> 2 * 1 --> 2
  // fact (3) --> 3 * fact (2) --> 3 * 2 --> 6
  // fact (4) --> 4 * fact (3) --> 4 * 6 --> 24
  // fact (5) --> 5 * fact (4) --> 5 * 24 --> 120

  // EXERCISE 1: complete the following definition, so that factTest is the list
  // of integers List(1,2,6,24,120).
  //
  // You must call the "fact" function (five times) defined above instead of
  // hardcoding the numbers 1,2,6,24,120.
  val factTest: List[Int] =
    // This answer utilizes a library funtion (map)
    (1 to 5).toList.map(fact)

  // EXERCISE 2: complete the following definition of the Fibonacci function.
  // You can use the mathematical definition of Fib:
  // https://en.wikipedia.org/wiki/Fibonacci_number
  //
  // fib(0) == 0
  // fib(1) == 1
  // fib(n) == fib(n-1) + fib(n-2), if n>1
  def fib(n: Int): Int =
    n match
      case 0 => 0
      case 1 => 1
      case _ if n > 1 => fib(n - 1) + fib(n - 2)


  // EXERCISE 3: write a function "sum" that takes a list of integers and
  // sums them.
  //
  // As with all of the exercises in this assignment, your function MUST be
  // recursive and MUST NOT use a while loop.
  def sum(xs: List[Int]): Int =
    if xs.isEmpty then 0 // Base case
    else xs.head + sum(xs.tail)


  // EXERCISE 4: given the definition of the function "sumTailAux" below,
  // complete the definition of the function "sumTail" so that it also sums a
  // list of integers.
  //
  // You must not alter the definition of "sumTailAux".
  //
  // Your definition for "sumTail" must call "sumTailAux" directly, and must
  // not call "sum"
  def sumTailAux(xs: List[Int], accumulator: Int): Int =
    xs match
      case Nil     => accumulator
      case y :: ys => sumTailAux(ys, accumulator + y)

  def sumTail(xs: List[Int]): Int =
    sumTailAux(xs, 0) // 0 is the initial accumulator


  // EXERCISE 5: complete the following definition of the function "max" that
  // finds the maximum integer in a list of integers.
  //
  // If the list is empty, throw a java.util.NoSuchElementException (with no
  // argument).
  //
  // Your function MUST be recursive and MUST NOT use a while loop.
  //
  // You MUST NOT use the "max" method on lists, but can use the "max" method
  // on integers: That is, you cannot use (xs.max) or similar.  But you can
  // use (1 max 2).
  def maxList(xs: List[Int]): Int =
    xs match
      case Nil => throw new java.util.NoSuchElementException() // If list is empty, throw an exception
      case head :: Nil => head // If the list has only one element, return it
      case head :: tail => head max maxList(tail) // Use recursion to find the max of the rest of the list


  // EXERCISE 6: given the definition of the function "maxTail" below,
  // complete the definition of the function "maxTailAux" so that "maxTail"
  // also finds the maximum of a list of integers.
  //
  // You must not alter the definition of "maxTail".
  //
  // Your definition for "maxTailAux" must be recursive and not use while
  // loops.
  def maxTailAux(xs: List[Int], accumulator: Int): Int =
    xs match
      case Nil => accumulator // Return the accumulator if no more elements are left
      case y :: ys => maxTailAux(ys, y max accumulator) // Recurse with the rest of the list, updating the accumulator to the maximum value found so far

  def maxTail(xs: List[Int]): Int =
    xs match
      case Nil     => throw NoSuchElementException()
      case y :: ys => maxTailAux(ys, y)


  // EXERCISE 7: Complete the following definition, so that "constant5" is a
  // function that returns 5 whenever it is invoked.
  val constant5: () => Int =
    () => 5 // Something about an outer function (thats not there?) returning the inner function() => 5? Explain more in class please.

  // EXERCISE 8: Complete the following definition, so that "constant" is a
  // function that when invoked with integer n returns a function that
  // returns n whenever it is invoked.
  val constant: Int => () => Int =
    (n: Int) => () => n // Something about an outer function returning the inner function? Explain more in class please. 

  // EXERCISE 9: Complete the following definition, so that "counter0" is a
  // (stateful) function that returns 0 when it is first invoked, then 1,
  // then 2, etc.
  //
  // REMEMBER: you can use "var" but everything you add has to be inside the
  // "{...}" body of "counter0".

  // This rule applies throughout this assignment.
  val counter0: () => Int =
    var count = 0 // Temporary variable
    () => {
      val result = count  // Store the current value to return
      count += 1  // Increment the counter for the next call
      result  // Return the stored value
    }

  // EXERCISE 10: Complete the following definition, so that "counter" is a
  // (stateless) function that when invoked with integer n returns a
  // (stateful) function that returns n when it is first invoked, then n+1,
  // then n+2, etc.
  //
  // The counters must be independent, i.e., running "counter (0)" twice
  // should yield two functions that do not interfere with one another's
  // state.
  val counter: Int => () => Int =
    (n: Int) => {
      var count = n - 1 // Start from n-1 so the first call returns n
      () => {
        count += 1      // Increment count each call
        count           // Return the current count
      }
    }


// SCALA IS HARD TO UNDERSTAND AT TIMES
