name := "json-benchmark"
version := "0.0.1"
scalaVersion in ThisBuild := "2.11.7"

lazy val root = (project in file("."))
  .dependsOn(common)
  .dependsOn(playjson)
  .dependsOn(circe)
  .dependsOn(json4s)
  .dependsOn(argonaut)

lazy val common = (project in file("parsers/common"))

lazy val playjson = (project in file("parsers/play"))
  .settings(
    libraryDependencies ++= Seq(
      "org.scalaz" %% "scalaz-core" % "7.1.2",
      "com.typesafe.play" %% "play-json" % "2.4.6"
    )
  ).dependsOn(common)

lazy val circe = (project in file("parsers/circe"))
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.2.1",
      "io.circe" %% "circe-generic" % "0.2.1",
      "io.circe" %% "circe-parse" % "0.2.1"
    )
  ).dependsOn(common)

lazy val json4s = (project in file("parsers/json4s"))
  .settings(
    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-native" % "3.3.0",
      "org.json4s" %% "json4s-jackson" % "3.3.0"
    )
  ).dependsOn(common)

lazy val argonaut = (project in file("parsers/argonaut"))
  .settings(
    libraryDependencies ++= Seq(
      "io.argonaut" %% "argonaut" % "6.1"
    )
  ).dependsOn(common)


scalacOptions in ThisBuild ++= Seq(
  "-deprecation",           // Warn when deprecated API are used
  "-feature",               // Warn for usages of features that should be importer explicitly
  "-unchecked",             // Warn when generated code depends on assumptions
  "-Ywarn-dead-code",       // Warn when dead code is identified
  "-Ywarn-numeric-widen",   // Warn when numeric are widened
  "-Xlint",                 // Additional warnings (see scalac -Xlint:help)
  "-Ywarn-adapted-args"     // Warn if an argument list is modified to match the receive
)

