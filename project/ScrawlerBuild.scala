import _root_.net.virtualvoid.sbt.graph.Plugin._
import sbt._
import sbt.Keys._
import sbtassembly.Plugin._
import sbtassembly.Plugin.AssemblyKeys._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease._
import sbtrelease.ReleaseStateTransformations._
import scala.Some

object MailerBuild extends Build {

  //Resolvers
  lazy val commonResolvers = Seq(
    "Codahale Repo" at "http://repo.codahale.com",
    "Sonatype Repo" at "https://oss.sonatype.org/content/repositories/releases/",
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Excilys" at "http://repository.excilys.com/content/groups/public",
    "sbt-idea-repo" at "http://mpeltonen.github.com/maven/",
    "Mandubian repository snapshots" at "https://github.com/mandubian/mandubian-mvn/raw/master/snapshots/",
    "Mandubian repository releases" at "https://github.com/mandubian/mandubian-mvn/raw/master/releases/",
    Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver
      .ivyStylePatterns)
  )

  //Dependencies
  lazy val commonDependencies = Seq(
    "commons-lang" % "commons-lang" % "2.6",
    "org.seleniumhq.selenium" % "selenium-server" % "2.33.0",
    "org.seleniumhq.selenium" % "selenium-java" % "2.33.0",
    "org.pegdown" % "pegdown" % "1.3.0",
    "org.jsoup" % "jsoup" % "1.7.2",
    "net.debasishg" %% "redisreact" % "0.1",
    "org.specs2" %% "specs2" % "1.14" % "test",
    "junit" % "junit" % "4.10" % "test",
    "ch.qos.logback" % "logback-classic" % "1.0.9",
    "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test",
    "play" %% "play-json" % "2.2-SNAPSHOT",
    "com.github.nscala-time" %% "nscala-time" % "0.2.0",
    "javax.mail" % "mail" % "1.4.2",
    "com.typesafe.akka" %% "akka-actor" % "2.2.0",
    "net.databinder.dispatch" %% "dispatch-core" % "0.10.1",
    "net.databinder.dispatch" %% "dispatch-json4s-native" % "0.10.1",
    "org.mongodb" %% "casbah" % "2.6.1"
  )

  //Build Settings applied to all projects
  lazy val commonBuildSettings = Seq(
    organization := "com.lion.mailer",
    scalaVersion := "2.10.2",
    resolvers ++= commonResolvers
  )

  //Settings applied to all projects
  lazy val defaultSettings =
    Defaults.defaultSettings ++ assemblySettings ++ commonBuildSettings ++ releaseSettings ++ Seq(
      libraryDependencies ++= commonDependencies,
      releaseProcess := releaseSteps,
      fork in test := true, //Fork a new JVM for running tests
      javaOptions in (Test, run) += "-XX:MaxPermSize=128m"
    )

  //Release steps for sbt-release plugin
  lazy val releaseSteps = Seq[ReleaseStep](
    //checkSnapshotDependencies,              // Check whether the working directory is a git repository and the
    // repository has no outstanding changes
    //inquireVersions,                        // Ask the user for the release version and the next development version
    runTest // Run test:test, if any test fails, the release process is aborted
    //setReleaseVersion,                      // Write version in ThisBuild := "releaseVersion" to the file version
    // .sbt and also apply this setting to the current build state.
    //commitReleaseVersion                   // Commit the changes in version.sbt.
    //tagRelease                             // Tag the previous commit with version (eg. v1.2, v1.2.3).
    //publishArtifacts,                       // Run publish.
    //setNextVersion,                         // Write version in ThisBuild := "nextVersion" to the file version.sbt
    // and also apply this setting to the current build state.
    //commitNextVersion,                      // Commit the changes in version.sbt.
    //pushChanges                             // Push changes
  )


  //Main project configuration
  lazy val root = Project(
    id = "mailer",
    base = file("."),
    settings = defaultSettings ++ Seq(
      mainClass in (Compile, run) := Some("com.lion.mailer.Main"),
      mainClass in assembly := Some("com.lion.mailer.Main"))
  ).settings(graphSettings: _*)



}