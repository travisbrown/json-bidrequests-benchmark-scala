package models.bidrequest

case class App(id: Option[String], name: Option[String], bundle: Option[String], domain: Option[String],
               storeurl: Option[String], cat: Option[List[String]], sectioncat: Option[List[String]],
               pagecat: Option[List[String]], ver: Option[String], privacypolicy: Option[Int],
               paid: Option[Int], publisher: Option[Publisher], content: Option[Content],
               keywords: Option[String], ext: Option[Ext])


