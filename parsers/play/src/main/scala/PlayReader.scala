package parsers

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

import models.bidrequest._
import models.bidrequest.device._
import models.{BidRequestParser, ParsingResult}

object PlayReader extends BidRequestParser {

  override def parse(id: Int, line: String, lastResult: ParsingResult): ParsingResult = {
    try {
      val json = Json.parse(line)
      bidRequestRead.reads(json).fold(err => lastResult.incrCannotUnmarshal, _ => lastResult.incrOk)
    } catch {
      case ex: Throwable => lastResult.incrCannotParse
    }
  }

  implicit val extRead = Json.reads[Ext]
  implicit val publisherRead = Json.reads[Publisher]
  implicit val producerRead = Json.reads[Producer]
  implicit val contentRead = Json.reads[Content]
  implicit val appRead = Json.reads[App]
  implicit val auctionTypeRead = Json.reads[AuctionType]
  implicit val bannerRead = Json.reads[Banner]
  implicit val segmentRead = Json.reads[Segment]
  implicit val dataRead = Json.reads[Data]
  implicit val dealRead = Json.reads[Deal]
  implicit val geoRead = Json.reads[Geo]
  implicit val nativeRead = Json.reads[Native]
  implicit val pmpRead = Json.reads[Pmp]
  implicit val regsRead = Json.reads[Regs]
  implicit val siteRead = Json.reads[Site]
  implicit val userRead = Json.reads[User]
  implicit val videoRead = Json.reads[Video]
  implicit val ipRead = Json.reads[IP]
  implicit val deviceInfoRead = Json.reads[DeviceInfo]
  implicit val osRead = Json.reads[OS]
  implicit val sizeRead = Json.reads[Size]
  implicit val didRead = Json.reads[DID]
  implicit val dpidRead = Json.reads[DPID]
  implicit val macRead = Json.reads[MAC]

  implicit val deviceRead = (
    (__ \ "ua").readNullable[String] and
    (__ \ "geo").readNullable[Geo] and
    (__ \ "dnt").readNullable[Int] and
    (__ \ "lmt").readNullable[Int] and
    (__).read[IP] and
    (__).read[DeviceInfo] and
    (__).read[OS] and
    (__ \ "hwv").readNullable[String] and
    (__).read[Size] and
    (__).read[DID]
  )(Device.apply _)

  implicit val impRead: Reads[Imp] = (
    (__ \ "id").read[String] and
    (__ \ "banner").readNullable[Banner] and
    (__ \ "video").readNullable[Video] and
    (__ \ "native").readNullable[Native] and
    (__ \ "displaymanager").readNullable[String] and
    (__ \ "displaymanagerver").readNullable[String] and
    (__ \ "instl").readNullable[Int].map(_.getOrElse(0)) and
    (__ \ "tagid").readNullable[String] and
    (__ \ "bidfloor").readNullable[Float].map(_.getOrElse(0f)) and
    (__ \ "bidfloorcur").readNullable[String].map(_.getOrElse("USD")) and
    (__ \ "secure").readNullable[Int].map(_.contains(1)) and
    (__ \ "iframebuster").readNullable[List[String]] and
    (__ \ "pmp").readNullable[Pmp] and
    (__ \ "ext").readNullable[Ext]
  )(Imp.apply _)

  implicit val bidRequestRead: Reads[BidRequest] = (
    (__ \ "id").read[String] and
    (__ \ "imp").read[List[Imp]] and
    (__ \ "site").readNullable[Site] and
    (__ \ "app").readNullable[App] and
    (__ \ "device").readNullable[Device] and
    (__ \ "user").readNullable[User] and
    (__ \ "test").readNullable[Int].map(_.getOrElse(0)) and
    (__ \ "at").readNullable[Int].map(_.getOrElse(0)) and
    (__ \ "tmax").readNullable[Int] and
    (__ \ "wseat").readNullable[List[String]] and
    (__ \ "allimps").readNullable[Int].map(_.getOrElse(0)) and
    (__ \ "cur").readNullable[List[String]] and
    (__ \ "bcat").readNullable[List[String]] and
    (__ \ "badv").readNullable[List[String]] and
    (__ \ "regs").readNullable[Regs] and
    (__ \ "ext").readNullable[Ext]
  )(BidRequest.apply _)

}
