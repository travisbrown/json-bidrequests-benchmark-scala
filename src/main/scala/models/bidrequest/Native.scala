package models.bidrequest

case class Native(request: String, ver: Option[String], api: Option[Seq[Int]], battr: Option[Seq[Int]],
                  ext: Option[Ext])
