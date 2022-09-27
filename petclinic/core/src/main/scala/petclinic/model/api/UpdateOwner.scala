package petclinic.model.api 

import zio.json._ 

final case class UpdateOwner(
    firsttName: Option[String],
    lastName: Option[String],
    address: Option[String],
    phone: Option[String],
    email: Option[String]
)

object UpdateOwner {
    implicit val codec: JsonCodec[UpdateOwner] = DeriveJsonCodec.gen[UpdateOwner] //
}