package actors

import play.api.libs.json._
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import models._

/**
 * "WSActor" = "Web Socket Actor"
 * represents a client's connection to the server via a web socket
 */
class WSActor(out: ActorRef, manager: ActorRef) extends Actor {
  import WSActor._
  
  private var id: Int = -1 //  temporary value (set in preStart())
  private var user: UserInfo = null //  temporary value (set in preStart())
  
  override def preStart() {
    this.id = WSManager.getNextClientID
    this.user = new UserInfo(this.id, (this.id + UserUtils.getRandomName(5)))
    println("A new client has connected to a web socket. WSActor ID: " + this.id)
    // tell manager that this actor has been connected
    this.manager.tell(WSManager.NewActor(this), self)
    // send initialization message to client
    val message = new InitSelfMessage(this.user)
    out.tell(message.getJsValue, self)
  }
  
  def receive = {
    case json: JsValue =>
      /* If WSActor gets a JSValue message,
       * we know it must have originated from
       * the client, because the WSManager only sends
       * "MessageOut" messages to the WSActors.
       */
      var msgOpt = MessageUtil.parseJsonOpt(json)
      if(msgOpt.isEmpty) {
        println("WSActor with id: " + this.id + " recieved an invalid json message.")
        // do nothing
        // no "return" needed here because of Scala magic
      }
      else {
        val msg = msgOpt.get
        msg match {
          case m:AddParticleActionMessage => {
            println("WSActor with id: " + this.id + " recieved a particle action message.")
            // let manager know about new particle action
            this.manager.tell(WSManager.AddParticleAction(m), self)
          }
          case m:NullMessage => {
            // sent by client just to keep connection open
            println("WSActor with id: " + this.id + " recieved a null message.")
            // do nothing (for now... maybe something later?)
          }
          case _ => {
            // message was of a type that should not
            // be sent by the client to the WSActor.
            println("WSActor with id: " + this.id + " recieved an unexpected json message of class " + msg.getClass + ".")
            // do nothing
          }
        }
      }
    case MessageOut(msg) =>
      println("WSActor with id: " + this.id + " was told to send a message to its client.")
      out.tell(msg, self)
    case m =>
      println("WSActor with id: " + this.id + " recieved an unknown message.")
  }
  
  override def postStop() {
    println("WSActor ID " + id + " is disconnecting...")
    manager.tell(WSManager.RemoveActor(this), self)
  }
}

object WSActor {
  def props(out: ActorRef, manager: ActorRef) = Props(new WSActor(out, manager))
  
  // Any message that the actor should send back to the client is stored in a MessageOut.
  // The WSManager sends MessageOut instances to the WSActors it manages.
  case class MessageOut(msg: JsValue)
  
  class WSActorRef(_ref: ActorRef, _id: Int, _user: UserInfo) {
    val ref: ActorRef = _ref
    val id: Int = _id
    val user: UserInfo = _user
    
    def tell(msg: Any, sender: ActorRef) = {
      ref.tell(msg, sender)
    }
  }
  
  implicit def getWSActorRef(actor: WSActor): WSActorRef = {
    new WSActorRef(actor.self, actor.id, actor.user)
  }
}