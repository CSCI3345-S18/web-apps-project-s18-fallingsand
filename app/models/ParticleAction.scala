package models

import java.time.Instant

class ParticleAction (_id: Int, _canvas: Canvas, _type: Int, _xPos: Int, _yPos: Int) {
  import ParticleAction._
  val id: Int = _id
  val canvas: Canvas = _canvas
  val particleType: Int = _type
  val xPos: Int = _xPos
  val yPos: Int = _yPos
  val timeAddedMillis: Long = Instant.now().toEpochMilli() - canvas.timeCreatedMillis
  
  canvas.addParticleAction(this)
}

object ParticleAction {
  object Types {
    val sand: Int = 0
  }
}