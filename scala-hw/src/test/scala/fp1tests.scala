import org.scalatest.*
import org.scalatest.prop.TableDrivenPropertyChecks.*

class fp1tests extends UnitSpec:
  val EX : Map[Int, Tag] =
    (for i <- (1 to 10).toList yield {
      object T extends Tag ("fp1ex%02d".format (i))
      (i, T)
    }).toMap

  import fp1.*

  property ("EX01 - factTest", EX (1)) {
    assert (factTest === List (1, 2, 6, 24, 120))
  }

  property ("EX02 - fib", EX (2)) {
    val fibTable = Table (
      ("n", "fib (n)"),
      ( 0,  0),
      ( 1,  1),
      ( 2,  1),
      ( 3,  2),
      ( 4,  3),
      ( 5,  5),
      ( 6,  8),
      ( 7, 13),
      ( 8, 21),
      ( 9, 34),
      (10, 55),
      (11, 89)
    )
    forAll (fibTable) { (n : Int, res : Int) =>
      assert (fib (n) ===  res)
    }
  }

  val sumTable = Table (
    ("xs", "sum (xs)"),
    (Nil,  0),
    (List (15),  15),
    ((0 to 100).toList,  5050)
  )

  property ("EX03 - sum", EX (3)) {
    forAll (sumTable) { (xs : List[Int], res : Int) =>
      assert (sum (xs) ===  res)
    }
  }

  property ("EX04 - sumTail", EX (4)) {
    forAll (sumTable) { (xs : List[Int], res : Int) =>
      assert (sumTail (xs) ===  res)
    }
  }

  val maxTable = Table (
    ("xs", "max (xs)"),
    (List (15),  15),
    ((0 to 100).toList,  100),
    ((0 to 100).toList.reverse,  100),
    ((0 to 50).toList ::: ((30 to 70).toList.reverse),  70)
  )
  val maxEmptyTable = Table (
    ("xs"),
    Nil
  )

  property ("EX05 - maxList", EX (5)) {
    forAll (maxTable) { (xs : List[Int], res : Int) =>
      assert (maxList (xs) ===  res)
    }
    forAll (maxEmptyTable) { (xs : List[Int]) =>
      a [NoSuchElementException] `should` be thrownBy { maxList(xs) }
    }
  }


  property ("EX06 - maxTailAux", EX (6)) {
    forAll (maxTable) { (xs : List[Int], res : Int) =>
      assert (maxTail (xs) ===  res)
    }
    forAll (maxEmptyTable) { (xs : List[Int]) =>
      a [NoSuchElementException] `should` be thrownBy { maxTail(xs) }
    }
  }

  property ("EX07 - constant5", EX (7)) {
    assert ({ 
      val r1 : Int = constant5 ()
      val r2 : Int = constant5 ()
      (   r1, r2)
    } === (5,  5)
    )
  }

  property ("EX08 - constant", EX (8)) {
    assert ({ 
      val k1 : () => Int = constant (1)
      val k2 : () => Int = constant (2)
      val r1 : Int = k1 ()
      val r2 : Int = k1 ()
      val r3 : Int = k2 ()
      val r4 : Int = k2 ()
      val r5 : Int = k2 ()
      val r6 : Int = k1 ()
      (   r1, r2, r3, r4, r5, r6)
    } === (1,  1,  2,  2,  2,  1)
    )
  }

  property ("EX09 - counter0", EX (9)) {
    assert ({ 
      val r1 : Int = counter0 ()
      val r2 : Int = counter0 ()
      val r3 : Int = counter0 ()
      (   r1, r2, r3)
    } === (0,  1,  2)
    )
  }

  property ("EX10 - counter", EX (10)) {
    assert ({ 
      val k1 : () => Int = counter (1)
      val k2 : () => Int = counter (2)
      val r1 : Int = k1 ()
      val r2 : Int = k1 ()
      val r3 : Int = k2 ()
      val r4 : Int = k2 ()
      val r5 : Int = k2 ()
      val r6 : Int = k1 ()
      (   r1, r2, r3, r4, r5, r6)
    } === (1,  2,  2,  3,  4,  3)
    )
  }  

