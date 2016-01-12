package models.bidrequest

case class Pmp(private_auction: Option[Int], deals: Option[List[Deal]], ext: Option[Ext])

