package models.bidrequest.device

import models.bidrequest.Geo

// Simplified version !!!
case class Device(ua: Option[String], geo: Option[Geo], dnt: Option[Int], lmt: Option[Int],
                  ip: IP, deviceInfo: DeviceInfo, os: OS,
                  hwv: Option[String], size: Size, did: DID)
