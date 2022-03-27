package io.delovaja.core.api.profile.domain.organization.model

sealed trait OrganizationApiError {
  def message: String
} 

final case class OrganizationAlreadyExists(override val message: String)
  extends OrganizationApiError