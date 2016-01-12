package models.bidrequest

case class Native(request: String, ver: Option[String], api: Option[List[Int]], battr: Option[List[Int]],
                  ext: Option[Ext])
