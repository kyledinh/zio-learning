package petclinic.model 

import zio.json._ 

final case class Vet(
    id: VetId,
    lastName: String,
    specialty: String
)

object Vet {
    implecit val codec: JsonCodec[Vet] =
        DeriveJsonCode.gen[Vet]
}