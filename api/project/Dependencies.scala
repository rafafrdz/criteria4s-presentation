import sbt.*

object Dependencies {

  object http4s {
    lazy val emberServer: ModuleID = "org.http4s" %% "http4s-ember-server" % Version.Http4s
    lazy val emberClient: ModuleID = "org.http4s" %% "http4s-ember-client" % Version.Http4s
    lazy val circe: ModuleID       = "org.http4s" %% "http4s-circe"        % Version.Http4s
    lazy val dsl: ModuleID         = "org.http4s" %% "http4s-dsl"          % Version.Http4s
  }

  object circe {
    lazy val generic: ModuleID = "io.circe" %% "circe-generic" % Version.Circe
  }

  object doobie {
    lazy val core: ModuleID          = "org.tpolecat" %% "doobie-core"           % Version.Doobie
    lazy val postgres: ModuleID      = "org.tpolecat" %% "doobie-postgres"       % Version.Doobie
    lazy val postgresCirce: ModuleID = "org.tpolecat" %% "doobie-postgres-circe" % Version.Doobie
    lazy val hikari: ModuleID        = "org.tpolecat" %% "doobie-hikari"         % Version.Doobie
  }
  object cats {
    lazy val kernel: ModuleID = "org.typelevel" %% "cats-kernel" % Version.Cats
    lazy val core: ModuleID   = "org.typelevel" %% "cats-core"   % Version.Cats
    lazy val laws: ModuleID   = "org.typelevel" %% "cats-laws"   % Version.Cats

  }

  object catsEffect {
    lazy val core: ModuleID   = "org.typelevel" %% "cats-effect"        % Version.CatsEffect
    lazy val kernel: ModuleID = "org.typelevel" %% "cats-effect-kernel" % Version.CatsEffect
    lazy val std: ModuleID    = "org.typelevel" %% "cats-effect-std"    % Version.CatsEffect
  }

  object log4cats {
    lazy val core: ModuleID  = "org.typelevel" %% "log4cats-core"  % Version.Log4Cats
    lazy val slf4j: ModuleID = "org.typelevel" %% "log4cats-slf4j" % Version.Log4Cats
  }

  object criteria4s {
    lazy val core: ModuleID = "io.github.rafafrdz" %% "criteria4s-core" % Version.Criteria4s
    lazy val sql: ModuleID  = "io.github.rafafrdz" %% "criteria4s-sql"  % Version.Criteria4s
  }

  object logback {
    lazy val classic: ModuleID = "ch.qos.logback" % "logback-classic" % Version.Logback
  }

  object tofu {
    lazy val core: ModuleID  = "tf.tofu" %% "derevo-core"           % Version.Tofu
    lazy val cats: ModuleID  = "tf.tofu" %% "derevo-cats"           % Version.Tofu
    lazy val circe: ModuleID = "tf.tofu" %% "derevo-circe-magnolia" % Version.Tofu
  }

  object estatico {
    lazy val newtype: ModuleID = "io.estatico" %% "newtype" % Version.Estatico
  }

  object pureconfig {
    lazy val pureconfig: ModuleID = "com.github.pureconfig" %% "pureconfig" % Version.Pureconfig
  }

  object database {
    lazy val postgresql: ModuleID = "org.postgresql" % "postgresql" % Version.Postgres
  }

}
