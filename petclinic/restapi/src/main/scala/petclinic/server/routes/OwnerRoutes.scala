package petclinic.server.routes

import petclinic.models.api.{CreateOwner, UpdateOwner}
import petclinic.server.utils.Parse.{parseBody, parseOwnerId}
import petclinic.services.OwnerService
import zhttp.http._
import zio._
import zio.json._

final case class OwnerRoutes(service: OwnerService) {

  val routes: Http[Any, Throwable, Request, Response] = Http.collectZIO[Request] {

    case Method.GET -> !! / "owners" =>
      service.getAll.map(owners => Response.json(owners.toJson))

    case Method.GET -> !! / "owners" / id =>
      for {
        id    <- parseOwnerId(id)
        owner <- service.get(id)
      } yield Response.json(owner.toJson)

    case req @ Method.POST -> !! / "owners" =>
      for {
        createOwner <- parseBody[CreateOwner](req)
        owner <-
          service.create(
            createOwner.firstName,
            createOwner.lastName,
            createOwner.address,
            createOwner.phone,
            createOwner.email
          )
      } yield Response.json(owner.toJson)

    case req @ Method.PATCH -> !! / "owners" / id =>
      for {
        ownerId     <- parseOwnerId(id)
        updateOwner <- parseBody[UpdateOwner](req)
        _ <- service.update(
               ownerId,
               updateOwner.firstName,
               updateOwner.lastName,
               updateOwner.address,
               updateOwner.phone,
               updateOwner.email
             )
      } yield Response.ok

    case Method.DELETE -> !! / "owners" / id =>
      for {
        id <- parseOwnerId(id)
        _  <- service.delete(id)
      } yield Response.ok

  }

}

object OwnerRoutes {
  val layer: URLayer[OwnerService, OwnerRoutes] = 
    ZLayer.fromFunction(OwnerRoutes.apply _)
}
