package models

import java.time.Instant
import play.api.libs.json._

abstract class AbstractParticleAction {
  val id: Int
  val xPos: Int
  val yPos: Int
  val timeAddedMillis: Long
  def getJsValue: JsValue
}

/**
 * Equivalent javascript object definition:
 * var NullParticleAction = null;
 */
class NullParticleAction() extends AbstractParticleAction {
  val id: Int = -1
  val xPos: Int = -100
  val yPos: Int = -100
  val timeAddedMillis = -1
  def getJsValue: JsValue = {
    JsNull
  }
}

/**
 * Equivalent javascript object definition:
 * var SandParticleAction = {
 * 	"particleType": 0,
 *  "xPos": [some x-coordinate as an integer],
 *  "yPos": [some y-coordinate as an integer]
 * };
 * 
 * NOTE: "timeAddedMillis" should not be calculated by javascript.
 * Leave out "timeAddedMillis" for objects created by the client,
 * and only use "timeAddedMillis" values from messages sent by the server.
 */
class SandParticleAction(_xPos: Int, _yPos: Int, _timeAddedMillis: Long = Instant.now().toEpochMilli()) extends AbstractParticleAction {
  val id: Int = 0
  val xPos: Int = _xPos
  val yPos: Int = _yPos
  val timeAddedMillis: Long = _timeAddedMillis
  def getJsValue: JsValue = {
    JsObject(Seq(
      "particleType" -> JsNumber(this.id),
      "timeAddedMillis" -> JsNumber(this.timeAddedMillis),
      "xPos" -> JsNumber(xPos),
      "yPos" -> JsNumber(yPos)
    ))
  }
}

/**
 * Equivalent javascript object definition:
 * var RockParticleAction = {
 * 	"particleType": 1,
 *  "xPos": [some x-coordinate as an integer],
 *  "yPos": [some y-coordinate as an integer]
 * };
 * 
 * NOTE: "timeAddedMillis" should not be calculated by javascript.
 * Leave out "timeAddedMillis" for objects created by the client,
 * and only use "timeAddedMillis" values from messages sent by the server.
 */
class RockParticleAction(_xPos: Int, _yPos: Int, _timeAddedMillis: Long = Instant.now().toEpochMilli()) extends AbstractParticleAction {
  val id: Int = 1
  val xPos: Int = _xPos
  val yPos: Int = _yPos
  val timeAddedMillis: Long = _timeAddedMillis
  def getJsValue: JsValue = {
    JsObject(Seq(
      "particleType" -> JsNumber(this.id),
      "timeAddedMillis" -> JsNumber(this.timeAddedMillis),
      "xPos" -> JsNumber(xPos),
      "yPos" -> JsNumber(yPos)
    ))
  }
}

/**
 * Equivalent javascript object definition:
 * var WaterParticleAction = {
 * 	"particleType": 2,
 *  "xPos": [some x-coordinate as an integer],
 *  "yPos": [some y-coordinate as an integer]
 * };
 * 
 * NOTE: "timeAddedMillis" should not be calculated by javascript.
 * Leave out "timeAddedMillis" for objects created by the client,
 * and only use "timeAddedMillis" values from messages sent by the server.
 */
class WaterParticleAction(_xPos: Int, _yPos: Int, _timeAddedMillis: Long = Instant.now().toEpochMilli()) extends AbstractParticleAction {
  val id: Int = 2
  val xPos: Int = _xPos
  val yPos: Int = _yPos
  val timeAddedMillis: Long = _timeAddedMillis
  def getJsValue: JsValue = {
    JsObject(Seq(
      "particleType" -> JsNumber(this.id),
      "timeAddedMillis" -> JsNumber(this.timeAddedMillis),
      "xPos" -> JsNumber(xPos),
      "yPos" -> JsNumber(yPos)
    ))
  }
}

object ParticleAction {
  def getParticleActionOptFromJson(json: JsValue): Option[AbstractParticleAction] = {
    if(json.eq(JsNull)) None
    else {
      val particleTypeOpt = (json \ "particleType").asOpt[JsNumber]
      val particleTimeAddedMillisOpt = (json \ "timeAddedMillis").asOpt[JsNumber]
      val xPosOpt = (json \ "xPos").asOpt[JsNumber]
      val yPosOpt = (json \ "yPos").asOpt[JsNumber]
      if(particleTypeOpt.isEmpty || xPosOpt.isEmpty || yPosOpt.isEmpty) {
        None
      }
      else {
        val particleType = particleTypeOpt.get.value.intValue()
        val xPos = xPosOpt.get.value.intValue()
        val yPos = yPosOpt.get.value.intValue()
        particleType match {
          case 0 => {
            if(particleTimeAddedMillisOpt.isEmpty) {
              Some(new SandParticleAction(xPos,yPos))
            }
            else {
              val particleTimeAddedMillis = particleTimeAddedMillisOpt.get.value.longValue()
              Some(new SandParticleAction(xPos,yPos,particleTimeAddedMillis))
            }
          }
          case 1 => {
            if(particleTimeAddedMillisOpt.isEmpty) {
              Some(new RockParticleAction(xPos,yPos))
            }
            else {
              val particleTimeAddedMillis = particleTimeAddedMillisOpt.get.value.longValue()
              Some(new RockParticleAction(xPos,yPos,particleTimeAddedMillis))
            }
          }
          case 2 => {
            if(particleTimeAddedMillisOpt.isEmpty) {
              Some(new WaterParticleAction(xPos,yPos))
            }
            else {
              val particleTimeAddedMillis = particleTimeAddedMillisOpt.get.value.longValue()
              Some(new WaterParticleAction(xPos,yPos,particleTimeAddedMillis))
            }
          }
        }
      }
    }
  }
}