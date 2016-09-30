package io.reactivesw.shoppingcart.application.grpc

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku
import io.reactivesw.shoppingcart.grpc.AddRequest
import io.reactivesw.shoppingcart.grpc.GrpcShoppingCartSku
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

    ShoppingCart requestSC
    ShoppingCartSku scSku

    def setup() {
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        scSku = new ShoppingCartSku(shoppingCartId: shppingCartId, customerId: customerId, sessionId: sessionId, skuId: skuId,
                quantity: quantity, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)
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
        GrpcShoppingCartSku grpcSC = GrpcShoppingCartSku.newBuilder().
                setShoppingCartId(shppingCartId).
                setCustomerId(customerId).
                setSessionId(sessionId).
                setSkuId(skuId).
                setSkuNumber(skuNumber).
                setSkuName(skuName).
                setMediaUrl(mediaUrl).
                setPrice(price).
                setQuantity(quantity).build()

        when:
        GrpcShoppingCartSku grpcReply = ScGrpcStream.shoppingCartToGrpcReply(scSku)
        then:
        grpcReply == grpcSC
    }
}
