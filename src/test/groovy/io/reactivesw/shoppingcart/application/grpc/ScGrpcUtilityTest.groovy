package io.reactivesw.shoppingcart.application.grpc

import io.grpc.stub.StreamObserver
import io.reactivesw.shoppingcart.grpc.AddReply
import spock.lang.Specification

class ScGrpcUtilityTest extends Specification {

    StreamObserver<AddReply> responseObserver = Mock()

    def "response to grpc client"() {
        setup:
        AddReply addReply = AddReply.newBuilder().build()
        when:
        ScGrpcUtility.completeResponse(responseObserver, addReply)
        then:
        noExceptionThrown()
    }
}
