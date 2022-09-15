scalaVersion := "3.1.3"
organization := "org.example"
version := "0.1.0"
name := "functional-programming"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.2",
  "dev.zio" %% "zio-streams" % "2.0.2",
  "dev.zio" %% "zio-json" % "0.3.0-RC11"
) ++ Seq(
  "dev.zio" %% "zio-test" % "2.0.2" % Test,
  "dev.zio" %% "zio-test-sbt" % "2.0.2" % Test,
  "dev.zio" %% "zio-test-magnolia" % "2.0.2" % Test
)

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
