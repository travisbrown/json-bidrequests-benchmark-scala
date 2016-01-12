package models.bidrequest

case class Imp(
  id: String,
  banner: Option[Banner] = None,
  video: Option[Video] = None,
  native: Option[Native] = None,
  displaymanager: Option[String] = None,
  displaymanagerver: Option[String] = None,
  instl: Int = 0,
  tagid: Option[String] = None,
  bidfloor: Float = 0,
  bidfloorcur: String = "USD",
  secure: Boolean = false,
  iframebuster: Option[Seq[String]] = None,
  pmp: Option[Pmp] = None,
  ext: Option[Ext] = None
)
