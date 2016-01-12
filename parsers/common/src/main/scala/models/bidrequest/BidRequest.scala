package models.bidrequest

import models.bidrequest.device.Device

case class BidRequest(id: String, imp: List[Imp], site: Option[Site], app: Option[App],
                      device: Option[Device], user: Option[User], test: Int = 0, at: Int = 2,
                      tmax: Option[Int], wseat: Option[List[String]], allimps: Int = 0,
                      cur: Option[List[String]], bcat: Option[List[String]], badv: Option[List[String]],
                      regs: Option[Regs], ext: Option[Ext])

