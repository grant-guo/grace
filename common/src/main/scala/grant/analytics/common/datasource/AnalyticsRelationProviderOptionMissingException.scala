package grant.analytics.common.datasource

case class AnalyticsRelationProviderOptionMissingException(val keys: Seq[String]) extends Exception("The options are missing: " + keys)
