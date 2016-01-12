package models.bidrequest

case class User(id: Option[String], buyerid: Option[String], yop: Option[Int], gender: Option[String],
                keywords: Option[String], customdata: Option[String], geo: Option[Geo], data: Option[List[Data]],
                ext: Option[Ext])

