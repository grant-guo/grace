package grant.analytics.common.event.alternative

import grant.analytics.common.event.AnalyticsEventParser

/**
  * Created by grant on 2016-11-28.
  */
trait EventParserNonGeneral extends AnalyticsEventParser{
  override type EVENTTYPE <: AnalyticsEvent
}
