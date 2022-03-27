package io.delovaja.core.api.profile.domain.organization.repository

import cats.effect._

import doobie.implicits._
import doobie.refined.implicits._
import doobie.util.transactor.Transactor

import io.delovaja.core.api.profile.domain.organization.model.Organization._

class OrganizationRepositoryInterpreter[F[_]: Async](
  transactor: Transactor[F]
) extends OrganizationRepositoryAlgebra[F] {

  override def findByTaxNumber(taxNumber: TaxNumber) = 
    SQL.findByTaxNumber(taxNumber)
      .option
      .transact(transactor)

  override def findByStateRegistrationNumber(stateRegistrationNumber: StateRegistrationNumber) =
    SQL.findByStateRegistrationNumber(stateRegistrationNumber)
    .option
    .transact(transactor)

  override def save(organization: NewOrganization) = 
    SQL.save(organization)
      .unique
      .transact(transactor)

  override def findById(id: Long) = 
    SQL.findById(id)
      .option
      .transact(transactor)

}

object OrganizationRepositoryInterpreter {
  def apply[F[_]: Async](transactor: Transactor[F]) =
    new OrganizationRepositoryInterpreter[F](transactor)
}

private object SQL {

  def findByTaxNumber(taxNumber: TaxNumber) = sql"""
    SELECT * FROM profile.organizations
    WHERE tax_number = $taxNumber LIMIT 1
  """.query[OldOrganization]

  def findByStateRegistrationNumber(stateRegistrationNumber: StateRegistrationNumber) = sql"""
    SELECT * FROM profile.organizations
    WHERE state_registration_number = $stateRegistrationNumber LIMIT 1
  """.query[OldOrganization]

  def findById(id: Long) = sql"""
    SELECT * FROM profile.organizations
    WHERE ID = $id LIMIT 1
  """.query[OldOrganization]

  def save(organization: NewOrganization) = sql"""
    INSERT INTO profile.organizations(
      name, tax_number, state_registration_number, address, work_area
    ) VALUES(
      ${organization.name}, 
      ${organization.taxNumber},
      ${organization.stateRegistrationNumber},
      ${organization.address},
      ${organization.workArea} 
    ) RETURNING id, name, tax_number, state_registration_number, address, work_area
  """.query[OldOrganization]

}
