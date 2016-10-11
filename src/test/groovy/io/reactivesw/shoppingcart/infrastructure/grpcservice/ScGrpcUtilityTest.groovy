package io.reactivesw.shoppingcart.infrastructure.grpcservice

import io.grpc.stub.StreamObserver
import io.reactivesw.shoppingcart.grpc.ShoppingCartReply
import spock.lang.Specification

class ScGrpcUtilityTest extends Specification {

    StreamObserver<ShoppingCartReply> responseObserver = Mock()

    def "response to grpc client"() {
        setup:
        ShoppingCartReply addReply = ShoppingCartReply.newBuilder().build()
        when:
        ScGrpcUtility.completeResponse(responseObserver, addReply)
        then:
        noExceptionThrown()
    }
}
