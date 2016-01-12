package models.bidrequest

case class Site(id: Option[String], name: Option[String], domain: Option[String], cat: Option[List[String]],
                sectioncat: Option[List[String]], pagecat: Option[List[String]], page: Option[String],
                ref: Option[String], search: Option[String], mobile: Option[Int],
                privacypolicy: Option[Int], publisher: Option[Publisher], content: Option[Content],
                keywords: Option[String], ext: Option[Ext])
