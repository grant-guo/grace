import Dependencies.{grpc_netty, grpc_protobuf, grpc_stub, scala_pb_grpc}

name := "grpc"
version := "0.0.1"
libraryDependencies ++= Seq(
  grpc_stub,
  grpc_netty,
  grpc_protobuf,
  scala_pb_grpc
)
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)