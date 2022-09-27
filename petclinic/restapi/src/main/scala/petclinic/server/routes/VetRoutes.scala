package petclinic.server.routes

import petclinic.server.utils.Parse{parseVetId}
import petclinic.services.VetService
import zhttp.http._
import zio._
import zio.json.EncoderOps

final case class VetRoutes(service: VetService) {

  val routes: Http[Any, Throwable, Request, Response] = Http.collectZIO[Request] {

    case Method.GET -> !! / "vets" =>
      service.getAll.map(vets => Response.json(vets.toJson))

    case Method.GET -> !! / "vets" / id =>
      for {
        vetId <- parseVetId(id)
        vet   <- service.get(vetId)
      } yield Response.json(vet.toJson)

  }

}

object VetRoutes {
  val layer: URLayer[VetService, VetRoutes] = 
    ZLayer.fromFunction(VetRoutes.apply _)
}