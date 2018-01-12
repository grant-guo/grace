package grant.analytics.stats.injector

import com.google.inject.{AbstractModule, Provides}
import org.apache.spark.sql.SparkSession

/**
 * Created by grant on 2016-11-13.
 */
class StatsInjector extends AbstractModule{
  override def configure(): Unit = {

  }

  @Provides
  def createSparkSession():SparkSession = {
    SparkSession.builder().appName("Stats").master("local[4]").getOrCreate()
  }
}

