package io.delovaja.core.api.profile.domain.subscriber.model

sealed abstract class SubscriberApiError(val message: String)

final case class SubscriberAlreadyExists(override val message: String) 
  extends SubscriberApiError(message)