package parsers

import scala.util.Success
import scala.util.control.NonFatal

import models.{ParsingResult, BidRequestReader}
import models.bidrequest._
import models.bidrequest.device._

import spray.json._

object SprayJawnReader extends BidRequestReader {
  override def parse(line: String, lastResult: ParsingResult) = {
    jawn.support.spray.Parser.parseFromString(line) match {
      case Success(json) => SprayReader.safeRead(json, lastResult)
      case _ => lastResult.incrCannotParse
    }
  }
}

object SprayNativeReader extends BidRequestReader {
  override def parse(line: String, lastResult: ParsingResult) = {
    try {
      val json: JsValue = line.parseJson
      SprayReader.safeRead(json, lastResult)
    } catch {
      case _: Throwable => lastResult.incrCannotParse
    }
  }
}

object SprayReader {

  @inline def safeRead(json: JsValue, lastResult: ParsingResult): ParsingResult = {
    try {
      BidRequestProtocol.bidRequestFormat.read(json)
      lastResult.incrOk
    } catch {
      case _: Throwable => lastResult.incrCannotUnmarshal
    }
  }

  object BidRequestProtocol extends DefaultJsonProtocol {
    implicit val extFormat = jsonFormat1(Ext)
    implicit val publisherFormat = jsonFormat5(Publisher)
    implicit val producerFormat = jsonFormat5(Producer)
    implicit val contentFormat = jsonFormat20(Content)
    implicit val appFormat = jsonFormat15(App)
    implicit val auctionTypeFormat = jsonFormat1(AuctionType)
    implicit val bannerFormat = jsonFormat15(Banner)
    implicit val segmentFormat = jsonFormat4(Segment)
    implicit val dataFormat = jsonFormat4(Data)
    implicit val dealFormat = jsonFormat7(Deal)
    implicit val geoFormat = jsonFormat11(Geo)
    implicit val nativeFormat = jsonFormat5(Native)
    implicit val pmpFormat = jsonFormat3(Pmp)
    implicit val regsFormat = jsonFormat2(Regs)
    implicit val siteFormat = jsonFormat15(Site)
    implicit val userFormat = jsonFormat9(User)
    implicit val videoFormat = jsonFormat21(Video)
    implicit val ipFormat = jsonFormat2(IP)
    implicit val deviceInfoFormat = jsonFormat3(DeviceInfo)
    implicit val osFormat = jsonFormat2(OS)
    implicit val sizeFormat = jsonFormat2(Size)
    implicit val didFormat = jsonFormat2(DID)
    implicit val dpidFormat = jsonFormat2(DPID)
    implicit val macFormat = jsonFormat2(MAC)

    implicit val deviceFormat = new JsonReader[Device] {
      override def read(json: JsValue): Device = {
        try {
          val fields = json.asJsObject.fields
          Device(
            ua = fields.get("ua").map(_.convertTo[String]),
            geo = fields.get("geo").map(_.convertTo[Geo]),
            dnt = fields.get("dnt").map(_.convertTo[Int]),
            lmt = fields.get("lmt").map(_.convertTo[Int]),
            ip = json.convertTo[IP],
            deviceInfo = json.convertTo[DeviceInfo],
            os = json.convertTo[OS],
            hwv = fields.get("hwv").map(_.convertTo[String]),
            size = json.convertTo[Size],
            did = json.convertTo[DID]
          )
        } catch {
          case NonFatal(ex) => deserializationError("invalid device", ex)
        }
      }
    }

    implicit val impFormat: JsonReader[Imp] = new JsonReader[Imp] {
      override def read(json: JsValue): Imp = {
        try {
          val fields = json.asJsObject.fields
          Imp(
            id = fields("id").convertTo[String],
            banner = fields.get("banner").map(_.convertTo[Banner]),
            video = fields.get("video").map(_.convertTo[Video]),
            native = fields.get("native").map(_.convertTo[Native]),
            displaymanager = fields.get("displaymanager").map(_.convertTo[String]),
            displaymanagerver = fields.get("displaymanagerver").map(_.convertTo[String]),
            instl = fields.get("instl").map(_.convertTo[Int]).getOrElse(0),
            tagid = fields.get("tagid").map(_.convertTo[String]),
            bidfloor = fields.get("bidfloor").map(_.convertTo[Float]).getOrElse(0f),
            bidfloorcur = fields.get("bidfloorcur").map(_.convertTo[String]).getOrElse("USD"),
            secure = fields.get("secure").map(_.convertTo[Int]).contains(1),
            iframebuster = fields.get("iframebuster").map(_.convertTo[List[String]]),
            pmp = fields.get("pmp").map(_.convertTo[Pmp]),
            ext = fields.get("ext").map(_.convertTo[Ext])
          )
        } catch {
          case NonFatal(ex) => deserializationError("invalid imp", ex)
        }
      }
    }

    implicit val bidRequestFormat = new JsonReader[BidRequest] {
      override def read(json: JsValue): BidRequest = {
        try {
          val fields = json.asJsObject.fields
          BidRequest(
            id = fields("id").convertTo[String],
            imp = fields("imp").convertTo[List[JsValue]].map(j => j.convertTo[Imp]),
            site = fields.get("site").map(_.convertTo[Site]),
            app = fields.get("app").map(_.convertTo[App]),
            device = fields.get("device").map(_.convertTo[Device]),
            user = fields.get("user").map(_.convertTo[User]),
            test = fields.get("test").map(_.convertTo[Int]).getOrElse(0),
            at = fields.get("at").map(_.convertTo[Int]).getOrElse(0),
            tmax = fields.get("tmax").map(_.convertTo[Int]),
            wseat = fields.get("wseat").map(_.convertTo[List[String]]),
            allimps = fields.get("allimps").map(_.convertTo[Int]).getOrElse(0),
            cur = fields.get("cur").map(_.convertTo[List[String]]),
            bcat = fields.get("bcat").map(_.convertTo[List[String]]),
            badv = fields.get("badv").map(_.convertTo[List[String]]),
            regs = fields.get("regs").map(_.convertTo[Regs]),
            ext = fields.get("ext").map(_.convertTo[Ext])
          )
        } catch {
          case NonFatal(ex) => deserializationError("invalid bidrequest", ex)
        }
      }
    }

  }


}
