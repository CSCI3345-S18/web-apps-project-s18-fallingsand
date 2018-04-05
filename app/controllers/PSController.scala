package controllers

import play.api.mvc._
import javax.inject._
import models.Canvas
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models._

/**
 * "PSController" = "Particle Simulator Controller"
 * handles particle simulator requests
 */
@Singleton
class PSController @Inject() (cc:ControllerComponents) (implicit assetsFinder: AssetsFinder) extends AbstractController(cc) {
  case class UserData(name: String, password: String)
  
  def validateUserData(name: String, password: String) = {
    if(name == "" || password == "") {
      None
    }
    var specName = (name.distinct).filter(x =>
          x == '{'
       || x == '}'
       || x == '['
       || x == ']'
       || x == '"'
       || x == '>'
       || x == '<'
       || x == '\\'
    )
    var specPW = (password.distinct).filter(x =>
          x == '{'
       || x == '}'
       || x == '['
       || x == ']'
       || x == '"'
       || x == '>'
       || x == '<'
       || x == '\\'
    )
    (specName,specPW) match {
      case ("","") =>
        Some(UserData(name,password))
      case _ =>
        None
    }
  }
  
  val userForm = Form(
      mapping(
          "name" -> nonEmptyText,
          "password" -> nonEmptyText,
      )(UserData.apply)(UserData.unapply) verifying("Failed form constraints", fields => fields match {
        case userData => validateUserData(userData.name, userData.password).isDefined
      })
   )
  
  def index = Action { implicit request =>
  	request.session.get("connnected").map { user =>
  	  Ok(views.html.particlesim("Welcome " + user))
  	}.getOrElse {
  	  Ok(views.html.login(userForm))
  	}
  }
  
  def login = Action { implicit request => 
    userForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(views.html.login(formWithErrors))
        },
        user => {
          // add
        }
    )
  }
  
  def register = Action { implicit request =>
    
  }
  
}
