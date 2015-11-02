package test.scala.kata.bowling

import main.scala.kata.bowling.BowlingGame
import org.scalatest.{FunSuite, BeforeAndAfter}


class BowlingGameTests extends FunSuite with BeforeAndAfter {

  var game: BowlingGame = _

  before {
    game = new BowlingGame
  }

  test("score is 0 when starting game") {
    assert(game.score() == 0)
  }

  test("score for one ball is number of pins knocked down by that ball") {
    val pins = 5;

    game.rollBallsWithPins(Array(pins))

    assert(game.score() == pins)
  }

  test("score for two balls is sum of number of pins knocked down by those two balls") {
    val pins = Array(3, 5)

    game.rollBallsWithPins(pins)

    assert(game.score() == pins.sum)
  }

  test("spare is awarded with next ball score") {
    val pins = Array(5, 5, 3, 4)

    game.rollBallsWithPins(pins)

    assert(game.scoreForFrame(1) == pins(0) + pins(1) + pins(2))
    assert(game.scoreForFrame(2) == pins(2) + pins(3))
  }

  test("strike is awarded with next 2 balls score") {
    val pins = Array(10, 3, 4, 5)

    game.rollBallsWithPins(pins)

    assert(game.scoreForFrame(1) == pins(0) + pins(1) + pins(2))
    assert(game.scoreForFrame(2) == pins(1) + pins(2))
  }

  test("double strikes are awarded with next 2 balls score for each strike") {
    val pins = Array(10, 10, 9, 0)

    game.rollBallsWithPins(pins)

    assert(game.scoreForFrame(1) == pins(0) + pins(1) + pins(2))
    assert(game.scoreForFrame(2) == pins(1) + pins(2) + pins(3))
    assert(game.scoreForFrame(3) == pins(2) + pins(3))
  }

  test("triple strikes are awarded with next 2 balls score for each strike") {
    val pins = Array(10, 10, 10, 0, 9)

    game.rollBallsWithPins(pins)

    assert(game.scoreForFrame(1) == pins(0) + pins(1) + pins(2))
    assert(game.scoreForFrame(2) == pins(1) + pins(2) + pins(3))
    assert(game.scoreForFrame(3) == pins(2) + pins(3) + pins(4))
    assert(game.scoreForFrame(4) == pins(3) + pins(4))
  }

  test("perfect game has score of 300") {
    game.rollBallsWithPins(Array.fill[Int](12)(10))

    assert(game.score() == 300)
  }
}