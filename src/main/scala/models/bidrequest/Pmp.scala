package models.bidrequest

case class Pmp(private_auction: Option[Int], deals: Option[Seq[Deal]], ext: Option[Ext])

