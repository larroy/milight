lazy val commonSettings = Seq(
  version := "0.2-SNAPSHOT",
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
    Resolver.sonatypeRepo("snapshots")
  ),

  // Sonatype publishing
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  //autoScalaLibrary := false,
  //autoScalaLibrary in test := false,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  pomExtra := (
    <url>https://github.com/larroy/milight</url>
    <licenses>
      <license>
        <name>Apache</name>
        <url>https://www.apache.org/licenses/LICENSE-2.0</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>https://github.com/larroy/milight</url>
      <connection>scm:git@github.com:larroy/milight.git</connection>
    </scm>
    <developers>
      <developer>
        <id>larroy</id>
        <name>Pedro Larroy</name>
        <url>https://github.com/larroy</url>
      </developer>
    </developers>
  )

)

lazy val commonDependencies = Seq(
  "org.slf4j" % "jcl-over-slf4j" % "1.7.+",
  "ch.qos.logback" % "logback-classic" % "1.1.+"
)

lazy val testDependencies = Seq(
  "org.specs2" %% "specs2" % "3.+" % "test"
)

lazy val root = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies)
  .settings(libraryDependencies ++= testDependencies)
