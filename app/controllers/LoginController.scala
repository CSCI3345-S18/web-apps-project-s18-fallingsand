package controllers
import javax.inject._
import play.api.mvc._
import play.api.Logger

import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import slick.jdbc.JdbcProfile
import slick.jdbc.JdbcCapabilities
import slick.jdbc.MySQLProfile.api._
import models._

import scala.concurrent.ExecutionContext
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import scala.concurrent.Future

import models._

@Singleton
class LoginController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, mcc: MessagesControllerComponents) (implicit ec: ExecutionContext, assetsFinder: AssetsFinder)
  extends MessagesAbstractController(mcc) with HasDatabaseConfigProvider[JdbcProfile] {

  def getLoginForm = {
    Form(mapping(
          "username" -> nonEmptyText,
          "password" -> nonEmptyText,
    )(NewUser.apply)(NewUser.unapply))
  }
  
  def index = Action { implicit request =>
    Ok(views.html.login(this.getLoginForm))
  }
  
  def logout = Action { implicit request =>
    Ok(views.html.login(this.getLoginForm)).withNewSession
  }

  def login = Action.async { implicit request =>
    (this.getLoginForm).bindFromRequest().fold(
      formWithErrors => {
        Future(BadRequest(views.html.login(formWithErrors)))
      },
      loginForm => {
        val loggedInUser = UserQueries.findByCredentials(loginForm.username, loginForm.password, db)
        loggedInUser.map{user =>
          if(user.isDefined) {
            Redirect(routes.PSController.index).withSession("connected" -> UserUtils.getStringFromUser(user.get))
          }
          else {
            BadRequest(views.html.login((this.getLoginForm).withGlobalError("Username or password was incorrect")))
          }
        }
      })
  }
  
  def register = Action.async { implicit request =>
    (this.getLoginForm).bindFromRequest().fold(
      formWithErrors => {
        Logger.debug("Registration form had errors...")
        Future(BadRequest(views.html.login(formWithErrors)))
      },
      registerForm => {
        Logger.debug("Before registering new user.")
        val registerUser = UserQueries.addUser(registerForm, db)
        registerUser.map{newUser => 
          if(newUser.isDefined) {
            Logger.debug("New user was created... redirecting")
            Redirect(routes.PSController.index).withSession("connected" -> UserUtils.getStringFromUser(newUser.get))
          }
          else {
            Logger.debug("User already exists")
            BadRequest(views.html.login((this.getLoginForm).withGlobalError("Try a different username")))
          }
        }
      })
  }
}