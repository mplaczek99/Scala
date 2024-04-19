import org.scalatest.*
import org.scalatest.prop.TableDrivenPropertyChecks.*

class fp4tests extends UnitSpec:
  val EX : Map[Int, Tag] = 
    (for i <- (1 to 8).toList yield {
      object T extends Tag ("fp4ex%02d".format (i))
      (i, T)
    }).toMap

  import fp4.*

    property ("EX01 - constant5", EX (1)) {
    assert ({ 
      val r1 : Int = constant5 ()
      val r2 : Int = constant5 ()
      (   r1, r2)
    } === (5,  5)
    )
  }

  property ("EX02 - constant", EX (2)) {
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

  property ("EX03 - counter0", EX (3)) {
    assert ({ 
      val r1 : Int = counter0 ()
      val r2 : Int = counter0 ()
      val r3 : Int = counter0 ()
      (   r1, r2, r3)
    } === (0,  1,  2)
    )
  }

  property ("EX04 - counter", EX (4)) {
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

  property ("EX05 - getAndSet", EX (5)) {
    assert ({ 
      val (get1, set1) : (() => Int, Int => Unit) = getAndSet (1)
      val (get2, set2) : (() => Int, Int => Unit) = getAndSet (2)
      val r1 : Int = get1 ()
      val r2 : Int = get1 ()
      set1 (10)
      val r3 : Int = get1 ()
      val r4 : Int = get1 ()
      val r5 : Int = get2 ()
      val r6 : Int = get2 ()
      set2 (20)
      val r7 : Int = get2 ()
      val r8 : Int = get1 ()
      (   r1, r2, r3, r4, r5, r6, r7, r8)
    } === (1,  1, 10, 10,  2,  2, 20, 10)
    )
  }

  property ("EX06 - observeCounter", EX (6)) {
    val rand = scala.util.Random
    var total = rand.nextInt (100)
    val res = observeCounter {
      case c:Counter =>
        for i <- 1 to total do
          if rand.nextBoolean() then
            c.increment()
          else
            c.decrement()
    }
    assert (res === total)
  }

  property ("EX07 - observeCounterList", EX (7)) {
    val rand = scala.util.Random
    var totals : List[Int] = (1 to 3).toList.map (_ => rand.nextInt (100))
    val res : List[Int] = observeCounterList {
      case cs:List[Counter] =>
        if cs.length != 3 then 
          throw RuntimeException ("Length of list must be exactly 3")
        for (total, c) <- totals.zip (cs) do 
          for i <- 1 to total do 
            if rand.nextBoolean() then
              c.increment()
            else
              c.decrement()
    }
    assert (res === totals)
  }

  property ("EX08 - observeCounterArray", EX (8)) {
    val rand = scala.util.Random
    var totals : List[Int] = (1 to 3).toList.map (_ => rand.nextInt (100))
    val res : Array[Int] = observeCounterArray {
      case cs:Array[Counter] =>
        if cs.length != 3 then
          throw RuntimeException ("Length of list must be exactly 3")
        for (total, c) <- totals.zip (cs) do 
          for i <- 1 to total do 
            if rand.nextBoolean() then 
              c.increment()
            else
              c.decrement()
    }
    assert (res.toList === totals)
  }

