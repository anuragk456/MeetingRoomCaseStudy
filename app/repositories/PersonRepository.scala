package repositories

import models.Person
import models.db.PersonTable
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class PersonRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext){
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val persons = TableQuery[PersonTable]

  def list(): Future[Seq[Person]] = db.run(persons.filter(_.active === true).result)

  def get(id: Long): Future[Option[Person]] = db.run(persons.filter(person => person.id === id && person.active === true).result.headOption)

  // Retrieve generated ID as part of Person object insertion (Useful in auto-generated id cases)
  def create(person: Person): Future[Long] = {
    val insertQueryThenReturnId = persons returning persons.map(_.id)
    val insertQueryThenReturnPerson = persons returning persons.map(_.id) into ((person, id) => person.copy(id = Some(id)))

    db.run(insertQueryThenReturnId += person)
  }

  /*
  // Straightforward insert when you don’t need the auto-generated ID afterward.
  def create(person: Person): Future[Int] = db.run(persons += person)
  // Return Value: Returns a Future[Int], where the integer represents the number of rows affected
                   (should be 1 for a successful insertion).
   */

  def update(id: Long, person: Person): Future[Option[Person]] = {
    val updateQuery = persons.filter(person => person.id === id && person.active === true)
      .map(ele => (ele.name, ele.age, ele.designation))
      .update((person.name, person.age, person.designation))

    // flatMap removes Some
    db.run(updateQuery).flatMap {
      case 0 => Future.successful(None)  // Implies, not updated
      case _ => get(id) // Updated, hence get the `Person` details using `get(id)`
    }
  }

  def delete(id: Long): Future[Boolean] = {
    db.run(persons.filter(person => person.id === id && person.active === true)
      .map(ele => ele.active).update(false)).map(_ > 0)
  }

}
