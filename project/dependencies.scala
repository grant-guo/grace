
import sbt._

object Dependencies {
  val scala_main_version = "2.11"
  val spark_version = "2.1.1"
  val kafka_version = "0-10"

  val scala_compiler = "org.scala-lang" % "scala-compiler" % "2.11.11"

  val spark_core = ("org.apache.spark" % s"spark-core_${scala_main_version}" % spark_version)
    .exclude("org.mortbay.jetty", "servlet-api")
    .exclude("commons-beanutils", "commons-beanutils-core")
    .exclude("commons-collections", "commons-collections")
    .exclude("commons-logging", "commons-logging")
    .exclude("com.esotericsoftware.minlog", "minlog")

  val spark_streaming = "org.apache.spark" % s"spark-streaming_${scala_main_version}" % spark_version

  val spark_streaming_kafka =   ("org.apache.spark" % s"spark-streaming-kafka-${kafka_version}_${scala_main_version}" % spark_version)
    .exclude("org.spark-project.spark", "unused")

  val spark_sql = ("org.apache.spark" % s"spark-sql_${scala_main_version}" % spark_version)

  val spark_cassandra_connector = ("com.datastax.spark" % s"spark-cassandra-connector_${scala_main_version}" % "2.0.3")
    .exclude("com.codahale.metrics", "metrics-core")
    .exclude("io.netty", "netty-handler")
    .exclude("io.netty", "netty-buffer")
    .exclude("io.netty", "netty-common")
    .exclude("io.netty", "netty-transport")
    .exclude("io.netty", "netty-codec")

  val typesafe_config_lib = "com.typesafe" % "config" % "1.3.2"

  val google_guice = ("com.google.inject" % "guice" % "4.1.0")
    .exclude("com.google.guava", "guava")
    .exclude("stax", "stax-api")
  val google_guava = "com.google.guava" % "guava" % "23.2-jre"

  val aspectj = "org.aspectj" % "aspectjrt" % "1.8.9"
  val hadoop_aws = ("org.apache.hadoop" % "hadoop-aws" % "2.7.3")
    .exclude("org.apache.hadoop", "hadoop-common")
  val scalatest = "org.scalatest" % "scalatest_2.11" % "2.2.6"
  val junit = "junit" % "junit" % "4.12"
  val elasticsearch_spark = "org.elasticsearch" % "elasticsearch-spark-20_2.11" % "5.2.2"
  val elasticsearch =  "org.elasticsearch" % "elasticsearch" % "5.2.2"
  val elasticsearch_transport = "org.elasticsearch.client" % "transport" % "5.2.2"
//  val elasticsearch_plugin_transport_netty4 = "org.elasticsearch.plugin" % "transport-netty4-client" % "5.2.2"
  val elasticsearch_plugin_transport_netty3 = "org.elasticsearch.plugin" % "transport-netty3-client" % "5.2.2"

  val cassandra = ("org.apache.cassandra" % "cassandra-all" % "3.5").exclude("org.slf4j", "log4j-over-slf4j").exclude("org.slf4j", "slf4j-api")

  val mysql_connector_java = "mysql" % "mysql-connector-java" % "5.1.17"
  val mysql_connector_mxj =  "mysql" % "mysql-connector-mxj" % "5.0.12"
  val mysql_connector_mxj_db_file = "mysql" % "mysql-connector-mxj-db-files" % "5.0.12"
  val twitter_algebird = "com.twitter" % "algebird-core_2.11" % "0.11.0"

  val jmeter_http = "org.apache.jmeter" % "ApacheJMeter_http" % "3.1"
  val typesafe_config = "com.typesafe" % "config" % "1.3.1"

  val hsqldb =  "org.hsqldb" % "hsqldb" % "2.3.4"
  val kafka_0_10_x = "org.apache.kafka" % "kafka_2.11" % "0.10.2.1"
  val log4j2 = "org.apache.logging.log4j" % "log4j-api" % "2.8.1"
  val akka_http = "com.typesafe.akka" % "akka-http_2.11" % "10.0.5"
  val rx_scala =  "io.reactivex" % "rxscala_2.11" % "0.26.5"

  val jetty_server = "org.eclipse.jetty" % "jetty-server" % "9.4.3.v20170317"
  val jetty_servlet = "org.eclipse.jetty" % "jetty-servlet" % "9.4.3.v20170317"
  val jersey_core =  "org.glassfish.jersey.core" % "jersey-server" % "2.25.1"
  val jersey_container = "org.glassfish.jersey.containers" % "jersey-container-servlet-core" % "2.25.1"

  val grpc_netty = "io.grpc" % "grpc-netty" % "1.5.0"
  val grpc_stub = "io.grpc" % "grpc-stub" % "1.5.0"
  val grpc_protobuf = "io.grpc" % "grpc-protobuf" % "1.5.0"

  val scala_pb = "com.trueaccord.scalapb" %% "compilerplugin" % "0.6.2"
  val scalapb_grpc = "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % com.trueaccord.scalapb.compiler.Version.scalapbVersion
  val grpc_netty_scalapb = "io.grpc" % "grpc-netty" % com.trueaccord.scalapb.compiler.Version.grpcJavaVersion

  val rxjava2 = "io.reactivex.rxjava2" % "rxjava" % "2.1.5"
  val rxscala = "io.reactivex" % "rxscala_2.11" % "0.26.5"

  val untar = "org.codehaus.plexus" % "plexus-archiver" % "3.5"
  val untar_logging = "org.codehaus.plexus" % "plexus-slf4j-logging" % "1.1"

  val ftp4j = "it.sauronsoftware" % "ftp4j" % "1.6"

  val default_dependencies_seq = Seq(
    (spark_core).exclude("net.java.dev.jets3t", "jets3t") ,//% Provided,
    spark_cassandra_connector,
    spark_streaming % Provided ,
    spark_streaming_kafka,
    spark_sql,
    typesafe_config_lib,
    google_guice,
    aspectj,
    twitter_algebird,
    hadoop_aws,
    scalatest % Test,
    junit  % Test
  )

}