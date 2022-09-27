package petclinic.services

import petclinic.QuillContext
import petclinic.model.{Vet, VetId}
import zio._

import javax.sql.DataSource

final case class VetServiceLive(dataSource: DataSource) extends VetService {

  import QuillContext._

  override def get(vetId: VetId): Task[Option[Vet]] =
    run(query[Vet].filter(_.id == lift(vetId)))
      .provideEnvironment(ZEnvironment(dataSource))
      .map(_.headOption)

  override def getAll: Task[List[Vet]] =
    run(query[Vet])
      .provideEnvironment(ZEnvironment(dataSource))

}

object VetServiceLive {
  val layer: URLayer[DataSource, VetService] =
    ZLayer.fromFunction(VetServiceLive.apply _)
}