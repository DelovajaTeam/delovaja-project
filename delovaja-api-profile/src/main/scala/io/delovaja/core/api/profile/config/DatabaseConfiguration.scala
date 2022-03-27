package io.delovaja.core.api.profile.config

import cats.effect._

import io.delovaja.core.api.profile.config.Properties._

import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts

object DatabaseConfiguration {

  def databaseConnection[F[_]: Async](properties: DatabaseProperties): Resource[F, HikariTransactor[F]] = for {
    pool <- ExecutionContexts.fixedThreadPool(properties.poolSize)
    xa   <- HikariTransactor.newHikariTransactor[F](
       properties.driverClassName,
       properties.url,
       properties.username, 
       properties.password, 
       pool
    )
  } yield xa
}
