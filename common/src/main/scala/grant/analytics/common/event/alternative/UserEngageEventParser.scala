package grant.analytics.common.event.alternative

import org.json4s.JsonAST.JValue

/**
 * Created by grant on 2016-11-13.
 */
class UserEngageEventParser extends EventParserNonGeneral{
  override type EVENTTYPE = UserEngageEvent

  override def parse(json: JValue): UserEngageEvent = ???
}
