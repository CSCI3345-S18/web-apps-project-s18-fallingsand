package models

import play.api.libs.json._

/**
 * abstract class that the various message formats are built from
 */
abstract class AbstractMessage {
  val id: Int
  def getJsValue: JsValue
}

class RemoveMemberMessage(val memberId: Int) extends AbstractMessage {
  val id: Int = 0
  
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(this.id),
      "body" -> JsObject(Seq(
          "memberId" -> JsNumber(memberId)
       ))
    ))
  }
}

class AddMemberMessage(val member: UserInfo) extends AbstractMessage {
  val id: Int = 1
  
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(this.id),
      "body" -> JsObject(Seq(
          "memberId" -> JsNumber(member.id),
          "memberName" -> JsString(member.username)
       ))
    ))
  }
}

class AddParticleActionMessage(val particleAction: AbstractParticleAction) extends AbstractMessage {
  val id: Int = 2
  
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(this.id),
      "body" -> particleAction.getJsValue
    ))
  }
}

class SyncCanvasMessage(val canvas: Canvas) extends AbstractMessage {
  val id: Int = 3
  
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(this.id),
      "body" -> canvas.getJsValue
    ))
  }
}

class InitSelfMessage(val selfUser: UserInfo) extends AbstractMessage {
  val id: Int = 4
  
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(this.id),
      "body" -> JsObject(Seq(
          "selfId" -> JsNumber(selfUser.id),
          "selfUsername" -> JsString(selfUser.username)
      ))
    ))
  }
}

class NullMessage extends AbstractMessage {
  val id: Int = 5
  
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(this.id)
    ))
  }
}

object MessageUtil {
  def parseJsonOpt(json: JsValue): Option[AbstractMessage] = {
    val msgTypeOpt = (json \ "messageType").asOpt[JsNumber]
    val msgBodyOpt = (json \ "body").asOpt[JsObject]
    if(msgTypeOpt.isEmpty) {
      None
    } 
    else {
      val msgBody = msgBodyOpt.get
      val msgType = msgTypeOpt.get
      msgType.value.intValue() match {
        case 0 => {
        	  val memberIdOpt = (msgBody \ "memberId").asOpt[JsNumber]
        		if(memberIdOpt.isEmpty) {
        		  None
        		}
        		else {
        		  Some(new RemoveMemberMessage(memberIdOpt.get.value.intValue()))
        		}
        }
        case 1 => {
          val memberIdOpt = (msgBody \ "memberId").asOpt[JsNumber]
        	val memberNameOpt = (msgBody \ "memberName").asOpt[JsString]
        	if(memberIdOpt.isEmpty || memberNameOpt.isEmpty) {
        		None
        	}
        	else {
        		val user = new UserInfo(memberIdOpt.get.value.intValue(),memberNameOpt.get.value)
        		Some(new AddMemberMessage(user))
      		}
        }
        case 2 => {
      	  val particleActionOpt = ParticleAction.getParticleActionOptFromJson(msgBody)
      	  if(particleActionOpt.isEmpty) {
      	    None
      	  }
      	  else {
      	    val particleAction = particleActionOpt.get
      		  Some(new AddParticleActionMessage(particleAction))
      	  }
        }
        case 3 => {
          val canvasOpt = Canvas.getCanvasOptFromJson(msgBody)
          if(canvasOpt.isEmpty) {
            None
          }
          else {
      	    Some(new SyncCanvasMessage(canvasOpt.get))
          }
        }
        case 4 => {
          val selfIdOpt = (msgBody \ "selfId").asOpt[JsNumber]
          val selfUsernameOpt = (msgBody \ "selfUsername").asOpt[JsString]
          if(selfIdOpt.isEmpty || selfUsernameOpt.isEmpty) {
            None
          }
          else {
            val selfUserInfo = new UserInfo(selfIdOpt.get.value.intValue(),selfUsernameOpt.get.value)
      	    Some(new InitSelfMessage(selfUserInfo))
          }
        }
        case 5 => {
      	  Some(new NullMessage())
        }
        case _ => None
      }
    }
  }
}