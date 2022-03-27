package io.delovaja.core.api.profile.domain.subscriber

import cats.effect._
import cats.data._
import cats.implicits._

import io.circe.generic.auto._
import io.circe.refined._

import org.http4s._
import org.http4s.circe.CirceEntityCodec._

import io.delovaja.core.api.profile.domain.Route
import io.delovaja.core.api.profile.domain.subscriber.model.Subscriber._

class SubscriberRoute[F[_]: Async](
  subscriberService: SubscriberService[F]
) extends Route[F] {

  import dsl._

  override def request: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root / "subscriber" => (for {
      json   <- requestAs[NewSubscriber](req)
      result <- EitherT(subscriberService.createSubscriber(json)).biflatMap(
        error  => EitherT.left[Response[F]](Conflict(error.message)),
        result => EitherT.right[Response[F]](Ok(result)), 
      )
    } yield result).merge

    case GET -> Root / "subscriber" / id => for {
      subscriber <- subscriberService.findSubscriberById(id.toLong)
      result     <- subscriber.map(Ok(_)).toRight(NotFound()).merge
    } yield result
  }
}
