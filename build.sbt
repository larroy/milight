lazy val commonSettings = Seq(
  version := "0.1",
  organization := "com.larroy.milight",
  name := "milight",
  scalaVersion := "2.11.8",
  scalacOptions := Seq(
    "-target:jvm-1.8",
    "-unchecked",
    "-deprecation",
    "-feature",
    "-encoding", "utf8",
    "-Xlint"
  ),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots"),
  )
)

lazy val commonDependencies = Seq(
  "org.slf4j" % "jcl-over-slf4j" % "1.7.+",
  "commons-logging" % "commons-logging" % "1.2",
  "ch.qos.logback" % "logback-classic" % "1.1.+"
)

lazy val testDependencies = Seq(
  "org.specs2" %% "specs2" % "3.+" % "test"
)

lazy val root = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies)
  .settings(libraryDependencies ++= testDependencies)
