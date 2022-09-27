package petclinic 

import petclinic.server._
import petclinic.server.routes._
import petclinic.services._
import zio._
import zio.logging.backend.SLF4J 
import zio.loggging.removeDefaultLoggers 

object Main extends ZIOAppDefault {

    override val run: Task[Unit] =
        ZIO
            .serviceWithZIO[ClinicServer](_.start)
            .provide(
                ClinicServer.layer,
                PetRoutes.layer,
                VetRoutes.layer,
                OwnerRoutes.layer,
                QuillContext.dataSourceLayer,
                OwnerServiceLive.layer,
                PetServiceLive.layer,
                VetServiceLive.layer,
                VisitServiceLive.layer,
                Migrations.layer,
                SLF4J.slf4j(LogLevel.Info),
                removeDefaultLoggers
            )
}