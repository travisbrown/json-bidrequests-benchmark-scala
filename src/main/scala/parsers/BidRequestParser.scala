package parsers

import models.ParsingResult

trait BidRequestParser {

  def parse(index: Int, line: String, lastResult: ParsingResult): ParsingResult

}
