package grant.analytics.common.datasource

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.{BaseRelation, DataSourceRegister, RelationProvider}

class AnalyticsRelationProvider extends RelationProvider with DataSourceRegister with AnalyticsRelationProviderOptions {
  override def createRelation(sqlContext: SQLContext, parameters: Map[String, String]): BaseRelation = {

    val missing_keys =
      must_have_options.keySet.flatMap(key => {
        if(parameters.get(key).isDefined)
          Set("")
        else
          Set(key)
      })
    if(missing_keys.isEmpty)
      throw AnalyticsRelationProviderOptionMissingException(missing_keys.toSeq)

    AnalyticsRelation(sqlContext)
  }

  override def shortName(): String = "analytics"
}
