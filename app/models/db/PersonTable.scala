package models.db

import models.Person
import slick.jdbc.MySQLProfile.api._

class PersonTable(tag: Tag) extends Table[Person](tag, "persons") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def age = column[Int]("age")
  def designation = column[String]("designation")
  def active = column[Boolean]("active", O.Default(true))

  def * = (id.?, name, age, designation, active) <> ((Person.apply _).tupled, Person.unapply)
}
