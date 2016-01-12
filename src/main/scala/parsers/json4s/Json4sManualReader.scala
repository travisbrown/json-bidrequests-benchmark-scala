package parsers.json4s

import org.json4s._

import models.ParsingResult
import parsers.BidRequestParser

object Json4sManualReader extends BidRequestParser {
  override def parse(index: Int, line: String, lastResult: ParsingResult) = {
    ???
  }
}
