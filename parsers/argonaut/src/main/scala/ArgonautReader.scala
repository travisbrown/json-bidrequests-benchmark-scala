package parsers

import argonaut.Argonaut._
import argonaut._

import models._
import models.bidrequest._
import models.bidrequest.device._

object ArgonautReader extends BidRequestParser {
  override def parse(index: Int, line: String, lastResult: ParsingResult) = {
    line.parse.fold(
      err => lastResult.incrCannotParse,
      json => json.as[BidRequest].fold(
        { case (errs, _) => lastResult.incrCannotUnmarshal },
        _ => lastResult.incrOk
      )
    )
  }

  implicit val extDecoder = jdecode1L(Ext.apply)("sessiondepth")
  implicit val publisherDecoder = jdecode5L(Publisher.apply)("id", "name", "cat", "domain", "ext")
  implicit val producerDecoder = jdecode5L(Producer.apply)("id", "name", "cat", "domain", "ext")
  implicit val contentDecoder = jdecode20L(Content.apply)("id", "episode", "title", "series", "season", "producer", "url", "cat", "videoquality", "context", "contentrating", "userrating", "qagmediarating", "keywords", "livestream", "sourcerelationship", "len", "language", "embeddable", "ext")
  implicit val appDecoder = jdecode15L(App.apply)("id", "name", "bundle", "domain", "storeurl", "cat", "sectioncat", "pagecat", "ver", "privacypolicy", "paid", "publisher", "content", "keywords", "ext")
  implicit val auctionTypeDecoder = jdecode1L(AuctionType.apply)("id")
  implicit val bannerDecoder = jdecode15L(Banner.apply)("w", "h", "wmax", "hmax", "wmin","hmin", "id", "btype","battr", "pos", "mimes","topframe", "expdir", "api", "ext")
  implicit val segmentDecoder = jdecode4L(Segment.apply)("id", "name", "value", "ext")
  implicit val dataDecoder = jdecode4L(Data.apply)("id", "name", "segment", "ext")
  implicit val dealDecoder = jdecode7L(Deal.apply)("id", "bidfloor", "bidfloorcur", "at", "wseat", "wadomain", "ext")
  implicit val geoDecoder = jdecode11L(Geo.apply)("lat", "lon", "type", "country", "region", "regionfips", "metro", "city", "zip", "utcoffset", "ext")
  implicit val nativeDecoder = jdecode5L(Native.apply)("request", "ver", "api", "battr", "ext")
  implicit val pmpDecoder = jdecode3L(Pmp.apply)("private_aunction", "deals", "ext")
  implicit val regsDecoder = jdecode2L(Regs.apply)("coppa", "ext")
  implicit val siteDecoder = jdecode15L(Site.apply)("id", "name", "domain", "cat","sectioncat", "pagecat", "page", "ref", "search", "mobile", "privacypolicy", "publisher", "content", "keywords", "ext")
  implicit val userDecoder = jdecode9L(User.apply)("id", "buyerid", "yop", "gender","keywords", "customdata", "geo", "data", "ext")
  implicit val videoDecoder = jdecode21L(Video.apply)("mimes", "minduration", "maxduration", "protocols", "w", "h", "startdelay", "linearity", "sequence", "battr", "maxextended", "minbitrate", "maxbitrate", "boxingallowed", "playbackmethod", "delivery", "pos", "companionad", "api", "companiontype", "ext")
  implicit val ipDecoder = jdecode2L(IP.apply)("ip", "ipv6")
  implicit val deviceInfoDecoder = jdecode3L(DeviceInfo.apply)("devicetype", "make", "model")
  implicit val osDecoder = jdecode2L(OS.apply)("os", "osv")
  implicit val sizeDecoder = jdecode2L(Size.apply)("h", "w")
  implicit val didDecoder = jdecode2L(DID.apply)("didsha1", "didmd5")
  implicit val dpidDecoder = jdecode2L(DPID.apply)("dpidsha1", "dpidmd5")
  implicit val macDecoder = jdecode2L(MAC.apply)("macsha1", "macmd5")

  implicit val deviceDecoder = DecodeJson( (c: HCursor) =>
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


  implicit val impDecoder = DecodeJson( (c: HCursor) =>
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

  implicit val bidRequestDecoder = DecodeJson((c: HCursor) =>
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
