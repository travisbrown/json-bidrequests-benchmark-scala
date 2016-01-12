package models.bidrequest

import models.bidrequest.device.Device
import scalaz.NonEmptyList

case class BidRequest(id: String, imp: Seq[Imp], site: Option[Site], app: Option[App],
                      device: Option[Device], user: Option[User], test: Int = 0, at: Int = 2,
                      tmax: Option[Int], wseat: Option[Seq[String]], allimps: Int = 0,
                      cur: Option[Seq[String]], bcat: Option[Seq[String]], badv: Option[Seq[String]],
                      regs: Option[Regs], ext: Option[Ext])

