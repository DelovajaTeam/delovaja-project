package io.delovaja.core.api.profile

import cats.data.NonEmptyList

object types {
  type ErrorOr[A, B] = Either[NonEmptyList[A], B]
}
