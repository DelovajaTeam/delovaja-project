package io.delovaja.core.api.profile.domain.organization.repository

import io.delovaja.core.api.profile.domain.organization.model.Organization._

trait OrganizationRepositoryAlgebra[F[_]] {

  def findByTaxNumber(taxNumber: TaxNumber): F[Option[OldOrganization]]

  def findByStateRegistrationNumber(stateRegistrationNumber: StateRegistrationNumber): F[Option[OldOrganization]]

  def findById(id: Long): F[Option[OldOrganization]]

  def save(organization: NewOrganization): F[OldOrganization]

}

