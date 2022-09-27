package petclinic.services

import petclinic.model._
import zio._
import zio.macros._

import java.time.LocalDate

@accessible
trait PetService {

  def create(name: String, birthdate: LocalDate, species: Species, ownerId: OwnerId): Task[Pet]

  def delete(id: PetId): Task[Unit]

  def get(id: PetId): Task[Option[Pet]]

  def getForOwner(ownerId: OwnerId): Task[List[Pet]]

  def getAll: Task[List[Pet]]

  def update(
      id: PetId,
      name: Option[String],
      birthdate: Option[LocalDate],
      species: Option[Species],
      ownerId: Option[OwnerId]
  ): Task[Unit]

}