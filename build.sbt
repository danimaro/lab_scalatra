organization := "se.lab_scala"

name := "lab_scalatra"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.1"

seq(webSettings :_*)

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.0.1",
  "org.scalatra" %% "scalatra-scalate" % "2.0.1",
  "org.scalatra" %% "scalatra-anti-xml" % "2.0.1",
  "org.scalaquery" % "scalaquery_2.9.0-1" % "0.9.5",
  "mysql" % "mysql-connector-java" % "5.1.12",
  "org.eclipse.jetty" % "jetty-webapp" % "7.4.5.v20110725" % "container",
  "javax.servlet" % "servlet-api" % "2.5" % "provided"
)

resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
