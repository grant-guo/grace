package grant.analytics.stats.app

import grant.analytics.stats.runtime.StatsRuntime
import org.apache.spark.sql.{ForeachWriter, SparkSession}
import org.apache.spark.sql.streaming.OutputMode
import org.json4s.JsonAST.{JNothing, JNull}
import org.json4s.jackson.JsonMethods


/**
  * Created by grant on 2017-05-25.
  */
object StatsStructuredStreamingMain extends App{
  val sparkSession = StatsRuntime.injector.getInstance(classOf[SparkSession])

  val path = "/Users/grant/work/data/content-events-gzip/2016-12-12/00" // here only directory path is accepted

  import sparkSession.implicits._

  val query =
  sparkSession.readStream.textFile(path).mapPartitions(iterator => {
    iterator.map(line => {
      val jvalue = JsonMethods.parse(line)
      (jvalue \ "event_uuid") match {
        case JNothing | JNull => "00000000-0000-0000-0000-000000000000"
        case value => value.values.toString
      }
    })
  }).writeStream.outputMode(OutputMode.Append()).foreach(new ForeachWriter[String] {
    override def process(value: String): Unit = {
      println(value)
    }

    override def close(errorOrNull: Throwable): Unit = {

    }

    override def open(partitionId: Long, version: Long): Boolean = {
      true
    }
  }).start()

  query.awaitTermination()

}

