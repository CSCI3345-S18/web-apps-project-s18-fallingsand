package models

/**
 * represents a user entity in the particle simulator
 * TODO: guarantee these invariants:
 * TODO: - one client per PSUser and vice versa
 * TODO: - no two PSUser entities can have the same id
 * TODO: - no two PSUser entities can have the same name
 */
case class User(val id: Int, val name: String, val password: String)

object User {
  def getRandomName(length: Int): String = {
    val r = new scala.util.Random
    val sb = new StringBuilder
    for (i <- 1 to length) {
      sb.append(r.nextPrintableChar)
    }
    sb.toString
  }
}