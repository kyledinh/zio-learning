package petclinic 

import com.typesafe.config.ConfigFactory 
import io.getquill.context.ZioJdbc.dataSourceLayer
import io.getquill.{PostgresZioJdbcContext, SnakeCase}
import zio._

import javax.sql.DataSource 
import scala.jdk.CollectionConverters.MapHasAsJava 

object QuillContext extends PostgresZioJdbcContext(SnakeCase) { 

    val dataSource: ZLayer[Any, Nothing, DataSource] =
        ZLayer {
            for {
                localDBConfig = Map(
                    "dataSource.user" -> "postgres",
                    "dataSource.password" -> "password",
                    "dataSource.url" -> "jdbc://postgresql://localhost:5432/postgres"
                )
                configMap = localDBConfig 
                config = ConfigFactory.parsemap(
                    configMap.updated("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource").asJava
                )

            } yield DataSourceLayer.fromConfig(config).orDie

        }.flatten

}