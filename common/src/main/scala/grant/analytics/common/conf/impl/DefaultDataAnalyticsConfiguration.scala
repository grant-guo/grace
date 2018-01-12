package grant.analytics.common.conf.impl

import grant.analytics.common.conf._
import grant.analytics.common.conf.parser.DataAnalyticsConfigurationParser
import grant.analytics.common.event.alternative.EventParserNonGeneral

/**
  * Created by grant on 2017-03-02.
  */
class DefaultDataAnalyticsConfiguration(parser: DataAnalyticsConfigurationParser) extends AnalyticsConfiguration{
  override def getSparkConfigurations(): SparkConfigurations = {
    parser.getSparkConfigurations()
  }

  override def getElasticsearchConfigurations(): ElasticsearchConfigurations = {
    parser.getElasticsearchConfigurations()
  }

  override def getZookeeperConfigurations(): ZookeeperConfigurations = ???

  override def getCassandraConfigurations(): CassandraConfigurations = ???

  override def getMysqlConfigurations(): MysqlConfigurations = ???

  override def getEventParserByEventType(event_type: String): EventParserNonGeneral = ???

}
