package io.reactivesw.shoppingcart.application.grpc

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.grpc.AddRequest
import io.reactivesw.shoppingcart.grpc.GrpcShoppingCart
import spock.lang.Shared
import spock.lang.Specification

class ScGrpcStreamTest extends Specification {

    @Shared
    long customerId = 1001L

    @Shared
    String sessionId = "test_session_001"

    @Shared
    long skuId = 1001L

    @Shared
    int quantity = 1

    ShoppingCart requestSC

    def setup() {
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
    }

    def "grpc request to shopping cart"() {
        setup:
        AddRequest request = AddRequest.newBuilder().
                setCustomerId(customerId).
                setSessionId(sessionId).
                setSkuId(skuId).
                setQuantity(quantity).build()

        when:
        ShoppingCart shoppingCart = ScGrpcStream.grpcRequestToShoppingCart(request)
        then:
        shoppingCart == requestSC
    }

    def "shopping cart to grpc reply"() {
        setup:
        GrpcShoppingCart grpcSC = GrpcShoppingCart.newBuilder().
                setCustomerId(customerId).
                setSessionId(sessionId).
                setSkuId(skuId).
                setQuantity(quantity).build()

        when:
        GrpcShoppingCart grpcReply = ScGrpcStream.shoppingCartToGrpcReply(requestSC)
        then:
        grpcReply == grpcSC
    }
}
