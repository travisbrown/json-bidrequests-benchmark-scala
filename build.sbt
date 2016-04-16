name := "json-bidrequests-benchmark"
version := "0.0.1"
scalaVersion in ThisBuild := "2.11.8"

lazy val benchs = (project in file("benchs"))
  .dependsOn(common)
  .dependsOn(playjson)
  .dependsOn(circe)
  .dependsOn(json4s)
  .dependsOn(argonaut)
  .dependsOn(spray)
  .enablePlugins(JmhPlugin)

libraryDependencies in ThisBuild ++= Seq(
  "org.spire-math" %% "jawn-parser" % "0.8.4"
)

lazy val common = (project in file("parsers/common"))

lazy val playjson = (project in file("parsers/play"))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.4.2",
      "org.spire-math" %% "jawn-play" % "0.8.4"
    )
  ).dependsOn(common)

lazy val circe = (project in file("parsers/circe"))
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.4.0",
      "io.circe" %% "circe-generic" % "0.4.0",
      "io.circe" %% "circe-parser" % "0.4.0"
    )
  ).dependsOn(common)

lazy val json4s = (project in file("parsers/json4s"))
  .settings(
    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-native" % "3.3.0",
      "org.json4s" %% "json4s-jackson" % "3.3.0",
      "org.spire-math" %% "jawn-json4s" % "0.8.4"
    )
  ).dependsOn(common)

lazy val argonaut = (project in file("parsers/argonaut"))
  .settings(
    libraryDependencies ++= Seq(
      "io.argonaut" %% "argonaut" % "6.1",
      "org.spire-math" %% "jawn-argonaut" % "0.8.4"
    )
  ).dependsOn(common)

lazy val spray = (project in file("parsers/spray"))
  .settings(
    libraryDependencies ++= Seq(
      "io.spray" %%  "spray-json" % "1.3.2",
      "org.spire-math" %% "jawn-spray" % "0.8.4"
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

