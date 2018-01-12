package grant.analytics.common.event.alternative

import org.json4s.JsonAST.{JNothing, JValue}

import scala.collection.immutable.HashMap

/**
  * Created by grant on 2017-01-03.
  */
class DefaultUserEventParserContainer extends UserEventParserContainer with Serializable{

  private lazy val cache = createCache()

  private def createCache():HashMap[String, EventParserNonGeneral] = {
    HashMap(
      "analytics.view" -> new UserViewEventParser,
      "analytics.engage" -> new UserEngageEventParser
    )
  }

  override def getParser(event_type: String): EventParserNonGeneral = {
    cache.get(event_type) match {
      case Some(parser) => parser
      case None => new GeneralContentEventParser
    }
  }

  override def getParser(json: JValue): EventParserNonGeneral = {
    getParser(
      (json \ "event_type") match {
        case JNothing => throw new Exception("No event type attribute!")
        case value => value.values.toString
      }
    )
  }

}

class GeneralContentEventParser extends EventParserNonGeneral{
  override type EVENTTYPE = GeneralUserContentEvent

  override def parse(event: JValue): GeneralUserContentEvent = ???

}