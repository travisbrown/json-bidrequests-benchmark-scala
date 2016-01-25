package parsers

import io.circe._
import io.circe.generic.auto._
import io.circe.parse._
import io.circe.syntax._

import models.bidrequest._
import models.bidrequest.device._
import models.{BidRequestReader, ParsingResult}

object CirceReader extends BidRequestReader {

  override def parse(line: String, lastResult: ParsingResult): ParsingResult = {
    io.circe.jawn.parse(line).fold(
      _ => lastResult.incrCannotParse,
      json => bidRequestDecoder.decodeJson(json).fold(_ => lastResult.incrCannotUnmarshal, _ => lastResult.incrOk)
    )
  }

  implicit val extDecoder = Decoder[Ext]
  implicit val publisherDecoder = Decoder[Publisher]
  implicit val producerDecoder = Decoder[Producer]
  implicit val contentDecoder = Decoder[Content]
  implicit val appDecoder = Decoder[App]
  implicit val auctionTypeDecoder = Decoder[AuctionType]
  implicit val bannerDecoder = Decoder[Banner]
  implicit val segmentDecoder = Decoder[Segment]
  implicit val dataDecoder = Decoder[Data]
  implicit val dealDecoder = Decoder[Deal]
  implicit val geoDecoder = Decoder[Geo]
  implicit val nativeDecoder = Decoder[Native]
  implicit val pmpDecoder = Decoder[Pmp]
  implicit val regsDecoder = Decoder[Regs]
  implicit val siteDecoder = Decoder[Site]
  implicit val userDecoder = Decoder[User]
  implicit val videoDecoder = Decoder[Video]
  implicit val ipDecoder = Decoder[IP]
  implicit val deviceInfoDecoder = Decoder[DeviceInfo]
  implicit val osDecoder = Decoder[OS]
  implicit val sizeDecoder = Decoder[Size]
  implicit val didDecoder = Decoder[DID]
  implicit val dpidDecoder = Decoder[DPID]
  implicit val macDecoder = Decoder[MAC]

  implicit val deviceDecoder = Decoder.instance( (c: HCursor) =>
    for {
      ua <- c.get[Option[String]]("ua")
      geo <- c.get[Option[Geo]]("geo")
      dnt <- c.get[Option[Int]]("dnt")
      lmt <- c.get[Option[Int]]("lmt")
      ip <- c.as[IP]
      deviceInfo <- c.as[DeviceInfo]
      os <- c.as[OS]
      hwv <- c.get[Option[String]]("hwv")
      size <- c.as[Size]
      did <- c.as[DID]
    } yield Device(ua, geo, dnt, lmt, ip, deviceInfo, os, hwv, size, did)
  )

  implicit val impDecoder = Decoder.instance( (c: HCursor) =>
    for {
      id <- c.get[String]("id")
      banner <- c.get[Option[Banner]]("banner")
      video <- c.get[Option[Video]]("video")
      native <- c.get[Option[Native]]("native")
      displaymanager <- c.get[Option[String]]("displaymanager")
      displaymanagerver <- c.get[Option[String]]("displaymanagerver")
      instl <- c.get[Option[Int]]("instl").map(_.getOrElse(0))
      tagid <- c.get[Option[String]]("tagid")
      bidfloor <- c.get[Option[Float]]("bidfloor").map(_.getOrElse(0f))
      bidfloorcur <- c.get[Option[String]]("bidfloorcur").map(_.getOrElse("USD"))
      secure <- c.get[Option[Int]]("secure").map(_.contains(1))
      iframebuster <- c.get[Option[List[String]]]("iframebuster")
      pmp <- c.get[Option[Pmp]]("pmp")
      ext <- c.get[Option[Ext]]("ext")
    } yield Imp(id, banner, video, native, displaymanager, displaymanagerver, instl, tagid, bidfloor, bidfloorcur, secure, iframebuster, pmp, ext)
  )

  implicit val bidRequestDecoder = Decoder.instance( (c: HCursor) =>
    for {
      id <- c.get[String]("id")
      imp <- c.get[List[Imp]]("imp")
      site <- c.get[Option[Site]]("site")
      app <- c.get[Option[App]]("app")
      device <- c.get[Option[Device]]("device")
      user <- c.get[Option[User]]("user")
      test <- c.get[Option[Int]]("test").map(_.getOrElse(0))
      at <- c.get[Option[Int]]("at").map(_.getOrElse(0))
      tmax <- c.get[Option[Int]]("tmax")
      wseat <- c.get[Option[List[String]]]("wseat")
      allimps <- c.get[Option[Int]]("allimps").map(_.getOrElse(0))
      cur <- c.get[Option[List[String]]]("cur")
      bcat <- c.get[Option[List[String]]]("bcat")
      badv <- c.get[Option[List[String]]]("badv")
      regs <- c.get[Option[Regs]]("regs")
      ext <- c.get[Option[Ext]]("ext")
    } yield BidRequest(id, imp, site, app, device, user, test, at, tmax, wseat, allimps, cur, bcat, badv, regs, ext)
  )

}
