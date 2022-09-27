package petclinic.services

import petclinic.QuillContext
import petclinic.model.{OwnerId, Pet, PetId, Species}
import zio._
import zio.metrics._

import javax.sql.DataSource

final case class PetServiceLive(dataSource: DataSource) extends PetService {

  // QuillContext needs to be imported here to expose the methods in the QuillContext object.
  import QuillContext._

  implicit val encodeSpecies: MappedEncoding[Species, String] = MappedEncoding[Species, String](_.toString)
  implicit val decodeSpecies: MappedEncoding[String, Species] = MappedEncoding[String, Species](Species.fromString)

    for {
      pet <- Pet.make(name, birthdate, species, ownerId)
      _   <- run(query[Pet].insertValue(lift(pet))).provideEnvironment(ZEnvironment(dataSource))
      _   <- Metric.counter("pet.created").increment
    } yield pet

  override def delete(id: PetId): Task[Unit] =
    run(query[Pet].filter(_.id == lift(id)).delete)
      .provideEnvironment(ZEnvironment(dataSource))
      .unit

  override def get(id: PetId): Task[Option[Pet]] =
    run(query[Pet].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(dataSource))
      .map(_.headOption)

  override def getForOwner(ownerId: OwnerId): Task[List[Pet]] =
    run(query[Pet].filter(_.ownerId == lift(ownerId)).sortBy(_.birthdate))
      .provideEnvironment(ZEnvironment(dataSource))

  override def getAll: Task[List[Pet]] =
    run(query[Pet].sortBy(_.birthdate))
      .provideEnvironment(ZEnvironment(dataSource))

  override def update(
      id: PetId,
      name: Option[String],
      birthdate: Option[java.time.LocalDate],
      species: Option[Species],
      ownerId: Option[OwnerId]
  ): Task[Unit] =
    run(
      dynamicQuery[Pet]
        .filter(_.id == lift(id))
        .update(
          setOpt(_.name, name),
          setOpt(_.birthdate, birthdate),
          setOpt(_.species, species),
          setOpt(_.ownerId, ownerId)
        )
    )
      .provideEnvironment(ZEnvironment(dataSource))
      .unit

}

object PetServiceLive {
  val layer: URLayer[DataSource, PetService] = 
    ZLayer.fromFunction(PetServiceLive.apply _)
}