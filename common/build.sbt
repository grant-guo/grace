import Dependencies.{default_dependencies_seq, scala_compiler}

name := "common"

version:="0.0.1"

libraryDependencies ++= (default_dependencies_seq ++ Seq(scala_compiler))


PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)