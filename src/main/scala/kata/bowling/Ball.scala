package main.scala.kata.bowling

class Ball(val pins: Int) {

  if(pins < 0 || pins > 10) {
    throw new InvalidPinsException("Invalid pins " + pins)
  }

  def isStrike(): Boolean = {
    pins == 10
  }
}
