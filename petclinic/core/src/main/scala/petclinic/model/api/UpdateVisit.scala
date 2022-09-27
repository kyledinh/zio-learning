package petclinic.model.api

import petclinic.model.PetId 
import zio.json._

final case class UpdateVisit(
    date: Option[java.time.LocalDate],
    description: Option[String],
    petId: PetId
)


object UpdateVisit {
    implicit val codec: JsonCodec[UpdateVisit] = DeriveJsonCodec.gen[UpdateVisit]
}
