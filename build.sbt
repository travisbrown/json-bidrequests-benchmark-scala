name := "json-benchmark"
version := "0.0.1"
scalaVersion := "2.11.7"

libraryDependencies ++= {
  Seq(
    "org.scalaz" %% "scalaz-core" % "7.1.2",
    "com.typesafe.play" %% "play-json" % "2.4.6",

    "net.liftweb" %% "lift-json" % "2.6.2",

    "io.circe" %% "circe-core" % "0.2.1",
    "io.circe" %% "circe-generic" % "0.2.1",
    "io.circe" %% "circe-parse" % "0.2.1",

    "org.json4s" %% "json4s-jackson" % "3.3.0",

    "io.argonaut" %% "argonaut" % "6.0.4"
  )
}

scalacOptions ++= Seq(
  "-deprecation",           // Warn when deprecated API are used
  "-feature",               // Warn for usages of features that should be importer explicitly
  "-unchecked",             // Warn when generated code depends on assumptions
  "-Ywarn-dead-code",       // Warn when dead code is identified
  "-Ywarn-numeric-widen",   // Warn when numeric are widened
  "-Xlint",                 // Additional warnings (see scalac -Xlint:help)
  "-Ywarn-adapted-args"     // Warn if an argument list is modified to match the receive
)

