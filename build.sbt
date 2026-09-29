// Like settings in a pom.xml, these describe the project to the build tool.
name := "scala-play-lab"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.13.18"

// Same ScalaTest version already present in the separate sbtlearn project.
// %% selects scalatest_2.13; Test keeps this dependency in the test configuration.
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test
