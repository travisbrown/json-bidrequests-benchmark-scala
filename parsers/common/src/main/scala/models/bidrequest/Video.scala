package models.bidrequest

case class Video(mimes: List[String], minduration: Option[Int], maxduration: Option[Int],
                 protocols: Option[List[Int]], w: Option[Int], h: Option[Int],
                 startdelay: Option[Int], linearity: Option[Int], sequence: Option[Int],
                 battr: Option[List[Int]], maxextended: Option[Int], minbitrate: Option[Int],
                 maxbitrate: Option[Int], boxingallowed: Option[Int] = Some(1),
                 playbackmethod: Option[List[Int]], delivery: Option[List[Int]],
                 pos: Option[Int], companionad: Option[List[Banner]], api: Option[List[Int]],
                 companiontype: Option[List[Int]], ext: Option[Ext])

