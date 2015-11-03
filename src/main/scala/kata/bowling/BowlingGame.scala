package main.scala.kata.bowling

class BowlingGame {
  val noOfOfficialFrames = 10
  val maximumNoOfFrames = 12
  val frames = Array.fill[ScoreFrame](maximumNoOfFrames)(new ScoreFrame())
  var pendingStrikeAndSpareFrames = List[ScoreFrame]()
  var currentFrameIndex = 0

  def rollBallsWithPins(pins: Array[Int]): Unit = {
    pins.foreach(b => roll(new Ball(b)))
  }

  def roll(ball: Ball): Unit = {
    val currentFrame = getCurrentFrame()

    currentFrame.record(ball)

    adjustScoreForStrikeAndSpareFrames(ball, currentFrame)

    if(currentFrame.finishAllBalls()) {
      increaseCurrentFrame()
    }
  }

  def score(): Int = {
    frames.take(noOfOfficialFrames).map(_.score).sum
  }

  def scoreForFrame(frameNo: Int): Int = {
    if(frameNo < 1 || frameNo > maximumNoOfFrames + 1) {
      throw new Exception("Invalid frame number: " + frameNo)
    }

    frames(frameNo - 1).score()
  }

  override def toString(): String = {
    frames.map(_.score()).mkString(", ")
  }

  private def adjustScoreForStrikeAndSpareFrames(ball: Ball, currentFrame: ScoreFrame): Unit = {
    pendingStrikeAndSpareFrames.foreach(_.adjustScore(ball))
    pendingStrikeAndSpareFrames = pendingStrikeAndSpareFrames.filter(!_.scoreAdjusted())

    if(ball.isStrike() || currentFrame.hasSpare()) {
      pendingStrikeAndSpareFrames = pendingStrikeAndSpareFrames.::(currentFrame)
    }
  }

  private def increaseCurrentFrame(): Unit = {
    currentFrameIndex = currentFrameIndex + 1
  }

  private def getCurrentFrame(): ScoreFrame = {
    frames(currentFrameIndex)
  }
}
