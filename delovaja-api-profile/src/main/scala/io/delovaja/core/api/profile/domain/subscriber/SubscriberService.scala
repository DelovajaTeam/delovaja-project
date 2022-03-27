package io.delovaja.core.api.profile.domain.subscriber

import io.delovaja.core.api.profile.domain.subscriber.model._
import io.delovaja.core.api.profile.domain.subscriber.model.Subscriber._
import io.delovaja.core.api.profile.domain.subscriber.repository.SubscriberRepositoryAlgebra

import cats.implicits._
import cats.effect.kernel.Async

class SubscriberService[F[_]: Async](
  subscriberRepository: SubscriberRepositoryAlgebra[F]
) {

  def createSubscriber(subscriber: NewSubscriber) = for {
    existsingSubscriber <- subscriberRepository.findByEmail(subscriber.email)
    savedSubscriber     <- (existsingSubscriber match {
      case Some(value) => SubscriberAlreadyExists(s"subscriber already exists [$value]").asLeft
      case None        => subscriberRepository.save(subscriber).asRight
    }).sequence
  } yield savedSubscriber

  def findSubscriberById(id: Long): F[Option[OldSubscriber]] = 
    subscriberRepository.findById(id)

}

object SubscriberService {
  def apply[F[_]: Async](
    subscriberRepository: SubscriberRepositoryAlgebra[F]
  ) = new SubscriberService[F](subscriberRepository)
}
