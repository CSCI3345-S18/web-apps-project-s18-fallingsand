package actors

import play.api.libs.json._
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import WSActor._
import models._

/**
 * "WSManager" = "Web Socket Manager"
 * manages active web sockets connected to the server
 */
class WSManager extends Actor {
  import WSManager._
  private var clients = List[WSActorRef]()
  private var canvas = Canvas.createCanvas("Trinity")
  
  def receive = {
    case RemoveActor(actorToRemove) =>
      removeClient(actorToRemove)
    case NewActor(newActor) =>
      addClient(newActor)
    case AddParticleAction(partAct) =>
      addParticleAction(partAct)
    case BroadcastMessage(msg) =>
      println("Broadcasting message \"" + msg + "\" to all clients...")
      // TODO: add particles to canvas, etc
      clients.foreach(c => c.tell(WSActor.MessageOut(msg), self))
  }
  
  def addParticleAction(partActMsg: AddParticleActionMessage) = {
    println("WSManager is adding the particle action with class " + partActMsg.particleAction.getClass)
    canvas.addParticleAction(partActMsg.particleAction)
    clients.foreach(c => c.tell(partActMsg, self))
  }
  
  def addClient(clientRef: WSActorRef) = {
    println("WSManager is adding the WSActor with id: " + clientRef.id)
    clients ::= clientRef
    canvas.addMember(clientRef.user)
    clients.foreach(c => {
        var msgBroadcast = new AddMemberMessage(clientRef.user)
        val msgAddExistingMember = new AddMemberMessage(c.user)
        val msgSyncCanvas = new SyncCanvasMessage(this.canvas)
        c.tell(msgBroadcast.getJsValue, self)
        clientRef.tell(msgAddExistingMember.getJsValue, self)
        clientRef.tell(msgSyncCanvas.getJsValue, self)
      })
  }
  
  def removeClient(clientRef: WSActorRef) = {
    println("WSManager is removing the WSActor with id: " + clientRef.id)
    clients = clients.filterNot(x => x.ref == clientRef.ref)
    val msg = new RemoveMemberMessage(clientRef.id)
    clients.foreach(c => c.tell(msg.getJsValue, self))
    canvas.removeMember(clientRef.user.id)
  }

}

object WSManager {
  private var nextClientID = 0
  
  def getNextClientID = {
    var id = nextClientID
    nextClientID += 1
    id
  }
  
  def props = Props[WSManager]
  
  case class RemoveActor(clientActor: WSActorRef)
  case class NewActor(clientActor: WSActorRef)
  case class AddParticleAction(partActMsg: AddParticleActionMessage)
  case class BroadcastMessage(msg: JsValue)
}