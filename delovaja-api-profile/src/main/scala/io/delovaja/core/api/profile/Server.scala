package io.delovaja.core.api.profile

import cats.implicits._
import cats.effect.kernel._
import io.delovaja.core.api.profile.domain.organization._
import org.http4s.server.Router
import org.http4s.blaze.server.BlazeServerBuilder
import cats.effect.IOApp
import cats.effect.{ExitCode, IO}
import io.delovaja.core.api.profile.config.Properties._
import io.delovaja.core.api.profile.config.DatabaseConfiguration._
import io.delovaja.core.api.profile.config.MigrationConnfiguration._
import io.delovaja.core.api.profile.domain.organization.repository._
import org.flywaydb.core.api.output.MigrateResult
import io.delovaja.core.api.profile.domain.subscriber.repository.SubscriberRepositoryInterpeter
import io.delovaja.core.api.profile.domain.subscriber.SubscriberService
import cats.Parallel

object Server extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = buildServer[IO]
    .use(_.serve.compile.drain)
    .map(_ => ExitCode.Success)

  def buildServer[F[_]: Async: Parallel] = for {
    config     <- Resource.pure[F, ApplicationProperties](loadProperties)
    transactor <- databaseConnection(config.database)
    _          <- Resource.eval((migrate(transactor)))
    organizatorRepository = OrganizationRepositoryInterpreter[F](transactor)
    subscriberRepository  = SubscriberRepositoryInterpeter[F](transactor)
    subscriberService     = SubscriberService[F](subscriberRepository)
    organizationService   = OrganizationService[F](organizatorRepository)
    organizationRoute     = OrganizationRoute[F](organizationService)
  } yield BlazeServerBuilder[F].withHttpApp(
    Router("/" -> organizationRoute.request).orNotFound
  ).bindHttp(8091)
  
}
