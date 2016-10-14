package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku
import spock.lang.Shared
import spock.lang.Specification

class GetSkuInfoListAppTest extends Specification {

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

    GetSkuInfoListApp getSkuInfoListApp = new GetSkuInfoListApp()

    GetSkuInfoApp getSkuInfoApp = Stub(GetSkuInfoApp)
    ShoppingCart findSC1 = new ShoppingCart(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId, quantity: quantity)
    ShoppingCart findSC3 = new ShoppingCart(shoppingCartId: 1002L, customerId: customerId, skuId: 1002L, quantity: quantity)
    ShoppingCartSku scProd1 = new ShoppingCartSku(skuId: skuId, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)
    ShoppingCartSku scProd2 = new ShoppingCartSku(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId,
            quantity: quantity, skuNumber: skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price)
    List<ShoppingCart> scList = new ArrayList<>()
    List<ShoppingCartSku> skuList = new ArrayList<>()
    List<ShoppingCartSku> infoList = new ArrayList<>()

    def "get sku id list"() {
        setup:
        scList.add(findSC1)
        scList.add(findSC3)
        when:
        List<Long> skuIdList = getSkuInfoListApp.getSkuIdList(scList)
        then:
        skuIdList == [1001L, 1002L]
    }

    def "get shopping cart sku info list"() {
        setup:
        scList.add(findSC1)
        skuList.add(scProd1)
        infoList.add(scProd2)
        getSkuInfoApp.organizeShoppingCartSkuList(_, _) >> infoList
        getSkuInfoListApp.getSkuInfoApp = getSkuInfoApp

        when:
        List<ShoppingCartSku> rList = getSkuInfoListApp.getShoppingCartSkuInfoList(scList)
        then:
        rList == infoList
    }

    def "get shopping cart sku info list null"() {
        setup:
        List<ShoppingCartSku> scList = new ArrayList<>();

        when:
        List<ShoppingCartSku> rList = getSkuInfoListApp.getShoppingCartSkuInfoList(scList)
        then:
        rList.size() == 0
    }

}
