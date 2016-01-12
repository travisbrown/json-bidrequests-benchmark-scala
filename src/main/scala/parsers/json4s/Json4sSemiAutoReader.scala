package parsers.json4s

import org.json4s.{DefaultFormats, MappingException, JValue}

import parsers.BidRequestParser
import models._
import models.bidrequest._
import models.bidrequest.device._

object Json4sSemiAutoReader extends BidRequestParser {
  override def parse(index: Int, line: String, lastResult: ParsingResult) = {
    try {
      val json: JValue = org.json4s.jackson.JsonMethods.parse(line)
      val bid = read(json)
      lastResult.incrOk
    } catch {
      case ex: MappingException => lastResult.incrCannotUnmarshal
      case _ => lastResult.incrCannotParse
    }
  }

  implicit val formats = DefaultFormats

  def read(json: JValue) = {
    BidRequest(
      id = (json \ "id").extract[String],
      imp = (json \ "imp").extract[Seq[JValue]].map { imp =>
        Imp(
          id = (imp \ "id").extract[String],
          banner = (imp \ "banner").extractOpt[Banner],
          video = (imp \ "video").extractOpt[Video],
          native = (imp \ "native").extractOpt[Native],
          displaymanager = (imp \ "displaymanager").extractOpt[String],
          displaymanagerver = (imp \ "displaymanagerver").extractOpt[String],
          instl = (imp \ "instl").extractOpt[Int].getOrElse(0),
          tagid = (imp \ "tagid").extractOpt[String],
          bidfloor = (imp \ "bidfloor").extractOpt[Float].getOrElse(0f),
          bidfloorcur = (imp \ "bidfloorcur").extractOpt[String].getOrElse("USD"),
          secure = (imp \ "secure").extractOpt[Int].contains(1),
          iframebuster = (imp \ "iframebuster").extractOpt[Seq[String]],
          pmp = (imp \ "pmp").extractOpt[Pmp],
          ext = (imp \ "ext").extractOpt[Ext]
        )
      },
      site = (json \ "site").extractOpt[Site],
      app = (json \ "app").extractOpt[App],
      device = (json \ "device").extractOpt[JValue].map { dev =>
        Device(
          ua = (dev \ "ua").extractOpt[String],
          geo = (dev \ "geo").extractOpt[Geo],
          dnt = (dev \ "dnt").extractOpt[Int],
          lmt = (dev \ "lmt").extractOpt[Int],
          ip = dev.extract[IP],
          deviceInfo = dev.extract[DeviceInfo],
          os = dev.extract[OS],
          hwv = (dev \ "hwv").extractOpt[String],
          size = dev.extract[Size],
          did = dev.extract[DID]
        )
      },
      user = (json \ "user").extractOpt[User],
      test = (json \ "test").extractOpt[Int].getOrElse(0),
      at = (json \ "at").extractOpt[Int].getOrElse(0),
      tmax = (json \ "tmax").extractOpt[Int],
      wseat = (json \ "wseat").extractOpt[Seq[String]],
      allimps = (json \ "allimps").extractOpt[Int].getOrElse(0),
      cur = (json \ "cur").extractOpt[Seq[String]],
      bcat = (json \ "bcat").extractOpt[Seq[String]],
      badv = (json \ "badv").extractOpt[Seq[String]],
      regs = (json \ "regs").extractOpt[Regs],
      ext = (json \ "ext").extractOpt[Ext]
    )
  }

}

