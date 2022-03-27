package io.delovaja.core.api.profile.domain.organization

import io.delovaja.core.api.profile.domain.organization.model.Organization._
import io.delovaja.core.api.profile.domain.organization.repository.OrganizationRepositoryAlgebra
import cats.effect._
import io.delovaja.core.api.profile.domain.organization.model.OrganizationApiError
import cats.implicits._
import io.delovaja.core.api.profile.domain.organization.model.OrganizationAlreadyExists
import cats.Parallel

class OrganizationService[F[_]: Async: Parallel](
  organizationRepository: OrganizationRepositoryAlgebra[F]
) {

  def createOrganization(organization: NewOrganization) = for {
    existsingOrganization <- (
      organizationRepository.findByTaxNumber(organization.taxNumber),
      organizationRepository.findByStateRegistrationNumber(organization.stateRegistrationNumber)
    ).parTupled
    savedOrganization     <- (existsingOrganization._1.orElse(existsingOrganization._2) match {
      case Some(value) => OrganizationAlreadyExists(s"organization already exists [$value]").asLeft
      case None        => organizationRepository.save(organization).asRight
    }).sequence
  } yield savedOrganization

  def findOrganizationById(id: Long) = 
    organizationRepository.findById(id)

}

object OrganizationService {
  def apply[F[_]: Async: Parallel](
    organizationRepository: OrganizationRepositoryAlgebra[F]
  ) = new OrganizationService[F](organizationRepository)
}
