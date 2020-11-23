package ru.ifkbhit.ppo5.darwing

import ru.ifkbhit.ppo5.DrawingContext

trait BaseDrawingApi extends DrawingApi {

  override def getCanvasWidth: Int = DrawingContext.DrawingScreenSize.width

  override def getCanvasHeight: Int = DrawingContext.DrawingScreenSize.height
}
