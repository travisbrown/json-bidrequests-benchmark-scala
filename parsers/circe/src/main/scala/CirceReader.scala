package parsers

import io.circe._
import io.circe.generic.semiauto._

import models.bidrequest._
import models.bidrequest.device._
import models.{BidRequestReader, ParsingResult}

object CirceReader extends BidRequestReader {

  override def parse(line: String, lastResult: ParsingResult): ParsingResult =
    io.circe.jawn.decode[BidRequest](line) match {
      case cats.data.Xor.Left(ParsingFailure(_, _)) => lastResult.incrCannotParse
      case cats.data.Xor.Left(DecodingFailure(_, _)) => lastResult.incrCannotUnmarshal
      case cats.data.Xor.Right(_) => lastResult.incrOk
    }

  /**
   * The following `deriveDecoder` definitions aren't strictly necessary: it's
   * possible to change the `semiauto` import above to `auto` and have the
   * generic derivation happen completely automatically for these types. In this
   * case fully-automatic derivation has a large impact on compile time (~66s
   * vs. 5s), so we use `semiauto`.
   */
  implicit val extDecoder: Decoder[Ext] = deriveDecoder
  implicit val publisherDecoder: Decoder[Publisher] = deriveDecoder
  implicit val producerDecoder: Decoder[Producer] = deriveDecoder
  implicit val contentDecoder: Decoder[Content] = deriveDecoder
  implicit val appDecoder: Decoder[App] = deriveDecoder
  implicit val auctionTypeDecoder: Decoder[AuctionType] = deriveDecoder
  implicit val bannerDecoder: Decoder[Banner] = deriveDecoder
  implicit val segmentDecoder: Decoder[Segment] = deriveDecoder
  implicit val dataDecoder: Decoder[Data] = deriveDecoder
  implicit val dealDecoder: Decoder[Deal] = deriveDecoder
  implicit val geoDecoder: Decoder[Geo] = deriveDecoder
  implicit val nativeDecoder: Decoder[Native] = deriveDecoder
  implicit val pmpDecoder: Decoder[Pmp] = deriveDecoder
  implicit val regsDecoder: Decoder[Regs] = deriveDecoder
  implicit val siteDecoder: Decoder[Site] = deriveDecoder
  implicit val userDecoder: Decoder[User] = deriveDecoder
  implicit val videoDecoder: Decoder[Video] = deriveDecoder
  implicit val ipDecoder: Decoder[IP] = deriveDecoder
  implicit val deviceInfoDecoder: Decoder[DeviceInfo] = deriveDecoder
  implicit val osDecoder: Decoder[OS] = deriveDecoder
  implicit val sizeDecoder: Decoder[Size] = deriveDecoder
  implicit val didDecoder: Decoder[DID] = deriveDecoder
  implicit val dpidDecoder: Decoder[DPID] = deriveDecoder
  implicit val macDecoder: Decoder[MAC] = deriveDecoder

  implicit val deviceDecoder: Decoder[Device] = Decoder.instance( (c: HCursor) =>
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

  implicit val impDecoder: Decoder[Imp] = Decoder.instance( (c: HCursor) =>
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

  implicit val bidRequestDecoder: Decoder[BidRequest] = Decoder.instance( (c: HCursor) =>
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
