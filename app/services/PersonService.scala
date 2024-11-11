package services

import models.Person
import repositories.PersonRepository
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class PersonService @Inject() (personRepository: PersonRepository) {
  def list(): Future[Seq[Person]] = personRepository.list()

  def get(id: Long): Future[Option[Person]] = personRepository.get(id)

  def create(person: Person): Future[Long] = personRepository.create(person)

  def update(id: Long, person: Person): Future[Option[Person]] =
    personRepository.update(id, person)

  def delete(id: Long): Future[Boolean] = personRepository.delete(id)
}
