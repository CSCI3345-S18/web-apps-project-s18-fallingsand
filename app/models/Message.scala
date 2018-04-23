package models

import play.api.libs.json._

/**
 * abstract class that the various message formats are built from
 */
abstract class AbstractMessage {
  def companion: MessageCompanion
  def getJsValue: JsValue
  def getId(): Int = companion.id
}

trait MessageCompanion {
  val id: Int
}

class RemoveMemberMessage(val memberId: Int) extends AbstractMessage {
  def companion = RemoveMemberMessage
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(RemoveMemberMessage.id),
      "body" -> JsObject(Seq(
          "memberId" -> JsNumber(memberId)
       ))
    ))
  }
}

object RemoveMemberMessage extends MessageCompanion {
  val id: Int = 0
}

class AddMemberMessage(val member: UserInfo) extends AbstractMessage {
  def companion = AddMemberMessage
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(AddMemberMessage.id),
      "body" -> JsObject(Seq(
          "memberId" -> JsNumber(member.id),
          "memberName" -> JsString(member.username)
       ))
    ))
  }
}

object AddMemberMessage extends MessageCompanion {
  val id: Int = 1
}

class AddParticleActionMessage(val particleAction: AbstractParticleAction) extends AbstractMessage {
  def companion = AddParticleActionMessage
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(AddParticleActionMessage.id),
      "body" -> particleAction.getJsValue
    ))
  }
}

object AddParticleActionMessage extends MessageCompanion {
  val id: Int = 2
}

class SyncCanvasMessage(val canvas: Canvas) extends AbstractMessage {
  def companion = SyncCanvasMessage
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(SyncCanvasMessage.id),
      "body" -> canvas.getJsValue
    ))
  }
}

object SyncCanvasMessage extends MessageCompanion {
  val id: Int = 3
}

class InitSelfMessage(val selfUser: UserInfo) extends AbstractMessage {
  def companion = InitSelfMessage
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(InitSelfMessage.id),
      "body" -> JsObject(Seq(
          "selfId" -> JsNumber(selfUser.id),
          "selfUsername" -> JsString(selfUser.username)
      ))
    ))
  }
}

object InitSelfMessage extends MessageCompanion {
  val id: Int = 4
}

class NullMessage extends AbstractMessage {
  def companion = NullMessage
  def getJsValue: JsValue = {
    JsObject(Seq(
      "messageType" -> JsNumber(NullMessage.id)
    ))
  }
}

object NullMessage extends MessageCompanion {
  val id: Int = 5
}

object MessageUtil {
  def parseJsonOpt(json: JsValue): Option[AbstractMessage] = {
    val msgTypeOpt = (json \ "messageType").asOpt[JsNumber]
    val msgBodyOpt = (json \ "body").asOpt[JsObject]
    if(msgTypeOpt.isEmpty) {
      None
    } 
    else {
      val msgType = msgTypeOpt.get
      if(msgBodyOpt.isEmpty) {
        if(msgType.value.intValue() == NullMessage.id) {
          Some(new NullMessage())
        }
        else {
          None
        }
      }
      else {
        val msgBody = msgBodyOpt.get
        msgType.value.intValue() match {
          case RemoveMemberMessage.id => {
          	  val memberIdOpt = (msgBody \ "memberId").asOpt[JsNumber]
          		if(memberIdOpt.isEmpty) {
          		  None
          		}
          		else {
          		  Some(new RemoveMemberMessage(memberIdOpt.get.value.intValue()))
          		}
          }
          case AddMemberMessage.id => {
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
          case AddParticleActionMessage.id => {
        	  val particleActionOpt = ParticleAction.getParticleActionOptFromJson(msgBody)
        	  if(particleActionOpt.isEmpty) {
        	    None
        	  }
        	  else {
        	    val particleAction = particleActionOpt.get
        		  Some(new AddParticleActionMessage(particleAction))
        	  }
          }
          case SyncCanvasMessage.id => {
            val canvasOpt = Canvas.getCanvasOptFromJson(msgBody)
            if(canvasOpt.isEmpty) {
              None
            }
            else {
        	    Some(new SyncCanvasMessage(canvasOpt.get))
            }
          }
          case InitSelfMessage.id => {
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
          case NullMessage.id => {
        	  Some(new NullMessage())
          }
          case _ => None
        }
      }
    }
  }
}