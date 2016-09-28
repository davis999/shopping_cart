package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException
import spock.lang.Shared
import spock.lang.Specification

class ValidateParamsAppTest extends Specification {

    @Shared
    long customerId = 1001L

    @Shared
    String sessionId = "test_session_001"

    @Shared
    long skuId = 1001L

    @Shared
    int quantity = 1

    ValidateParamsApp validateParamsApp = new ValidateParamsApp()

    ShoppingCart requestSC

    def "validate customer"() {
        when:
        validateParamsApp.validateCustomer(customerId, sessionId)
        validateParamsApp.validateCustomer(0L, sessionId)
        validateParamsApp.validateCustomer(customerId, null)
        then:
        noExceptionThrown()

        when:
        validateParamsApp.validateCustomer(0L, null)
        then:
        RuntimeException e = thrown()
        assert e.message == ShoppingCartParamException.CUSTOMER_REQUIRED
    }

    def "validate quantity"() {
        when:
        validateParamsApp.validateQuantity(quantity)
        then:
        noExceptionThrown()

        when:
        validateParamsApp.validateQuantity(0)
        then:
        RuntimeException e1 = thrown()
        assert e1.message == ShoppingCartParamException.QUANTITY_REQUIRED

        when:
        validateParamsApp.validateQuantity(-1)
        then:
        RuntimeException e2 = thrown()
        assert e2.message == ShoppingCartParamException.QUANTITY_INVALID
    }

    def "validate request parameters"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)

        when:
        validateParamsApp.validateRequestParams(requestSC)
        then:
        noExceptionThrown()

        when:
        requestSC = new ShoppingCart(customerId: customerId, skuId: skuId, quantity: quantity)
        validateParamsApp.validateRequestParams(requestSC)
        then:
        noExceptionThrown()

        when:
        requestSC = new ShoppingCart(sessionId: sessionId, skuId: skuId, quantity: quantity)
        validateParamsApp.validateRequestParams(requestSC)
        then:
        noExceptionThrown()

        when:
        requestSC = new ShoppingCart(skuId: skuId, quantity: quantity)
        validateParamsApp.validateRequestParams(requestSC)
        then:
        RuntimeException e1 = thrown()
        assert e1.message == ShoppingCartParamException.CUSTOMER_REQUIRED

        when:
        requestSC = new ShoppingCart(customerId: customerId, skuId: skuId)
        validateParamsApp.validateRequestParams(requestSC)
        then:
        RuntimeException e2 = thrown()
        assert e2.message == ShoppingCartParamException.QUANTITY_REQUIRED

        when:
        requestSC = new ShoppingCart(customerId: customerId, skuId: skuId, quantity: -1)
        validateParamsApp.validateRequestParams(requestSC)
        then:
        RuntimeException e3 = thrown()
        assert e3.message == ShoppingCartParamException.QUANTITY_INVALID
    }
}
