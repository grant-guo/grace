import Dependencies.{default_dependencies_seq, jmeter_http}

name := "rest-performance"
version := "0.0.1"
libraryDependencies ++= (default_dependencies_seq ++ Seq(
  jmeter_http
))