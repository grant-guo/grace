package grant.analytics.common.datasource

trait AnalyticsRelationProviderOptions {
  val must_have_options: Map[String, String] = Map()
  val other_options: Map[String, String] = Map()
}
