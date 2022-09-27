package petclinic 

import javax.sql.DataSource
import org.flywaydb.core.Flyway 
import zio._

final case class Migration(dataSource: DataSource)  {

    val migrate: Task[Unit] =
        for {
            flyway <- loadFlyway 
            _ <- ZIO.attempt(flyway.migrate())
        } yield ()

    val reset: Task[Unit] =
        for {
            _ <- ZIO.debug("RESETTING DATABASE")
            flyway <- loadFlyway 
            _ <- ZIO.attempt(flyway.clean())
            _ <- ZIO.attempt(flyway.migrate())
        } yield ()


    private lazy val loadFlyway: Task[Flyway] =
        ZIO.attempt {
            Flyway 
                .configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true)
                .baselinVersion("0")
                .load()
        }
}