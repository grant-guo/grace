package grant.analytics.grpc.client

import grant.analytics.grpc.protoc.metadata.{MetadataServiceGrpc, SampleMappingRequest}
import io.grpc.ManagedChannelBuilder

object GrpcClient extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  val server = "localhost"
  val port = 8080

  val request = SampleMappingRequest()

  val channel = ManagedChannelBuilder.forAddress(server, port).usePlaintext(true).build()

  // blocking call
  val blockingStub = MetadataServiceGrpc.blockingStub(channel)
  val reply1 = blockingStub.getSampleMapping(request)
  println(reply1)

  //async call
  val asyncStub = MetadataServiceGrpc.stub(channel)
  val reply2 = asyncStub.getSampleMapping(request)
  reply2 onComplete println

}
