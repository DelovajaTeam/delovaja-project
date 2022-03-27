package io.delovaja.core.api.profile.domain.organization.model

import cats.implicits._
import cats.effect._
import cats.data._

import eu.timepit.refined.string._
import eu.timepit.refined.api._

import io.delovaja.core.api.profile.types._

object Organization {
  
  type Name                    = String Refined MatchesRegex["""^[\p{L}\s+]{1,100}$"""]
  type TaxNumber               = String Refined MatchesRegex["""[\d]{10,10}"""]
  type StateRegistrationNumber = String Refined MatchesRegex["""[\d]{13,13}"""]
  type Address                 = String Refined MatchesRegex["""^[\p{L}\d\s+/,/.]{1,255}$"""]

  sealed trait Organization {
    def name: Name
    def taxNumber: TaxNumber
    def stateRegistrationNumber: StateRegistrationNumber
    def address: Address
    def workArea: Option[String]
  }

  final case class NewOrganization(
    name: Name,
    taxNumber: TaxNumber,
    stateRegistrationNumber: StateRegistrationNumber,
    address: Address,
    workArea: Option[String]
  ) extends Organization

  final case class OldOrganization(
    id: Long,
    name: Name,
    taxNumber: TaxNumber,
    stateRegistrationNumber: StateRegistrationNumber,
    address: Address,
    workArea: Option[String]
  ) extends Organization
}
