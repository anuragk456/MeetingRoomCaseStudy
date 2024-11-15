# Meeting Room Management System

The **Meeting Room Management System** is a comprehensive solution designed to streamline the process of reserving, preparing, and managing meeting rooms in an office environment. This project integrates multiple modules, including room reservation, automated notifications, and room preparation, using a scalable and event-driven architecture.

---

## Features

- **Meeting Room Reservation**:
  - Allows users to book meeting rooms based on availability.
  - Prevents overlapping reservations with conflict management.
- **Room Preparation Notifications**:
  - Notifies the RoomService team to prepare rooms before meetings.
  - Automates reminders for upcoming meetings.
- **Role-Based Access**:
  - Differentiates functionality for Admin, RoomService, and User roles.
- **Database Management**:
  - Well-structured schema for rooms, reservations, and notification logs.
- **Event-Driven Design**:
  - Uses Kafka for real-time communication between modules.

---

## Repository Structure

- **Meeting_Room_Reservation**:
  - Handles room reservations and availability checks.
- **Automated_Notification**:
  - Manages notifications for reminders and room readiness.
- **Database Schema**:
  - Includes `Room` and `Reservation` tables for efficient data management.

---

## Tools and Technologies Used

1. **Programming Language**: Scala
2. **Frameworks**: Play Framework, Akka
3. **Database**: MySQL
4. **Message Broker**: Kafka
5. **Containerization**: Docker
6. **API Testing**: Postman

---

## How to Set Up and Run

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/anuragk456/MeetingRoomCaseStudy.git
   cd MeetingRoomCaseStudy
