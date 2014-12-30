import sbt._
import sbt.Keys._
object Build extends sbt.Build {

  lazy val basicSettings = Seq(
    organization := "com.yiguang",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.11.4",
    crossScalaVersions := Seq("2.10.4", "2.11.4")
  )

  lazy val root = Project("perftool",file("."))
    .settings(basicSettings:_*)
    .settings(libraryDependencies ++= Dependencies.all)
    .settings(XitrumPackage.copy("bin","log"):_*)
}

object Dependencies {
  val logback       = "ch.qos.logback" % "logback-classic" % "1.1.1"
  val slf4s         = "org.slf4s" %% "slf4s-api" % "1.7.7"
  val scala_test    = "org.scalatest" %% "scalatest" % "2.2.1"
  val xmemcached    = "com.googlecode.xmemcached" % "xmemcached" % "2.0.0"
  val akka_actor    = "com.typesafe.akka" %% "akka-actor" % "2.3.7"
  val whalinMC      = "com.whalin" % "Memcached-Java-Client" % "3.0.0"
  val scopt         = "com.github.scopt" %% "scopt" % "3.3.0"
  val httpclient    = "org.apache.httpcomponents" % "httpclient" % "4.3.6"


  val all = Seq(logback,slf4s,scala_test,xmemcached,akka_actor,whalinMC,scopt,httpclient)
}
