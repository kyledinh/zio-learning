scalaVersion := "3.1.3"
organization := "com.kyledinh"
name         := "restful-server"
version      := "0.0.1"

libraryDependencies ++= Seq(
  "dev.zio"       %% "zio"            % "2.0.1",
  "dev.zio"       %% "zio-json"       % "0.3.0-RC11",
  "io.d11"        %% "zhttp"          % "2.0.0-RC10",
  "io.getquill"   %% "quill-zio"      % "4.3.0",
  "io.getquill"   %% "quill-jdbc-zio" % "4.3.0",
  "com.h2database" % "h2"             % "2.1.214"
)

ThisBuild / assemblyMergeStrategy := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList("io", "getquill", xs @ _*)           => MergeStrategy.first
  case PathList("io", "netty", xs @ _*)              => MergeStrategy.first
  // https://stackoverflow.com/questions/54625572/sbt-assembly-errordeduplicate-different-file-contents-found-in-io-netty-versio
  case x if x.contains("io.netty.versions.properties") => MergeStrategy.discard
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
    oldStrategy(x)
}
