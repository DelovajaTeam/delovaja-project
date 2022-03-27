package io.delovaja.core.api.profile.domain.subscriber.model

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string._
import eu.timepit.refined.numeric._
import eu.timepit.refined.collection._

object  Subscriber {

  type FirstName      = String Refined MatchesRegex["""^[\p{L}]{1,20}$"""]
  type LastName       = String Refined MatchesRegex["""^[\p{L}]{1,30}$"""]
  type Email          = String Refined MatchesRegex[""" ^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"""]
  type OrganizationId = Long Refined Positive
  type Position       = String Refined MatchesRegex["""^[\p{L}\s+]{1,100}$"""]

  sealed trait Subscriber {
    def firstName: FirstName
    def lastName: LastName
    def email: Email
    def organizationId: OrganizationId
    def position: Position
  }

  final case class NewSubscriber(
    firstName: FirstName,
    lastName: LastName,
    email: Email,
    organizationId: OrganizationId,
    position: Position
  ) extends Subscriber

  final case class OldSubscriber(
    id: Long,
    firstName: FirstName,
    lastName: LastName,
    email: Email,
    organizationId: OrganizationId,
    position: Position
  ) extends Subscriber

}



