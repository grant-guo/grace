import Dependencies.{default_dependencies_seq, elasticsearch, elasticsearch_spark}

name := "es_aggr"
version := "0.0.1"
libraryDependencies ++= (default_dependencies_seq ++ Seq(
  elasticsearch,
  elasticsearch_spark,
  "commons-cli" % "commons-cli" % "1.3.1"
))