package grant.analytics.stats.conf.impl

import java.net.URL

import grant.analytics.common.conf._
import grant.analytics.common.event.alternative.EventParserNonGeneral
import grant.analytics.stats.conf.StatsConfiguration

/**
 * Created by grant on 2016-11-14.
 */
class DefaultStatsConfiguration(val url:URL) extends StatsConfiguration{
  override def getSparkConfigurations(): SparkConfigurations = ???

  override def getElasticsearchConfigurations(): ElasticsearchConfigurations = ???

  override def getZookeeperConfigurations(): ZookeeperConfigurations = ???

  override def getCassandraConfigurations(): CassandraConfigurations = ???

  override def getMysqlConfigurations(): MysqlConfigurations = ???

  override def getEventParserByEventType(event_type: String): EventParserNonGeneral = ???
}
