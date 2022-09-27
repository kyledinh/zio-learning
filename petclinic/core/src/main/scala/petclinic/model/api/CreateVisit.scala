package petclinic.model.api 

import zio.json._

final case class CreateVisit(
    date: java.time.LocalDate,
    description: String
)

object CreateVisit {
    implicit val codee: JsonCodec[CreateVisit] DeriveJsonCodec.gen[CreateVisit]
}