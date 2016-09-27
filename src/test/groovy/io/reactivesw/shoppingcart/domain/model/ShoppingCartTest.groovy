package io.reactivesw.shoppingcart.domain.model

import spock.lang.Shared
import spock.lang.Specification

class ShoppingCartTest extends Specification {

    @Shared
    long shoppingCartId = 1001L

    @Shared
    long customerId = 1001L

    @Shared
    String sessionId = "test_session_001"

    @Shared
    long skuId = 1001L

    @Shared
    int quantity = 1

    @Shared
    Date createdTime = new Date()

    @Shared
    Date modifiedTime = new Date()

    ShoppingCart sc1 = new ShoppingCart(shoppingCartId: shoppingCartId, customerId: customerId, skuId: skuId, quantity: quantity, createdTime: createdTime, modifiedTime: modifiedTime)
    ShoppingCart sc2 = new ShoppingCart(shoppingCartId: shoppingCartId, customerId: customerId, skuId: skuId, quantity: quantity, createdTime: createdTime, modifiedTime: modifiedTime)
    ShoppingCart sc3 = new ShoppingCart(shoppingCartId: shoppingCartId, customerId: customerId, skuId: 1002L, quantity: quantity, createdTime: createdTime, modifiedTime: modifiedTime)

    def "hash code test"() {
        when:
        int hashValue1 = sc1.hashCode()
        int hashValue2 = sc2.hashCode()
        int hashValue3 = sc3.hashCode()
        int hashCompute = Objects.hash(shoppingCartId, customerId, null, skuId, quantity, createdTime, modifiedTime)
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
