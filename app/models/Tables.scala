package models

object Tables extends {
  val profile = slick.jdbc.MySQLProfile
  import profile.api._
  
  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def password = column[String]("password")
    def * = (id, username, password) <> (User.tupled, User.unapply)
  }
  val users = TableQuery[Users]
}