package io.reactivesw.shoppingcart.domain.model

import spock.lang.Shared
import spock.lang.Specification

class ShoppingCartSkuTest extends Specification {

    @Shared
    long shoppingCartId = 1001L

    @Shared
    long customerId = 1001L

    @Shared
    String sessionId = "test_session_001"

    @Shared
    long skuId = 1001L

    String skuNumber = "sku_no_001"
    String skuName = "product001"
    String mediaUrl = "/img/sku001.jpg"
    String price = "19.9"

    @Shared
    int quantity = 1

    ShoppingCartSku sc1 = new ShoppingCartSku(shoppingCartId: shoppingCartId, customerId: customerId, skuId: skuId, skuNumber:skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price, quantity: quantity)
    ShoppingCartSku sc2 = new ShoppingCartSku(shoppingCartId: shoppingCartId, customerId: customerId, skuId: skuId, skuNumber:skuNumber, skuName: skuName, mediaUrl: mediaUrl, price: price, quantity: quantity)
    ShoppingCartSku sc3 = new ShoppingCartSku(shoppingCartId: shoppingCartId, customerId: customerId, skuId: 1002L, skuNumber:"sku_no_002", skuName: "product002", mediaUrl: "/img/sku002.jpg", price: "29.9", quantity: quantity)

    def "hash code test"() {
        when:
        int hashValue1 = sc1.hashCode()
        int hashValue2 = sc2.hashCode()
        int hashValue3 = sc3.hashCode()
        int hashCompute = Objects.hash(shoppingCartId, customerId, null, skuId, skuNumber, skuName, mediaUrl, price, quantity)
        then:
        hashValue1 == hashCompute
        hashValue1 == hashValue2
        hashValue1 != hashValue3
    }

    def "equals test"() {
        expect:
        sc1.equals(sc2)
        !sc1.equals(sc3)
    }
}
