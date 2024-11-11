package models

import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Person(
    id: Option[Long] = None,   // id will be auto-incremented
    name: String,
    age: Int,
    designation: String,
    active: Boolean = true
)

object Person {
  // Read for the Person fields
  private val idReads: Reads[Option[Long]] = (JsPath \ "id").readNullable[Long]
  private val nameReads: Reads[String] = (JsPath \ "name").read[String]
  private val ageReads: Reads[Int] = (JsPath \ "age").read[Int]
  private val designationReads: Reads[String] = (JsPath \ "designation").read[String]
  private val activeReads: Reads[Boolean] = (JsPath \ "active")
    .readNullable[Boolean]
    .map(_.getOrElse(true)) // Default to true if missing

  // Combine all the reads
  implicit val personReads: Reads[Person] = (
    idReads and
    nameReads and
    ageReads and
    designationReads and
    activeReads
  )(Person.apply _)

  // Use Json.writes to generate Writes automatically
  implicit val personWrites: Writes[Person] = Json.writes[Person]

  // Combine Reads and Writes into Format
  implicit val personFormat: Format[Person] = Format(personReads, personWrites)
}
