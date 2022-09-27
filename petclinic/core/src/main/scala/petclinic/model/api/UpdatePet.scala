package petclinic.model.api 

import petclinic.model.{OwnerId, Species}

import zio.json._ 

final case class UpdatePet(
    name: Option[String],
    birthdate: Option[java.time.LocalDate],
    species: Option[String],
    ownerId: Option[OwnerId]
)

object UpdatePet {
    implicit val codec: JsonCodec[UpdatePet] = DeriveJsonCodec.gen[UpdatePet]
}