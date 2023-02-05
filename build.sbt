ThisBuild / organization := "dev.astern"
ThisBuild / scalaVersion := "3.2.2"

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    "io.d11"  %% "zhttp" % "2.0.0-RC10",
    "dev.zio" %% "zio" % "2.0.0",
    "dev.zio" %% "zio-json" % "0.3.0-RC10"
  )
)

lazy val all = project.in(file("./apps/all"))
  .dependsOn(
    auth, utils
  )
  .settings(commonSettings)

lazy val auth = project.in(file("./apps/auth"))
  .dependsOn(utils)
  .settings(commonSettings)

lazy val utils = project.in(file("./libs/utils"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "io.d11"  %% "zhttp" % "2.0.0-RC10",
      "dev.zio" %% "zio" % "2.0.0",
      "dev.zio" %% "zio-json" % "0.3.0-RC10"
    )
  )

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .aggregate(
    auth,
    utils
  )
  
lazy val commonSettings = Seq(

)
