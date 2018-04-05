package models

import play.api.libs.json._

/**
 * abstract class that the various message formats are built from
 */
abstract class Message {
  val id: Int
  
  
}
object Message {
  object Types {
    val removeMember: Int = 0 // for removing a member when they disconnect
    val addMember: Int = 1 // for adding members
    val addParticles: Int = 2 // when other members have added particles to the canvas
    val syncCanvas: Int = 3 // for periodically synchronizing all members' canvases
    val initSelf: Int = 4 // for setting this client's id upon first connection
    val nullMsg: Int = 5 // for keeping port open, nothing important in these messages
  }
  
  def getTypeOfMessage(json: JsValue): Int = {
    var opt = json("type").asOpt[JsNumber]
    if(!(opt.isEmpty)) {
      return opt.get.value.intValue()
    }
    else {
      // message was invalid, return null type
      return Types.nullMsg
    }
  }
  
  def getRemoveMemberMessage(memberIdToRemove: Int) : JsValue = {
    JsObject(Seq(
      "type" -> JsNumber(Types.removeMember),
      "body" -> JsObject(Seq(
          "memberId" -> JsNumber(memberIdToRemove)
       ))
    ))
  }
  
  def getAddMemberMessage(memberToAdd: User) : JsValue = {
    JsObject(Seq(
      "type" -> JsNumber(Types.addMember),
      "body" -> JsObject(Seq(
          "memberId" -> JsNumber(memberToAdd.id),
          "memberName" -> JsString(memberToAdd.name)
       ))
    ))
  }
  
  def getInitSelfMessage(selfUser: User) : JsValue = {
    JsObject(Seq(
      "type" -> JsNumber(Types.initSelf),
      "body" -> JsObject(Seq(
          "selfId" -> JsNumber(selfUser.id),
          "selfName" -> JsString(selfUser.name)
       ))
    ))
  }
  
  /* TODO
  def getAddParticlesMessage(newParticles: PSParticleAction) : JsValue = {
    JsObject(Seq(
      "type" -> JsNumber(Types.addParticles),
      "body" -> JsObject(Seq(
          "targetMemberId" -> JsNumber(memberIdToRemove)
       ))
    ))
  }
  
  def getSyncCanvasMessage(canvasState: PSCanvas) : JsValue = {
    JsObject(Seq(
      "type" -> JsNumber(Types.syncCanvas),
      "body" -> JsObject(Seq(
          "targetMemberId" -> JsNumber(memberIdToRemove)
       ))
    ))
  }
  */
  
  def getNullMessage() : JsValue = {
    JsObject(Seq(
      "type" -> JsNumber(Types.nullMsg)
    ))
  }
}