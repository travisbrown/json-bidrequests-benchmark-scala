package models

trait BidRequestParser {

  def parse(index: Int, line: String, lastResult: ParsingResult): ParsingResult

}
