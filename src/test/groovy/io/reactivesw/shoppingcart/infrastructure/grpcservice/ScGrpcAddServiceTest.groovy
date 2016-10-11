package io.reactivesw.shoppingcart.infrastructure.grpcservice

import io.grpc.stub.StreamObserver
import io.reactivesw.shoppingcart.application.AddItemApp
import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku
import io.reactivesw.shoppingcart.grpc.AddReply
import io.reactivesw.shoppingcart.grpc.AddRequest
import io.reactivesw.shoppingcart.grpc.GrpcShoppingCartSku
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartLimitException
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException
import spock.lang.Shared
import spock.lang.Specification

class ScGrpcAddServiceTest extends Specification {

    @Shared
    long customerId = 1001L

    @Shared
    String sessionId = "test_session_001"

    @Shared
    long skuId = 1001L

    @Shared
    int quantity = 1

    @Shared
    long shppingCartId = 1001L

    @Shared
    String skuNumber = "test_sku_number_001"

    @Shared
    String skuName = "test_sku_name_a"

    @Shared
    String mediaUrl = "http://sample.com/test_001.jpg"

    @Shared
    String price = "42.00"

    ScGrpcAddService scGrpc = new ScGrpcAddService()
    AddRequest request
    GrpcShoppingCartSku grpcShoppingCartSku = new GrpcShoppingCartSku()
    StreamObserver<AddReply> responseObserver = Mock()

    ShoppingCart requestSC
    ShoppingCartSku scSku
    AddItemApp addItemApp = Stub(AddItemApp)

    def setup() {
        request = AddRequest.newBuilder().
                setCustomerId(customerId).
                setSessionId(sessionId).
                setSkuId(skuId).
                setQuantity(quantity).build()
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        scSku = new ShoppingCartSku(shoppingCartId: shppingCartId, customerId: customerId, sessionId: sessionId, skuId: skuId,
                quantity: quantity, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)

        ScGrpcStream.grpcRequestToShoppingCart(_) >> requestSC
        ScGrpcStream.shoppingCartToGrpcReply(_) >> grpcShoppingCartSku
        scGrpc.addItemApp = addItemApp
    }

    def "add to shopping cart"() {
        setup:
        requestSC.setShoppingCartId(1001L)
        addItemApp.addToShoppingCart(_) >> scSku
        when:
        scGrpc.addToShoppingCart(request, responseObserver)
        then:
        noExceptionThrown()
    }

    def "add throw invalid paramter exception"() {
        setup:
        addItemApp.addToShoppingCart(_) >> { throw new ShoppingCartParamException() }
        scGrpc.addItemApp = addItemApp
        when:
        scGrpc.addToShoppingCart(request, responseObserver)
        then:
        RuntimeException e = thrown()
        assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
    }

    def "add throw out of range exception"() {
        setup:
        addItemApp.addToShoppingCart(_) >> { throw new ShoppingCartLimitException() }
        scGrpc.addItemApp = addItemApp
        when:
        scGrpc.addToShoppingCart(request, responseObserver)
        then:
        RuntimeException e = thrown()
        assert e.message == "OUT_OF_RANGE: quantity is out of range"
    }

    def "add throw resource exhausted exception"() {
        setup:
        addItemApp.addToShoppingCart(_) >> { throw new ShoppingCartInventoryException() }
        scGrpc.addItemApp = addItemApp
        when:
        scGrpc.addToShoppingCart(request, responseObserver)
        then:
        RuntimeException e = thrown()
        assert e.message == "RESOURCE_EXHAUSTED: inventory resource is exhausted"
    }
}
