scalaVersion := "2.13.8"
name         := "delovaja-api-profile"
organization := "io.delovaja.core"
version      := "0.0.1-SNAPSHOT"

val http4sVersion     = "0.23.11"
val refinedVersion    = "0.9.28"
val circeVersion      = "0.14.1"
val loggingVersion    = "3.9.4"
val logbackVersion    = "1.2.11"
val doobieVersion     = "1.0.0-RC2"
val flywayVersion     = "8.5.4"
val pureConfigVersion = "0.17.1"

libraryDependencies += "org.http4s"                 %% "http4s-dsl"          % http4sVersion
libraryDependencies += "org.http4s"                 %% "http4s-blaze-server" % http4sVersion
libraryDependencies += "org.http4s"                 %% "http4s-circe"        % http4sVersion
libraryDependencies += "io.circe"                   %% "circe-generic"       % circeVersion
libraryDependencies += "io.circe"                   %% "circe-literal"       % circeVersion
libraryDependencies += "io.circe"                   %% "circe-refined"       % circeVersion
libraryDependencies += "ch.qos.logback"             % "logback-classic"      % logbackVersion
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging"       % loggingVersion
libraryDependencies += "org.tpolecat"               %% "doobie-core"         % doobieVersion
libraryDependencies += "org.tpolecat"               %% "doobie-postgres"     % doobieVersion
libraryDependencies += "org.tpolecat"               %% "doobie-hikari"       % doobieVersion
libraryDependencies += "org.tpolecat"               %% "doobie-refined"      % doobieVersion
libraryDependencies += "org.flywaydb"               % "flyway-core"          % flywayVersion
libraryDependencies += "com.github.pureconfig"      %% "pureconfig"          % pureConfigVersion