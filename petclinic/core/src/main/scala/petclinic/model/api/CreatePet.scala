package petclinic.model.api 

import petclinic.models.{OwnerId, Species}
import zio.json._

final case class CreateOwner(
    name: String,
    birthdate: java.time.LocalDate,
    species: Species,
    ownerId: OwnerId
)

object CreateOwner {
    implicit val codec: JsonCodec[CreatePet] = DeriveJsonCode.gen[CreatePet] 
}