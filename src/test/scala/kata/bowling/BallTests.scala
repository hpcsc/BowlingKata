package test.scala.kata.bowling

import main.scala.kata.bowling.{Ball, InvalidPinsException}
import org.scalatest.{BeforeAndAfter, FunSuite}

class BallTests  extends FunSuite with BeforeAndAfter {

  test("ball throws exception when number of pins is negative") {
    intercept[InvalidPinsException] {
      val ball = new Ball(-1)
    }
  }

  test("ball throws exception when number of pins exceeds 10") {
    intercept[InvalidPinsException] {
      val ball = new Ball(12)
    }
  }

  test("ball with 10 pins is strike") {
    val ball = new Ball(10)

    assert(ball.isStrike())
  }
}
