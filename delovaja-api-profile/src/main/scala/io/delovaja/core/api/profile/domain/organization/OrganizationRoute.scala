package io.delovaja.core.api.profile.domain.organization

import cats.implicits._
import cats.effect._
import cats.data._

import io.circe.generic.auto._
import io.circe.refined._

import org.http4s._
import org.http4s.implicits._
import org.http4s.circe._
import org.http4s.circe.CirceEntityCodec._

import io.delovaja.core.api.profile.domain.Route
import io.delovaja.core.api.profile.domain.organization.model._
import io.delovaja.core.api.profile.domain.organization.model.Organization._

import com.typesafe.scalalogging.LazyLogging

class OrganizationRoute[F[_]: Async](
  organizationService: OrganizationService[F]
) extends Route[F] with LazyLogging {

  import dsl._

  override def request: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root / "organization" => (for {
      json    <- requestAs[NewOrganization](req)
      result  <- EitherT(organizationService.createOrganization(json)).biflatMap(
        error  => EitherT.left[Response[F]](Conflict(error.message)),
        result => EitherT.right[Response[F]](Ok(result)), 
      )
    } yield result).merge

    case req @ GET -> Root / "organization" / id => for {
      organization <- organizationService.findOrganizationById(id.toLong)
      result       <- organization.map(Ok(_)).toRight(NotFound()).merge
    } yield result
  }
}

object OrganizationRoute {
  def apply[F[_]: Async](organizationService: OrganizationService[F]) = 
    new OrganizationRoute[F](organizationService)
}
