name := """PRTravelServer"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1103-jdbc4"

libraryDependencies += "org.json" % "org.json" % "chargebee-1.0"

libraryDependencies += filters

fork in run := true

fork in run := true

fork in run := true

fork in run := true

fork in run := true