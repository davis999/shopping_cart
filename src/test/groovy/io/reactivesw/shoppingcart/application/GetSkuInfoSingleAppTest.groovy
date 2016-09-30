package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.application.grpc.SkuGrpcClient
import io.reactivesw.shoppingcart.application.grpc.config.SkuGrpcConfig
import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku
import spock.lang.Shared
import spock.lang.Specification

class GetSkuInfoSingleAppTest extends Specification {

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

    GetSkuInfoSingleApp getSkuInfoSingleApp = new GetSkuInfoSingleApp()

    GetSkuInfoApp getSkuInfoApp = Stub(GetSkuInfoApp)
    ShoppingCart findSC1 = new ShoppingCart(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId, quantity: quantity)
    ShoppingCartSku scProd1 = new ShoppingCartSku(skuId: skuId, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)
    ShoppingCartSku scProd2 = new ShoppingCartSku(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId,
            quantity: quantity, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)

    def "get shopping cart sku info"() {
        setup:
        getSkuInfoApp.getSkuInfo(_) >> scProd1
        getSkuInfoApp.organizeShoppingCartSku(_, _) >> scProd2
        getSkuInfoSingleApp.getSkuInfoApp = getSkuInfoApp

        when:
        ShoppingCartSku scSku = getSkuInfoSingleApp.getShoppingCartSkuInfo(findSC1)
        then:
        scSku == scProd2
    }

}
