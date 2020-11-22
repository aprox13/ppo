package ru.ifkbhit.ppo5.darwing

import ru.ifkbhit.ppo5.darwing.RGBColor.{DeepPurple, Grey}

trait DrawingApi {

  /**
   * Нарисовать круг
   *
   * @param center центр круга
   * @param radius радиус круга
   */
  def drawCircle(center: Point, radius: Float, color: RGBColor = DeepPurple): Unit
  def drawSegment(p1: Point, p2: Point, color: RGBColor = Grey): Unit
  def drawText(text: String, point: Point): Unit
  def drawArrow(p1: Point, p2: Point, color: RGBColor = Grey): Unit

  def getCanvasWidth: Int
  def getCanvasHeight: Int

  def getCenterPoint: Point

  def repaint(): Unit
}
