package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.application.grpc.SkuGrpcClient
import io.reactivesw.shoppingcart.application.grpc.config.SkuGrpcConfig
import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.model.ShoppingCartProduct
import spock.lang.Shared
import spock.lang.Specification

class ListSkuInfoAppTest extends Specification {

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

    ListSkuInfoApp listSkuInfoApp = new ListSkuInfoApp()

    SkuGrpcConfig skuGrpcConfig = Stub(SkuGrpcConfig)
    SkuGrpcClient skuGrpcClient = Stub(SkuGrpcClient)
    ShoppingCart findSC1 = new ShoppingCart(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId, quantity: quantity)
    ShoppingCart findSC3 = new ShoppingCart(shoppingCartId: 1002L, customerId: customerId, skuId: 1002L, quantity: quantity)
    ShoppingCart findSC2 = new ShoppingCart(shoppingCartId: shppingCartId, sessionId: sessionId, skuId: skuId, quantity: quantity)
    ShoppingCartProduct scProd1 = new ShoppingCartProduct(skuId: skuId, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)
    ShoppingCartProduct scProd3 = new ShoppingCartProduct(skuId: 1002L, skuNumber: "test_sku_number_002", skuName: "test_sku_name_b",
            mediaUrl: "http://sample.com/test_002.jpg", price: price)
    ShoppingCartProduct scProd2 = new ShoppingCartProduct(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId,
            quantity: quantity, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)
    ShoppingCartProduct scProd4 = new ShoppingCartProduct(shoppingCartId: 1002L, customerId: customerId, skuId: 1002L,
            quantity: quantity, skuNumber: "test_sku_number_002", skuName: "test_sku_name_b", mediaUrl: "http://sample.com/test_002.jpg", price: price)
    ShoppingCartProduct scProd5 = new ShoppingCartProduct(shoppingCartId: shppingCartId, sessionId: sessionId, skuId: skuId,
            quantity: quantity, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)
    List<ShoppingCart> scList = new ArrayList<>()
    List<ShoppingCartProduct> skuList = new ArrayList<>()
    List<ShoppingCartProduct> infoList = new ArrayList<>()

    def "list shopping cart sku info for customer"() {
        setup:
        scList.add(findSC1)
        skuList.add(scProd1)
        infoList.add(scProd2)
        skuGrpcClient.getSkuInfoList(_) >> skuList
        skuGrpcConfig.skuGrpcClient() >> skuGrpcClient
        listSkuInfoApp.skuGrpcConfig = skuGrpcConfig

        when:
        List<ShoppingCartProduct> rList = listSkuInfoApp.listShoppingCartProductInfo(scList)
        then:
        rList == infoList
    }

    def "list shopping cart sku info for session"() {
        setup:
        scList.add(findSC2)
        skuList.add(scProd1)
        infoList.add(scProd5)
        skuGrpcClient.getSkuInfoList(_) >> skuList
        skuGrpcConfig.skuGrpcClient() >> skuGrpcClient
        listSkuInfoApp.skuGrpcConfig = skuGrpcConfig

        when:
        List<ShoppingCartProduct> rList = listSkuInfoApp.listShoppingCartProductInfo(scList)
        then:
        rList == infoList
    }

    def "list shopping cart sku info for customer multi product"() {
        setup:
        scList.add(findSC1)
        scList.add(findSC3)
        skuList.add(scProd1)
        skuList.add(scProd3)
        infoList.add(scProd2)
        infoList.add(scProd4)
        skuGrpcClient.getSkuInfoList(_) >> skuList
        skuGrpcConfig.skuGrpcClient() >> skuGrpcClient
        listSkuInfoApp.skuGrpcConfig = skuGrpcConfig

        when:
        List<ShoppingCartProduct> rList = listSkuInfoApp.listShoppingCartProductInfo(scList)
        then:
        rList == infoList
    }

}
