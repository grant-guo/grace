import Dependencies._
import Resolvers.novus

name := "misc"
version := "0.0.1"
libraryDependencies ++= Seq(
  jetty_server,
  jetty_servlet,
  jersey_core,
  jersey_container,
  rxscala,
  typesafe_config_lib,
  google_guice,
  ftp4j,
  google_guava,
  untar,
  untar_logging
)

resolvers ++= Seq(
  novus
)