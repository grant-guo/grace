import Dependencies._

name := "embedded"
version := "0.0.1"
libraryDependencies ++= (default_dependencies_seq ++ Seq(
  cassandra,
  elasticsearch,
  hsqldb,
  kafka_0_10_x,
  log4j2,
  elasticsearch_transport,
  elasticsearch_plugin_transport_netty3
))