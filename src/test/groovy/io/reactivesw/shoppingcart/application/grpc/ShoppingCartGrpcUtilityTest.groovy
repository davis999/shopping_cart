package io.reactivesw.shoppingcart.application.grpc

import io.grpc.stub.StreamObserver
import io.reactivesw.shoppingcart.grpc.AddReply
import spock.lang.Specification

class ShoppingCartGrpcUtilityTest extends Specification {

    StreamObserver<AddReply> responseObserver = Mock()

    def "response to grpc client"() {
        setup:
        AddReply addReply = AddReply.newBuilder().build()
        when:
        ShoppingCartGrpcUtility.completeResponse(responseObserver, addReply)
        then:
        noExceptionThrown()
    }
}
