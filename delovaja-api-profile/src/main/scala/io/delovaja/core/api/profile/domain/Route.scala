package io.delovaja.core.api.profile.domain

import cats._
import cats.data._
import cats.implicits._
import cats.effect._

import org.http4s._
import org.http4s.circe.CirceEntityCodec._

import io.circe._

abstract class Route[F[_]: Async] {

  val dsl = org.http4s.dsl.Http4sDsl.apply[F]
  import dsl._

  type ApiResponse = EitherT[F, Response[F], Response[F]]

  def requestAs[A: Decoder](request: Request[F]) = 
    request.attemptAs[A].leftFlatMap {
      case InvalidMessageBodyFailure(_, Some((err: Error))) => 
        EitherT.left[A](BadRequest(err.getMessage))
      case _ => 
        EitherT.left[A](BadRequest(s"unprocessable error"))
    }

  def request: HttpRoutes[F]
}
