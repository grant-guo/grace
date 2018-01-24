package grant.analytics.grpc.server

import grant.analytics.grpc.protoc.metadata.{FileIDList, MetadataServiceGrpc, SampleMappingRequest, SampleMappingResponse}

import scala.concurrent.Future

object GrpcServer extends App {

  private class MetadataServiceImpl extends MetadataServiceGrpc.MetadataService {
    override def getSampleMapping(request: SampleMappingRequest): Future[SampleMappingResponse] = {
      Future.successful(
        SampleMappingResponse(
          Map("a" -> FileIDList(Seq("a1", "a2")), "b" -> FileIDList(Seq("b1", "b2")))
        )
      )
    }
  }

}
