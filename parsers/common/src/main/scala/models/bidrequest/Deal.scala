package models.bidrequest

case class Deal(id: String, bidfloor: Float = 0, bidfloorcur: String = "USD", at: Option[AuctionType],
                wseat: Option[List[String]], wadomain: Option[List[String]], ext: Option[Ext])
