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
  
  def receive = {
    case RemoveActor(actorToRemove) =>
      removeClient(actorToRemove)
      val msg = new RemoveMemberMessage(actorToRemove.id)
      clients.foreach(c => c.tell(msg.getJsValue, self))
    case NewActor(newActor) =>
      println("New client connected")
      clients.foreach(c => {
        var msgBroadcast = new AddMemberMessage(newActor.user)
        val msgReply = new AddMemberMessage(c.user)
        c.tell(msgBroadcast.getJsValue, self)
        newActor.tell(msgReply.getJsValue, self)
      })
      clients ::= newActor
    case BroadcastMessage(msg) =>
      println("Broadcasting message \"" + msg + "\" to all clients...")
      // TODO: add particles to canvas, etc
      clients.foreach(c => c.tell(WSActor.MessageOut(msg), self))
  }
  
  def removeClient(clientRef: WSActorRef) = {
    println("Removing client from websocket pool...");
    clients = clients.filterNot(x => x.ref == clientRef.ref)
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
  case class BroadcastMessage(msg: JsValue)
}