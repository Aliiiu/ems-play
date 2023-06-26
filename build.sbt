name := """employee-management-system"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := "employee-management-system",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.11",
    libraryDependencies ++= Seq(
      guice,
      javaJdbc,
      javaJpa,
      "com.h2database" % "h2" % "1.4.200",
      "org.hibernate" % "hibernate-core" % "5.6.15.Final",
      "org.postgresql" % "postgresql" % "42.2.18",
      "io.dropwizard.metrics" % "metrics-core" % "4.2.19",
      "com.palominolabs.http" % "url-builder" % "1.1.5",
      "net.jodah" % "failsafe" % "2.4.4",
    ),
    PlayKeys.externalizeResources := false,
    (Test / testOptions) := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v")),
    javacOptions ++= Seq(
      "-Xlint:unchecked",
      "-Xlint:deprecation"
//      "-Werror"
    )
  )
