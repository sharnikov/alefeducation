lazy val root = (project in file("."))
  .settings(
    name := "OysterCardTask",
    scalaVersion := "2.13.5",
    organization := "eLama",
    organizationName := "test",
    libraryDependencies ++= Seq(
        "com.beachape"   %% "enumeratum" % "1.6.1",
        "org.scalamock"  %% "scalamock"  % "4.4.0"  % Test,
        "org.scalatest"  %% "scalatest" % "3.2.3" % Test,
        "org.scalatest"  %% "scalatest-shouldmatchers" % "3.2.3" % Test
    )
  )
