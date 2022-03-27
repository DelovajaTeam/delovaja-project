package io.delovaja.core.api.profile.config

import cats.effect.kernel.Sync
import doobie.util.transactor.Transactor
import org.flywaydb.core.Flyway
import doobie.hikari.HikariTransactor

object MigrationConnfiguration {

  def migrate[F[_]: Sync](transactor: HikariTransactor[F]) = 
    transactor.configure { datasource => Sync[F].delay {
      Flyway
        .configure
        .dataSource(datasource)
        .load
        .migrate
    }
  }
  
}
