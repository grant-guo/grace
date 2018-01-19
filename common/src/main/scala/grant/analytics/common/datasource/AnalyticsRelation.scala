package grant.analytics.common.datasource

import grant.analytics.common.model.Document
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.sql.sources.{BaseRelation, TableScan}
import org.apache.spark.sql.types.StructType

class AnalyticsRelation(override val sqlContext: SQLContext) extends BaseRelation with TableScan{

  override val schema: StructType = parseDescriptor(Document.scalaDescriptor)

  override def buildScan(): RDD[Row] = ???
}


object AnalyticsRelation {
  def apply(sc: SQLContext): AnalyticsRelation = {
    new AnalyticsRelation(sc)
  }
}