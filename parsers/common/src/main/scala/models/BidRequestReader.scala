package models

trait BidRequestReader {

  def parse(line: String, lastResult: ParsingResult): ParsingResult

}
