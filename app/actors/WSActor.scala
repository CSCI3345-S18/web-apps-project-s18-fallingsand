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
      /* TODO : parse json
       * should not be receiving any add/remove member
       * or "syncCanvas" messages from the websockets.
       * Incoming messages should all be "addParticles"
       */
      var msgOpt = MessageUtil.parseJsonOpt(json)
      if(msgOpt.isEmpty) {
        // do nothing
      }
      else {
        val msg = msgOpt.get
        if(msg.isInstanceOf[AddMemberMessage]) {
          // TODO
        }
        else if(msg.isInstanceOf[RemoveMemberMessage]) {
          // TODO
        }
        else if(msg.isInstanceOf[AddParticleActionMessage]) {
          // TODO
          this.manager.tell(msg,self)
        }
        else if(msg.isInstanceOf[SyncCanvasMessage]) {
          // TODO
        }
        else if(msg.isInstanceOf[InitSelfMessage]) {
          // TODO
        }
        else if(msg.isInstanceOf[NullMessage]) {
          // TODO
        }
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