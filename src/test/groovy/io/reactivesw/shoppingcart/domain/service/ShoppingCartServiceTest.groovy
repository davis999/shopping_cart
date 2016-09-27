package io.reactivesw.shoppingcart.domain.service

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.infrastructure.persistence.ShoppingCartRepository

import spock.lang.Shared
import spock.lang.Specification

class ShoppingCartServiceTest extends Specification {

    @Shared
    long customerId = 1001L

    @Shared
    String sessionId = "test_session_001"

    @Shared
    long skuId = 1001L

    @Shared
    int quantity = 1

    ShoppingCartService shoppingCartService = new ShoppingCartService()
    ShoppingCartRepository shoppingCartRepo = Mock()

    ShoppingCart savedSC1 = new ShoppingCart(shoppingCartId: 1001L, customerId: customerId, skuId: skuId, quantity: quantity, createdTime: new Date(), modifiedTime: new Date())
    ShoppingCart savedSC2 = new ShoppingCart(shoppingCartId: 1001L, sessionId: sessionId, skuId: skuId, quantity: quantity, createdTime: new Date(), modifiedTime: new Date())

    def "save a customer shopping cart record"() {
        setup:
        ShoppingCart shoppingCartCustomer = new ShoppingCart()
        shoppingCartCustomer.setCustomerId(customerId)
        shoppingCartCustomer.setSkuId(skuId)
        shoppingCartCustomer.setQuantity(quantity)

        shoppingCartRepo.save(_) >> savedSC1
        shoppingCartService.shoppingCartRepository = shoppingCartRepo

        when: "save to shopping cart"
        ShoppingCart shoppingCartCustomerSaved = shoppingCartService.save(shoppingCartCustomer)

        then: "save success"
        shoppingCartCustomerSaved == savedSC1
    }

    def "save existed customer shopping cart record"() {
        setup:
        ShoppingCart shoppingCartCustomer = new ShoppingCart()
        shoppingCartCustomer.setCustomerId(customerId)
        shoppingCartCustomer.setSkuId(skuId)
        shoppingCartCustomer.setQuantity(quantity)
        shoppingCartCustomer.setShoppingCartId(1001L)

        shoppingCartRepo.save(_) >> savedSC1
        shoppingCartService.shoppingCartRepository = shoppingCartRepo

        when: "save to shopping cart"
        ShoppingCart shoppingCartCustomerSaved = shoppingCartService.save(shoppingCartCustomer)

        then: "save success"
        shoppingCartCustomerSaved == savedSC1
    }

    def "save a session shopping cart record"() {
        setup:
        ShoppingCart shoppingCartSession = new ShoppingCart()
        shoppingCartSession.setSessionId(sessionId)
        shoppingCartSession.setSkuId(skuId)
        shoppingCartSession.setQuantity(quantity)

        shoppingCartRepo.save(_) >> savedSC2
        shoppingCartService.shoppingCartRepository = shoppingCartRepo

        when: "save to shopping cart"
        ShoppingCart shoppingCartSessionSaved = shoppingCartService.save(shoppingCartSession)

        then: "save success"
        shoppingCartSessionSaved == savedSC2
    }

    def "find a record for customer"() {
        setup:
        ShoppingCart requestSC1 = new ShoppingCart(customerId: customerId, skuId: skuId)
        ShoppingCart requestSC2 = new ShoppingCart(sessionId: sessionId, skuId: skuId)

        shoppingCartRepo.findOneByCustomerIdAndSkuId(_, _) >> savedSC1
        shoppingCartRepo.findOneBySessionIdAndSkuId(_, _) >> savedSC2
        shoppingCartService.shoppingCartRepository = shoppingCartRepo

        when: "find a customer and sku specified shopping cart"
        ShoppingCart shoppingCartFound1 = shoppingCartService.findOneBySkuIdForCustomer(requestSC1)
        ShoppingCart shoppingCartFound2 = shoppingCartService.findOneBySkuIdForCustomer(requestSC2)
        then: "find success"
        shoppingCartFound1 == savedSC1
        shoppingCartFound2 == savedSC2
    }

    def "find a record by null params"() {
        setup:
        ShoppingCart requestSC1 = new ShoppingCart(skuId: skuId)
        ShoppingCart requestSC2 = new ShoppingCart(customerId: customerId)
        ShoppingCart requestSC3 = new ShoppingCart(sessionId: sessionId)

        shoppingCartRepo.findOneByCustomerIdAndSkuId(_, _) >> null
        shoppingCartRepo.findOneBySessionIdAndSkuId(_, _) >> null
        shoppingCartService.shoppingCartRepository = shoppingCartRepo

        when: "find a null customer and sku specified shopping cart"
        ShoppingCart shoppingCartFound1 = shoppingCartService.findOneBySkuIdForCustomer(requestSC1)
        ShoppingCart shoppingCartFound2 = shoppingCartService.findOneBySkuIdForCustomer(requestSC2)
        ShoppingCart shoppingCartFound3 = shoppingCartService.findOneBySkuIdForCustomer(requestSC3)
        then: "find failed"
        shoppingCartFound1 == null
        shoppingCartFound2 == null
        shoppingCartFound3 == null
    }

    def "delete a record for customer"() {
        setup:
        ShoppingCart requestSC1 = new ShoppingCart(customerId: customerId, skuId: skuId)
        ShoppingCart requestSC2 = new ShoppingCart(sessionId: sessionId, skuId: skuId)

        shoppingCartRepo.deleteByCustomerIdAndSkuId(_, _) >> 1L
        shoppingCartRepo.deleteBySessionIdAndSkuId(_, _) >> 1L
        shoppingCartService.shoppingCartRepository = shoppingCartRepo

        when: "delete a customer and sku specified shopping cart"
        long deleteNum1 = shoppingCartService.deleteBySkuIdForCustomer(requestSC1)
        long deleteNum2 = shoppingCartService.deleteBySkuIdForCustomer(requestSC2)
        then: "delete success"
        deleteNum1 == 1
        deleteNum2 == 1
    }

    def "delete a record by null params"() {
        setup:
        ShoppingCart requestSC1 = new ShoppingCart(skuId: skuId)
        ShoppingCart requestSC2 = new ShoppingCart(customerId: customerId)
        ShoppingCart requestSC3 = new ShoppingCart(sessionId: sessionId)

        shoppingCartRepo.deleteByCustomerIdAndSkuId(_, _) >> -1L
        shoppingCartRepo.deleteBySessionIdAndSkuId(_, _) >> -1L
        shoppingCartService.shoppingCartRepository = shoppingCartRepo

        when: "delete a customer and null sku specified shopping cart"
        long deleteNum1 = shoppingCartService.deleteBySkuIdForCustomer(requestSC1)
        long deleteNum2 = shoppingCartService.deleteBySkuIdForCustomer(requestSC2)
        long deleteNum3 = shoppingCartService.deleteBySkuIdForCustomer(requestSC3)
        then: "delete failed"
        deleteNum1 == -1
        deleteNum2 == -1
        deleteNum3 == -1
    }

    def "get total quantity of shopping cart"() {
        setup:
        ShoppingCart requestSC1 = new ShoppingCart(customerId: customerId, skuId: skuId, quantity: quantity)
        ShoppingCart requestSC2 = new ShoppingCart(sessionId: sessionId, skuId: skuId, quantity: quantity)
        ShoppingCart savedSC3 = new ShoppingCart(shoppingCartId: 1003L, customerId: customerId, skuId: 1002L, quantity: quantity, createdTime: new Date(), modifiedTime: new Date())
        ShoppingCart savedSC4 = new ShoppingCart(shoppingCartId: 1004L, sessionId: sessionId, skuId: 1002L, quantity: quantity, createdTime: new Date(), modifiedTime: new Date())

        def cartList1 = [savedSC1, savedSC3]
        def cartList2 = [savedSC2, savedSC4]
        shoppingCartRepo.findByCustomerId(_) >> cartList1
        shoppingCartRepo.findBySessionId(_) >> cartList2
        shoppingCartService.shoppingCartRepository = shoppingCartRepo

        when:
        int total1 = shoppingCartService.getTotalQuantityForCustomer(requestSC1)
        int total2 = shoppingCartService.getTotalQuantityForCustomer(requestSC2)
        then:
        total1 == 2
        total2 == 2
    }

    def "find shopping cart by customer or session"() {
        setup:
        def cartList1 = [savedSC1]
        def cartList2 = [savedSC2]
        shoppingCartRepo.findByCustomerId(_) >> cartList1
        shoppingCartRepo.findBySessionId(_) >> cartList2
        shoppingCartService.shoppingCartRepository = shoppingCartRepo

        ShoppingCart requestSC1 = new ShoppingCart(customerId: customerId)
        ShoppingCart requestSC2 = new ShoppingCart(sessionId: sessionId)

        when: "customer and session not null"
        List shoppingCartListCust = shoppingCartService.listShoppingCartForCustomer(requestSC1)
        List shoppingCartListSess = shoppingCartService.listShoppingCartForCustomer(requestSC2)
        then:
        shoppingCartListCust == cartList1
        shoppingCartListSess == cartList2
    }

    def "find shopping cart by null"() {
        when: "customer and session null"
        List shoppingCartListCust = shoppingCartService.listShoppingCartForCustomer(new ShoppingCart())
        then: "null returen"
        shoppingCartListCust.size() == 0
    }
}
