package petclinic.model

import zio.UIO
import zio.json._

final case class Visit(
    id: VisitId,
    petId: PetId,
    date: java.time.LocalDate,
    description: String,
    vetId: VetId
)

object Visit {

    def make(
        petId: PetId,
        date: java.time.LocalDate,
        description: String,
        vetId: VetId
    ): UIO[Visit] =
        VisitId.random.map(id => Visit(id, petId, date, description, vetId))

    implicit val codec: JsonCodec[Visit] = 
        DeriveJsonCodec.gen[Visit]

}