name := "networkframes"

ThisBuild / organization := "com.dongjinlee"

ThisBuild / version := "0.1-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.12"

ThisBuild / crossScalaVersions := Seq("2.11.8", "2.12.12")

ThisBuild / autoScalaLibrary := true



lazy val commonSettings = Seq(
  javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
  Test / parallelExecution := false,
  Test / fork := true,
  resolvers ++= Seq("jitpack" at "https://jitpack.io/")
)

lazy val dependencies =
  new {
    val graphFramesVersion = "0.8"
    val sparkVersion = "2.4.5"
    val scalatestVersion = "3.0.8"

    val graphFrames = "graphframes" % "graphframes" % s"$graphFramesVersion-spark2.4-s_${scalaVersion}"
    val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion
    val sparkSQL = "org.apache.spark" %% "spark-sql" % sparkVersion
    val sparkTestingBase = "com.holdenkarau" %% "spark-testing-base" % s"${sparkVersion}_0.12.0"
    val scalaTest = "org.scalatest" %% "scalatest" % scalatestVersion
  }

lazy val commonDependencies = Seq(
  dependencies.graphFrames % Provided,
  dependencies.sparkCore % Provided,
  dependencies.sparkSQL % Provided,
  dependencies.sparkTestingBase % Test,
  dependencies.sparkCore % Test,
  dependencies.sparkSQL % Test,
  dependencies.scalaTest % Test
)

// root project
lazy val root = (project in file("."))
  .settings(publishArtifact := false)

// sbt-sonatype configuration
ThisBuild / homepage := Some(url("https://github.com/dongjinleekr/networkframes"))
ThisBuild / scmInfo := Some(ScmInfo(url("https://github.com/dongjinleekr/networkframes"),
  "git@github.com:dongjinleekr/networkframes.git"))
ThisBuild / developers := List(Developer("username",
  "Lee Dongjin",
  "dongjin@apache.org",
  url("https://github.com/dongjinleekr")))
ThisBuild / licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }
