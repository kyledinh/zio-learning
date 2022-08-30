scalaVersion := "3.1.3"
organization := "com.kyledinh"
name         := "restful-server"
version      := "0.0.1"

libraryDependencies ++= Seq(
  "dev.zio"       %% "zio"            % "2.0.1",
  "dev.zio"       %% "zio-json"       % "0.3.0-RC11",
  "io.d11"        %% "zhttp"          % "2.0.0-RC10"
  // "io.getquill"   %% "quill-zio"      % "4.3.0",
  // "io.getquill"   %% "quill-jdbc-zio" % "4.3.0",
  // "com.h2database" % "h2"             % "2.1.214"
)

ThisBuild / assemblyMergeStrategy := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
    oldStrategy(x)
}
