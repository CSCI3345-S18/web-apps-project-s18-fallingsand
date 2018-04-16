package models

import java.time.Instant
import play.api.libs.json._
import scala.collection.mutable._

/**
 * Holds the data for a canvas, including all particles and the objects representing contributing users
 */
class Canvas private (
    _id: Int, 
    _timeCreatedMillis: Option[Long] = Some(Instant.now().toEpochMilli()), 
    _contributingUsers: Option[List[UserInfo]] = Some(List[UserInfo]()), 
    _particleActions: Option[List[models.AbstractParticleAction]] = Some(List[models.AbstractParticleAction]()),
    _tags: String = "") {
  val id: Int = _id
  val timeCreatedMillis: Long = if(_timeCreatedMillis.isDefined) _timeCreatedMillis.get else Instant.now().toEpochMilli()
  
  private var contributingUsers = if(_contributingUsers.isDefined) _contributingUsers.get else List[UserInfo]()
  private var particleActions = if(_particleActions.isDefined) _particleActions.get else List[models.AbstractParticleAction]()
  private var tags: String = _tags
  
  def addContributor(user: UserInfo) = {
    contributingUsers ::= user
  }
  
  def addParticleAction(partAct: models.AbstractParticleAction) = {
    particleActions ::= partAct
  }
  
  def addTags(newTags: String) = {
    var buffer = if (tags.endsWith(" ") || newTags.startsWith(" ")) "" else " "
    tags = tags + buffer + newTags
  }
  
  def getJsValue: JsValue = {
    val userJson = ArrayBuffer[(String, JsValue)]()
    for(user <- contributingUsers) {
      userJson.append(user.id.toString() -> JsString(user.username))
    }
    val partActs = ArrayBuffer[(String, JsValue)]()
    for(i <- 0 to particleActions.length) {
      partActs.append(i.toString() -> particleActions(i).getJsValue)
    }
    JsObject(Seq(
      "canvasId" -> JsNumber(this.id),
      "timeCreatedMillis" -> JsNumber(this.timeCreatedMillis),
      "contributingUsers" -> JsObject(userJson.toSeq),
      "particleActions" -> JsObject(partActs.toSeq),
      "tags" -> JsString(tags)
    ))
  }
}

object Canvas {
  private var nextCanvasId: Int = 0
  private var canvases = List[Canvas]()
  
  def getNextId: Int = {
    var ret = nextCanvasId
    nextCanvasId += 1
    return ret
  }
  
  def createCanvas(tags: String): Canvas = {
    var nC = new Canvas(getNextId, _tags = tags)
    canvases ::= nC
    return nC
  }
  
  def getCanvasOptFromJson(json: JsValue): Option[Canvas] = {
    val canvasIdOpt = (json \ "canvasId").asOpt[JsNumber]
    val timeCreatedMillisOpt = (json \ "timeCreatedMillis").asOpt[JsNumber]
    val contributingUsersOpt = (json \ "contributingUsers").asOpt[JsObject]
    val particleActionsOpt = (json \ "particleActions").asOpt[JsObject]
    val tagsOpt = (json \ "tags").asOpt[JsString]
    if(canvasIdOpt.isEmpty) {
      None
    }
    else {
      val canvasId = canvasIdOpt.get.value.intValue()
      val timeCreatedMillis = if(timeCreatedMillisOpt.isDefined) Some(timeCreatedMillisOpt.get.value.longValue()) else None
      val contributingUsers = if(contributingUsersOpt.isDefined) Some(contributingUsersOpt.get.value.map(pair => new UserInfo(pair._1.toInt,pair._2.as[JsString].value)).toList) else None
      val particleActions = if(particleActionsOpt.isDefined) Some(particleActionsOpt.get.value.map(pair => ParticleAction.getParticleActionOptFromJson(pair._2).getOrElse(new NullParticleAction())).toList) else None
      val tags = if(tagsOpt.isDefined) tagsOpt.get.value else ""
      Some(new Canvas(canvasId, timeCreatedMillis, contributingUsers, particleActions, tags))
    }
  }
}