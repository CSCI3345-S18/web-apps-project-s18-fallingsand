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
  
  var loggedInUser: Option[User] = None
  
  def index = Action { implicit request =>
    val userConnected = request.session.get("connected")
    if(userConnected.isEmpty) {
      Redirect(routes.LoginController.index)
    }
    else {
      loggedInUser = Some(UserUtils.getUserFromString(userConnected.get))
      Ok(views.html.particlesim("Welcome " + loggedInUser.get.username))
    }
  }
  
  def test = Action { implicit request =>
    Ok(views.html.particlesim("bypassing login for testing"))
  }
  
}
