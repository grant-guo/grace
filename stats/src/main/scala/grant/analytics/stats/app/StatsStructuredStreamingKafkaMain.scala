package grant.analytics.stats.app

import grant.analytics.stats.runtime.StatsRuntime
import org.apache.spark.sql.SparkSession

/**
  * Created by grant on 2017-05-25.
  */
object StatsStructuredStreamingKafkaMain extends App{
  val sparkSession = StatsRuntime.injector.getInstance(classOf[SparkSession])

  val topic = "topic1, topic2"
  val brokers = "ip1:9092, ip2:9092"
  sparkSession
    .readStream.format("kafka")
    .option("kafka.bootstrap.servers", "")
    .option("subscribe", "")
//    .option("startingOffsets", """{"topic1":{"0":23,"1":-2},"topic2":{"0":-2}}""")
//    .option("endingOffsets", """{"topic1":{"0":50,"1":-1},"topic2":{"0":-1}}""")
    .option("auto.offset.reset", "largest")
    .option("enable.auto.commit", "false")
    .load()
    .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").as[(String, String)]
}
