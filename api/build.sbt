import Build.*
import Dependencies.*

lazy val api =
  project
    .in(file("."))
    .disablePlugins(AssemblyPlugin)
    .aggregate(criterias, server)
    .settings(
      name := "api"
    )

lazy val server: Project =
  (project in file("server"))
    .enablePlugins(JavaAppPackaging, AshScriptPlugin, DockerPlugin)
    .settings(
      name := "server",
      libraryDependencies ++= Seq(
        cats.core,
        catsEffect.core,
        catsEffect.std,
        estatico.newtype,
        http4s.circe,
        http4s.dsl,
        http4s.emberServer,
        logback.classic,
        pureconfig.pureconfig,
        tofu.cats,
        tofu.circe,
        tofu.core
      ),
      Universal / javaOptions ++= jvmSettings,
      run / javaOptions ++= localJvmSettings
    )
    .dependsOn(criterias)

lazy val criterias =
  (project in file("criterias"))
    .settings(
      name := "criterias",
      libraryDependencies ++= Seq(
        criteria4s.core,
        criteria4s.sql
      )
    )
