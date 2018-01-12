import Dependencies.{default_dependencies_seq, scala_compiler}

name := "stats"
version := "0.0.1"
libraryDependencies ++= (default_dependencies_seq ++ Seq(scala_compiler))