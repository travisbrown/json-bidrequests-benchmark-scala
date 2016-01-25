package benchs

import parsers._

object Referential {

  val readers = Map(
    "play" -> PlayNativeReader,
    "play-jawn" -> PlayJawnReader,
    "circe-jawn" -> CirceReader,
    "json4s" -> Json4sNativeReader,
    "json4s-jackson" -> Json4sJacksonReader,
    "json4s-jawn" -> Json4sJawnReader,
    "argonaut-jawn" -> ArgonautReader,
    "spray" -> SprayNativeReader,
    "spray-jawn" -> SprayJawnReader
  )

}
