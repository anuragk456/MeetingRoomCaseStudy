package controllers

import models.Person
import play.api.mvc._
import services.PersonService
import play.api.libs.json._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PersonController @Inject()(
    val cc: ControllerComponents,
    personService: PersonService
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def list(): Action[AnyContent] = Action.async {
    personService.list().map(persons => Ok(Json.toJson(persons)))
  }

  def get(id: Long): Action[AnyContent] = Action.async {
    personService.get(id).map {
      case Some(person) => Ok(Json.toJson(person))
      case None => NotFound(Json.obj("message" -> s"Person with id $id not found"))
    }
  }

  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Person] match {
      case JsSuccess(person, _) =>
        personService.create(person).map(created =>
          Created(Json.toJson(created)))
      case JsError(errors) =>
        Future.successful(BadRequest(Json.obj(
          "message" -> "Invalid person data",
          "errors" -> JsError.toJson(errors))))
    }
  }

  def update(id: Long): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Person] match {
      case JsSuccess(person, _) =>
        personService.update(id, person).map {
          case Some(updated) => Ok(Json.toJson(updated))
          case None => NotFound(Json.obj("message" -> s"Person with id $id not found"))
        }
      case JsError(errors) =>
        Future.successful(BadRequest(Json.obj(
          "message" -> "Invalid person data",
          "errors" -> JsError.toJson(errors))))
    }
  }

  def delete(id: Long): Action[AnyContent] = Action.async {
    personService.delete(id).map {
      case true => NoContent
      case false => NotFound(Json.obj("message" -> s"Person with id $id not found"))
    }
  }
}