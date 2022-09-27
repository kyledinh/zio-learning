package petclinic.services

import petclinic.model.{Vet, VetId}
import zio._

trait VetService {

  def get(vetId: VetId): Task[Option[Vet]]

  def getAll: Task[List[Vet]]

}