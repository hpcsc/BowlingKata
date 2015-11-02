package main.scala.kata.bowling

class ScoreFrame {
  var balls = List[Ball]()
  var adjustedScores = List[Int]()

  def record(ball: Ball): Unit = {
    if(finishAllBalls) {
      throw new MaximumFrameBallsExceededException
    }

    balls = balls.::(ball)
  }

  def score(): Int = {
    calculateOriginalScore() + calculateAdjustedScore()
  }

  def hasSpare(): Boolean = {
    balls.length == 2 && calculateOriginalScore() == 10
  }

  def finishAllBalls(): Boolean = {
    balls.length == 2 || firstBallIsStrike
  }

  def scoreAdjusted(): Boolean = {
    val strike = hasStrike()

    (!hasSpare() && !strike) ||
    (hasSpare() && adjustedScores.length == 1) ||
      (strike && adjustedScores.length == 2)
  }

  def adjustScore(ball: Ball): Unit = {
    val strike = hasStrike()

    if((hasSpare() && adjustedScores.length == 1) ||
      (strike && adjustedScores.length == 2) ||
      (!hasSpare() && !strike)) {
      throw new ScoreAlreadyAdjustedException()
    }

    adjustedScores = adjustedScores.::(ball.pins)
  }

  private def hasStrike(): Boolean = {
    balls.exists(b => b.isStrike())
  }

  private def calculateOriginalScore(): Int = {
    balls.map(f => f.pins).sum
  }

  private def calculateAdjustedScore(): Int = {
    adjustedScores.sum
  }

  private def firstBallIsStrike(): Boolean = {
    balls.length == 1 && balls(0).isStrike()
  }
}
