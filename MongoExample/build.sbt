ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.0"

lazy val root = (project in file("."))
  .settings(
    name := "MongoExample"
  )

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.8.1"
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.5"






