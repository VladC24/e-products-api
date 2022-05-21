package models

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import play.api.libs.json.{Json, Reads}

case class Rating(rate: Double, count: Int)

object Rating {
  implicit val fooDecoder: Decoder[Rating] = deriveDecoder
  implicit val fooEncoder: Encoder[Rating] = deriveEncoder
  implicit val orderReads: Reads[Rating] = Json.reads[Rating]
}
