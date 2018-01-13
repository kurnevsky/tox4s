name := "tox4s"

organization := "net.kurnevsky"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "org.scodec" %% "scodec-core" % "1.10.3",
  "co.fs2" %% "fs2-core" % "0.10.0-RC1",
  "co.fs2" %% "fs2-io" % "0.10.0-RC1",
  "com.github.emstlk" %% "nacl4s" % "1.1.0-SNAPSHOT"
)
