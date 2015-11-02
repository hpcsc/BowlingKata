package test.scala.kata.bowling

import main.scala.kata.bowling.{MaximumFrameBallsExceededException, ScoreAlreadyAdjustedException, ScoreFrame, Ball}
import org.scalatest.{FunSuite, BeforeAndAfter}

class ScoreFrameTests extends FunSuite with BeforeAndAfter  {

  var frame: ScoreFrame = _

  before {
    frame = new ScoreFrame
  }

  test("frame score is 0 when created") {
    assert(frame.score() == 0)
  }

  test("frame score for one ball is number of pins knocked down by that ball") {
    val pins = 5

    frame.record(new Ball(pins))

    assert(frame.score() == pins)
  }

  test("frame score for two balls is sum of number of pins knocked down by those balls") {
    val pins = Array(3, 5)

    pins.foreach(b => frame.record(new Ball(b)))

    assert(frame.score() == pins.sum)
  }

  test("score frame can only record maximum of two balls if no strike on the first ball") {
    val pins = Array(2, 3, 4)

    intercept[MaximumFrameBallsExceededException] {
      pins.foreach(b => frame.record(new Ball(b)))
    }
  }

  test("frame stops scoring when first ball is strike") {
    frame.record(new Ball(10));

    assert(frame.finishAllBalls)
    intercept[MaximumFrameBallsExceededException] {
      frame.record(new Ball(5))
    }
  }

  test("frame with spare can adjust score once only") {
    frame.record(new Ball(5))
    frame.record(new Ball(5))

    frame.adjustScore(new Ball(3))

    intercept[ScoreAlreadyAdjustedException] {
      frame.adjustScore(new Ball(4))
    }
  }

  test("frame with strike on the first ball can adjust score twice only") {
    frame.record(new Ball(10))

    frame.adjustScore(new Ball(3))
    frame.adjustScore(new Ball(4))

    intercept[ScoreAlreadyAdjustedException] {
      frame.adjustScore(new Ball(5))
    }
  }

  test("frame with strike on the second ball can adjust score twice only") {
    frame.record(new Ball(2))
    frame.record(new Ball(10))

    frame.adjustScore(new Ball(3))
    frame.adjustScore(new Ball(4))

    intercept[ScoreAlreadyAdjustedException] {
      frame.adjustScore(new Ball(5))
    }
  }

  test("frame with neither spare nor strike cannot be adjusted") {
    frame.record(new Ball(3))
    frame.record(new Ball(4))

    intercept[ScoreAlreadyAdjustedException] {
      frame.adjustScore(new Ball(5))
    }
  }
}
