package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.service.ShoppingCartConfigService
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartLimitException
import spock.lang.Shared
import spock.lang.Specification

class CheckConfigLimitAppTest extends Specification {

    @Shared
    long customerId = 1001L

    @Shared
    String sessionId = "test_session_001"

    @Shared
    long skuId = 1001L

    @Shared
    int quantity = 1

    CheckConfigLimitApp checkConfigLimitApp = new CheckConfigLimitApp()

    ShoppingCart requestSC
    ShoppingCartService shoppingCartService = Stub(ShoppingCartService)
    ShoppingCartConfigService shoppingCartConfigService = Stub(ShoppingCartConfigService)

    def "check quantity unlimited"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        shoppingCartConfigService.getTotalQuantityLimit() >> 0
        shoppingCartService.getTotalQuantityForCustomer(_) >> quantity
        checkConfigLimitApp.shoppingCartService = shoppingCartService
        checkConfigLimitApp.shoppingCartConfigService = shoppingCartConfigService

        when:
        checkConfigLimitApp.checkQuantityLimit(requestSC)
        then:
        noExceptionThrown()
    }

    def "check quantity limit success"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        shoppingCartConfigService.getTotalQuantityLimit() >> 30
        shoppingCartService.getTotalQuantityForCustomer(_) >> quantity
        checkConfigLimitApp.shoppingCartService = shoppingCartService
        checkConfigLimitApp.shoppingCartConfigService = shoppingCartConfigService

        when:
        checkConfigLimitApp.checkQuantityLimit(requestSC)
        then:
        noExceptionThrown()
    }

    def "check quantity limit failed"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        shoppingCartConfigService.getTotalQuantityLimit() >> 5
        shoppingCartService.getTotalQuantityForCustomer(_) >> 10
        checkConfigLimitApp.shoppingCartService = shoppingCartService
        checkConfigLimitApp.shoppingCartConfigService = shoppingCartConfigService

        when:
        checkConfigLimitApp.checkQuantityLimit(requestSC)
        then:
        RuntimeException e = thrown()
        assert e.message == ShoppingCartLimitException.TOTAL_LIMIT
    }

}
