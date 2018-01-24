import Dependencies.{grpc_netty, grpc_protobuf, grpc_stub, scalapb_grpc, grpc_netty_scalapb}

name := "grpc"
version := "0.0.1"
libraryDependencies ++= Seq(
//  grpc_stub,
//  grpc_netty,
//  grpc_protobuf,
  grpc_netty_scalapb,
  scalapb_grpc
)
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)