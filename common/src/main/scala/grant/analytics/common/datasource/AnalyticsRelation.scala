package grant.analytics.common.datasource

import grant.analytics.common.model.Document
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, DataFrameReader, Row, SQLContext}
import org.apache.spark.sql.sources.{BaseRelation, TableScan}
import org.apache.spark.sql.types.StructType

class AnalyticsRelation(override val sqlContext: SQLContext, override val schema: StructType, val path:String) extends BaseRelation with TableScan{

  override def buildScan(): RDD[Row] = {
    sqlContext.read.parquet(path).rdd
  }
}


object AnalyticsRelation {
  def apply(sc: SQLContext, schema: StructType, path: String): AnalyticsRelation = {
    new AnalyticsRelation(sc, schema, path)
  }

  // with this implicit class, one could call spark.reader.analytics(...)
  implicit class AnalyticsRelationWrapper(reader: DataFrameReader) {
    def analytics(path:String): DataFrame = {
      reader.format("analytics").load(path)
    }
  }
}