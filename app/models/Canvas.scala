package models

import java.time.Instant

/**
 * Holds the data for a canvas, including all particles and the IDs of contributing users
 */
class Canvas private (_id: Int, _tags: String) {
  val id: Int = _id
  val timeCreatedMillis: Long = Instant.now().toEpochMilli()
  
  private var contributingUserIds = List[Int]()
  private var particleActions = List[ParticleAction]()
  private var tags: String = _tags
  
  def addContributor(user: User) = {
    contributingUserIds ::= (user.id)
  }
  
  def addParticleAction(partAct: ParticleAction) = {
    particleActions ::= partAct
  }
  
  def addTags(_tags: String) = {
    var buffer = if (tags.endsWith(" ") || _tags.startsWith(" ")) "" else " "
    tags = tags + buffer + _tags
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
    var nC = new Canvas(getNextId, tags)
    canvases ::= nC
    return nC
  }
}