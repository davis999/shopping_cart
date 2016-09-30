package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.application.grpc.SkuGrpcClient
import io.reactivesw.shoppingcart.application.grpc.config.SkuGrpcConfig
import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku
import spock.lang.Shared
import spock.lang.Specification

class GetSkuInfoAppTest extends Specification {

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

    GetSkuInfoApp getSkuInfoApp = new GetSkuInfoApp()

    SkuGrpcConfig skuGrpcConfig = Stub(SkuGrpcConfig)
    SkuGrpcClient skuGrpcClient = Stub(SkuGrpcClient)
    ShoppingCart findSC1 = new ShoppingCart(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId, quantity: quantity)
    ShoppingCart findSC2 = new ShoppingCart(shoppingCartId: shppingCartId, sessionId: sessionId, skuId: skuId, quantity: quantity)
    ShoppingCart findSC3 = new ShoppingCart(shoppingCartId: 1002L, customerId: customerId, skuId: 1002L, quantity: quantity)
    ShoppingCartSku scProd1 = new ShoppingCartSku(skuId: skuId, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)
    ShoppingCartSku scProd3 = new ShoppingCartSku(skuId: 1002L, skuNumber: "test_sku_number_002", skuName: "test_sku_name_b",
            mediaUrl: "http://sample.com/test_002.jpg", price: price)
    ShoppingCartSku scProd2 = new ShoppingCartSku(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId,
            quantity: quantity, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)
    ShoppingCartSku scProd4 = new ShoppingCartSku(shoppingCartId: 1002L, customerId: customerId, skuId: 1002L,
            quantity: quantity, skuNumber: "test_sku_number_002", skuName: "test_sku_name_b", mediaUrl: "http://sample.com/test_002.jpg", price: price)
    ShoppingCartSku scProd5 = new ShoppingCartSku(shoppingCartId: shppingCartId, sessionId: sessionId, skuId: skuId,
            quantity: quantity, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)
    List<ShoppingCartSku> skuList = new ArrayList<>()

    def "get sku info"() {
        setup:
        skuGrpcClient.getSkuInfo(_) >> scProd1
        skuGrpcConfig.skuGrpcClient() >> skuGrpcClient
        getSkuInfoApp.skuGrpcConfig = skuGrpcConfig

        when:
        ShoppingCartSku scProdGet = getSkuInfoApp.getSkuInfo(skuId)
        then:
        scProdGet == scProd1
    }

    def "get sku info list"() {
        List<Long> skuIdList = new ArrayList<>()
        skuIdList.add(skuId)
        skuList.add(scProd1)
        skuGrpcClient.getSkuInfoList(_) >> skuList
        skuGrpcConfig.skuGrpcClient() >> skuGrpcClient
        getSkuInfoApp.skuGrpcConfig = skuGrpcConfig

        when:
        List<ShoppingCartSku> rList = getSkuInfoApp.getSkuInfoList(skuIdList)
        then:
        rList == skuList
    }

    def "organize shopping cart sku info for customer"() {
        when:
        ShoppingCartSku scSku = getSkuInfoApp.organizeShoppingCartSku(findSC1, scProd1)
        then:
        scSku == scProd2
    }

    def "organize shopping cart sku info for session"() {
        when:
        ShoppingCartSku scSku = getSkuInfoApp.organizeShoppingCartSku(findSC2, scProd1)
        then:
        scSku == scProd5
    }

    def "organize shopping cart sku info list"() {
        setup:
        List<ShoppingCart> scList = new ArrayList<>()
        List<ShoppingCartSku> skuList = new ArrayList<>()
        List<ShoppingCartSku> infoList = new ArrayList<>()
        scList.add(findSC1)
        scList.add(findSC3)
        skuList.add(scProd1)
        skuList.add(scProd3)
        infoList.add(scProd2)
        infoList.add(scProd4)
        when:
        List<ShoppingCartSku> list = getSkuInfoApp.organizeShoppingCartSkuList(scList, skuList)
        then:
        list == infoList
    }

}
