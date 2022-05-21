import scala.language.postfixOps

name := """e-products"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies += ws
libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"
libraryDependencies += "org.mockito" %% "mockito-scala-scalatest" % "1.16.13" % Test
libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.2.12",
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.3"
)

libraryDependencies ++= Seq(
  evolutions
)

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
).map(_ % circeVersion)
libraryDependencies += "com.dripower" %% "play-circe" % "2814.1"


(Compile / sourceGenerators) += slick.taskValue // Automatic code generation on build
lazy val slick = taskKey[Seq[File]]("Generate Tables.scala")
slick := {
  val dir = (Compile / sourceManaged) value
  val outputDir = dir / "slick"
  val url = "jdbc:postgresql://localhost:5432/e_products"
  val jdbcDriver = "org.postgresql.Driver"
  val slickDriver = "slick.jdbc.PostgresProfile"
  val pkg = "demo"
  val user = "test"
  val password = "0000"

  val cp = (Compile / dependencyClasspath) value
  val s = streams value

  runner.value.run("slick.codegen.SourceCodeGenerator",
    cp.files,
    Array(slickDriver, jdbcDriver, url, outputDir.getPath, pkg, user, password),
    s.log).failed foreach (sys error _.getMessage)

  val file = outputDir / pkg / "Tables.scala"

  Seq(file)
}

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
