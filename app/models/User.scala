package models

import slick.jdbc.MySQLProfile.api._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import java.time.OffsetDateTime

/**
 * represents a user entity in the particle simulator
 * TODO: guarantee these invariants:
 * TODO: - one client per User and vice versa
 * TODO: - no two User entities can have the same id
 * TODO: - no two User entities can have the same username
 * TODO: STORE PASSWORDS AS HASHES AND SALTS IN DB
 */
case class User(id: Int, username: String, password: String)
/**
 * represents a new user entity which has not yet been added to the database,
 * and therefore has no id.
 */
case class NewUser(username: String, password: String)
/**
 * represents a user's id and username without their password
 * (for websocket messages)
 */
case class UserInfo(id: Int, username: String)

object UserQueries {
  import Tables._

  def findById(id: Int, db: Database)(implicit ec: ExecutionContext):Future[Option[User]] = {
    db.run {
      users.filter(_.id === id).result.headOption
    }
  }
  
  def findByUsername(username: String, db: Database)(implicit ec: ExecutionContext):Future[Option[User]] = {
    db.run {
      users.filter(_.username === username).result.headOption
    }
  }
  
  def findByCredentials(username: String, password: String, db: Database)(implicit ec: ExecutionContext):Future[Option[User]] = {
    db.run {
      users.filter(_.username === username).filter(_.password === password).result.headOption
    }
  }
  
  def addUser(nu: NewUser, db: Database)(implicit ec: ExecutionContext): Future[Option[User]] = {
    val existing = findByCredentials(nu.username,nu.password,db)
    existing.transformWith{userTry =>
      if(userTry.get.isDefined) {
        Future(None)
      }
      else {
        db.run{
          (users returning users.map(_.id) into ((user,id) => Some(user.copy(id=id)))) += User(-1, nu.username, nu.password)
        }
      }
    }
  }
  
  def removeUser(id: Int, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
    val q = users.filter(_.id === id)
    val action = q.delete
    db.run(action)
  }
}

object UserUtils {
  def getRandomName(length: Int): String = {
    val r = new scala.util.Random
    val sb = new StringBuilder
    for (i <- 1 to length) {
      sb.append(r.nextPrintableChar)
    }
    sb.toString
  }
  
  /**
   * used to read user information from session
   */
  def getUserFromString(str: String): User = {
    val splitCommas = str.split(',')
    val userId = splitCommas(0).toInt
    val username = splitCommas(1)
    // once we implement hashes and salts, this will be the hash of the user's password
    // for now, we don't care about security
    val password = splitCommas(2)
    new User(userId, username, password)
  }
  
  /**
   * used to store user information in session
   */
  def getStringFromUser(user: User): String = {
    user.id.toString() ++ "," ++ user.username ++ "," ++ user.password
  }
  
  def toUserInfo(user: User): UserInfo = {
    new UserInfo(user.id, user.username)
  }
}