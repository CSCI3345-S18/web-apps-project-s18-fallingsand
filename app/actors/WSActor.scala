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
  private var user: User = null //  temporary value (set in preStart())
  
  override def preStart() {
    this.id = WSManager.getNextClientID
    this.user = new User(this.id, (this.id + User.getRandomName(5)), this.id.toString())
    println("A new client has connected to a web socket. WSActor ID: " + this.id)
    // tell manager that this actor has been connected
    this.manager.tell(WSManager.NewActor(this), self)
    // send initialization message to client
    out.tell(Message.getInitSelfMessage(this.user), self)
  }
  
  def receive = {
    case json: JsValue =>
      /* TODO : parse json
       * should not be receiving any add/remove member
       * or "syncCanvas" messages from the websockets.
       * Incoming messages should all be "addParticles"
       */
      var msgType = Message.getTypeOfMessage(json)
      if(msgType == Message.Types.addParticles) {
        this.manager.tell(WSManager.BroadcastMessage(json), self)
      }
      else {
        // do nothing otherwise
      }
    case MessageOut(msg) =>
      out.tell(msg, self)
    case m =>
      println("Unknown message: " + m)
  }
  
  override def postStop() {
    println("WSActor ID " + id + " is disconnecting...")
    manager.tell(WSManager.RemoveActor(this), self)
  }
}

object WSActor {
  def props(out: ActorRef, manager: ActorRef) = Props(new WSActor(out, manager))
  
  case class MessageOut(msg: JsValue)
  
  class WSActorRef(_ref: ActorRef, _id: Int, _user: User) {
    val ref: ActorRef = _ref
    val id: Int = _id
    val user: User = _user
    
    def tell(msg: Any, sender: ActorRef) = {
      ref.tell(msg, sender)
    }
  }
  
  implicit def getWSActorRef(actor: WSActor): WSActorRef = {
    new WSActorRef(actor.self, actor.id, actor.user)
  }
}