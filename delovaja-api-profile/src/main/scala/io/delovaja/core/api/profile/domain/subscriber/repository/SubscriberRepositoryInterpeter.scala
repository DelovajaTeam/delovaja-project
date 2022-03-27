package io.delovaja.core.api.profile.domain.subscriber.repository

import cats.effect._

import doobie.util.transactor._
import doobie.refined.implicits._
import doobie.implicits._

import io.delovaja.core.api.profile.domain.subscriber.model.Subscriber._

class SubscriberRepositoryInterpeter[F[_]: Async] (
  transactor: Transactor[F]
) extends SubscriberRepositoryAlgebra[F] {

  def findByEmail(email: Email) = 
    SQL.findByEmail(email)
      .option
      .transact(transactor)

  def save(subscriber: NewSubscriber) =
    SQL.save(subscriber)
      .unique
      .transact(transactor)

  def findById(id: Long) =
    SQL.findById(id)
      .option
      .transact(transactor)

}


object SubscriberRepositoryInterpeter {
  def apply[F[_]: Async](transactor: Transactor[F]) =
    new SubscriberRepositoryInterpeter[F](transactor)
}

private object SQL {

  def findById(id: Long) = sql"""
    SELECT * FROM profile.subscribers
    WHERE id = $id LIMIT 1
  """.query[OldSubscriber]

  def findByEmail(email: Email) = sql"""
    SELECT * FROM profile.subscribers
    WHERE email = $email LIMIT 1
  """.query[OldSubscriber]

  def save(subscriber: NewSubscriber) = sql"""
    INSERT INTO profile.subscribers(
      first_name, last_name, email, organization_id, position
    ) VALUES(
      ${subscriber.firstName}, 
      ${subscriber.lastName},
      ${subscriber.organizationId},
      ${subscriber.position} 
    ) RETURNING id, first_name, last_name, email, organization_id, position
  """.query[OldSubscriber]

}
