package models

case class ParsingResult(ok: Long, cannotParse: Long, cannotUnmarshal: Long) {
  def incrOk = copy(ok = ok + 1)
  def incrCannotParse = copy(cannotParse = cannotParse + 1)
  def incrCannotUnmarshal = copy(cannotUnmarshal = cannotUnmarshal + 1)

  override def toString = s"$ok ok, $cannotParse cannot unmarshal, $cannotParse cannot parse JSON"
}
