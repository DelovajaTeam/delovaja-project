package io.delovaja.core.api.profile.config

import pureconfig.ConfigSource
import pureconfig._
import pureconfig.generic.auto._

object Properties {

  def loadProperties = 
    ConfigSource.default.loadOrThrow[ApplicationProperties]
  
  final case class ApplicationProperties(
    server: ServerProperties, 
    database: DatabaseProperties
  )

  final case class ServerProperties(
    port: Int
  )

  final case class DatabaseProperties(
    url: String,
    poolSize: Int,
    driverClassName: String,
    username: String,
    password: String
  )

}
