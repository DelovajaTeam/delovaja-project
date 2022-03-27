package io.delovaja.core.api.profile.domain.subscriber.repository

import io.delovaja.core.api.profile.domain.subscriber.model.Subscriber._

trait SubscriberRepositoryAlgebra[F[_]] {
  
  def findByEmail(email: Email): F[Option[OldSubscriber]]

  def save(subscriber: NewSubscriber): F[OldSubscriber]

  def findById(id: Long): F[Option[OldSubscriber]]
}
