package models.bidrequest

case class Banner(w: Option[Int], h: Option[Int], wmax: Option[Int], hmax: Option[Int], wmin: Option[Int],
                  hmin: Option[Int], id: Option[String], btype: Option[List[Int]],
                  battr: Option[List[Int]], pos: Option[Int], mimes: Option[List[String]],
                  topframe: Option[Int], expdir: Option[List[Int]], api: Option[List[Int]],
                  ext: Option[Ext])

