package services

import models.{Reservation, Room}
import repositories.{ReservationRepository, RoomRepository}
import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ReservationService @Inject()(reservationRepository: ReservationRepository, roomRepository: RoomRepository)(implicit ec: ExecutionContext) {

  // Check if a room is available for the requested time range
  def isRoomAvailable(roomId: Int, startTime: String, endTime: String): Future[Boolean] = {
    reservationRepository.findAvailableRooms(startTime, endTime).map { availableRooms =>
      // Use `filter` and `nonEmpty` to check if the roomId matches any available room
      availableRooms.filter(room => room.id == roomId).nonEmpty
    }
  }

  // Create a new reservation with availability check
  def reserveRoom(reservation: Reservation): Future[Option[Reservation]] = {
    // First, check if the room is available
    isRoomAvailable(reservation.roomId, reservation.startTime, reservation.endTime).flatMap { available =>
      if (available) {
        reservationRepository.createReservation(reservation).map { reservationId =>
          Some(reservation.copy(id = Some(reservationId)))
        }
      } else {
        Future.successful(None) // Return None if the room is not available
      }
    }
  }

  // Create a new reservation without availability check
  def createReservation(reservation: Reservation): Future[Reservation] = {
    reservationRepository.createReservation(reservation).map { reservationId =>
      reservation.copy(id = Some(reservationId)) // Return reservation with the generated ID
    }
  }

  def checkRoomAvailability(roomId: Int, startTime: String, endTime: String): Future[Boolean] = {
    reservationRepository.checkRoomAvailability(roomId, startTime, endTime)
  }

}
