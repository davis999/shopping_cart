package io.reactivesw.shoppingcart.application

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.reactivesw.shoppingcart.application.grpc.SkuGrpcClient
import io.reactivesw.shoppingcart.application.grpc.config.GrpcConfig
import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.service.ShoppingCartConfigService
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartLimitException
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException

import spock.lang.Shared
import spock.lang.Specification

class AddProductToShoppingCartTest extends Specification {

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

    AddToShoppingCartApp addToShoppingCartApp = new AddToShoppingCartApp()

    ShoppingCart requestSC
    ShoppingCartService shoppingCartService = Stub(ShoppingCartService)
    ShoppingCartConfigService shoppingCartConfigService = Stub(ShoppingCartConfigService)
    GrpcConfig grpcConfig = Stub(GrpcConfig)
    SkuGrpcClient skuGrpcClient = Mock(SkuGrpcClient)

    def "validate customer"() {
        when:
        addToShoppingCartApp.validateCustomer(customerId, sessionId)
        addToShoppingCartApp.validateCustomer(0L, sessionId)
        addToShoppingCartApp.validateCustomer(customerId, null)
        then:
        noExceptionThrown()

        when:
        addToShoppingCartApp.validateCustomer(0L, null)
        then:
        RuntimeException e = thrown()
        assert e.message == ShoppingCartParamException.CUSTOMER_REQUIRED
    }

    def "validate quantity"() {
        when:
        addToShoppingCartApp.validateQuantity(quantity)
        then:
        noExceptionThrown()

        when:
        addToShoppingCartApp.validateQuantity(0)
        then:
        RuntimeException e1 = thrown()
        assert e1.message == ShoppingCartParamException.QUANTITY_REQUIRED

        when:
        addToShoppingCartApp.validateQuantity(-1)
        then:
        RuntimeException e2 = thrown()
        assert e2.message == ShoppingCartParamException.QUANTITY_INVALID
    }

    def "validate request parameters"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)

        when:
        addToShoppingCartApp.validateRequestParams(requestSC)
        then:
        noExceptionThrown()

        when:
        requestSC = new ShoppingCart(customerId: customerId, skuId: skuId, quantity: quantity)
        addToShoppingCartApp.validateRequestParams(requestSC)
        then:
        noExceptionThrown()

        when:
        requestSC = new ShoppingCart(sessionId: sessionId, skuId: skuId, quantity: quantity)
        addToShoppingCartApp.validateRequestParams(requestSC)
        then:
        noExceptionThrown()

        when:
        requestSC = new ShoppingCart(skuId: skuId, quantity: quantity)
        addToShoppingCartApp.validateRequestParams(requestSC)
        then:
        RuntimeException e1 = thrown()
        assert e1.message == ShoppingCartParamException.CUSTOMER_REQUIRED

        when:
        requestSC = new ShoppingCart(customerId: customerId, skuId: skuId)
        addToShoppingCartApp.validateRequestParams(requestSC)
        then:
        RuntimeException e2 = thrown()
        assert e2.message == ShoppingCartParamException.QUANTITY_REQUIRED

        when:
        requestSC = new ShoppingCart(customerId: customerId, skuId: skuId, quantity: -1)
        addToShoppingCartApp.validateRequestParams(requestSC)
        then:
        RuntimeException e3 = thrown()
        assert e3.message == ShoppingCartParamException.QUANTITY_INVALID
    }

    def "merge existed quantity not existed"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        shoppingCartService.findOneBySkuIdForCustomer(_) >> null
        addToShoppingCartApp.shoppingCartService = shoppingCartService

        when:
        ShoppingCart shoppingCartMerged1 = addToShoppingCartApp.mergeExistedQuantity(requestSC)
        then:
        shoppingCartMerged1 == requestSC
    }

    def "merge existed quantity existed"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        ShoppingCart shoppingCartExisted = new ShoppingCart(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId, quantity: 1)
        shoppingCartService.findOneBySkuIdForCustomer(_) >> shoppingCartExisted
        addToShoppingCartApp.shoppingCartService = shoppingCartService

        when:
        ShoppingCart shoppingCartMerged = addToShoppingCartApp.mergeExistedQuantity(requestSC)
        then:
        shoppingCartMerged.getQuantity() == quantity + 1
        shoppingCartMerged.getShoppingCartId() == shppingCartId
    }

    def "check quantity unlimited"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        shoppingCartConfigService.getTotalQuantityLimit() >> 0
        shoppingCartService.getTotalQuantityForCustomer(_) >> quantity
        addToShoppingCartApp.shoppingCartService = shoppingCartService
        addToShoppingCartApp.shoppingCartConfigService = shoppingCartConfigService

        when:
        addToShoppingCartApp.checkQuantityLimit(requestSC)
        then:
        noExceptionThrown()
    }

    def "check quantity limit success"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        shoppingCartConfigService.getTotalQuantityLimit() >> 30
        shoppingCartService.getTotalQuantityForCustomer(_) >> quantity
        addToShoppingCartApp.shoppingCartService = shoppingCartService
        addToShoppingCartApp.shoppingCartConfigService = shoppingCartConfigService

        when:
        addToShoppingCartApp.checkQuantityLimit(requestSC)
        then:
        noExceptionThrown()
    }

    def "check quantity limit failed"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        shoppingCartConfigService.getTotalQuantityLimit() >> 5
        shoppingCartService.getTotalQuantityForCustomer(_) >> 10
        addToShoppingCartApp.shoppingCartService = shoppingCartService
        addToShoppingCartApp.shoppingCartConfigService = shoppingCartConfigService

        when:
        addToShoppingCartApp.checkQuantityLimit(requestSC)
        then:
        RuntimeException e = thrown()
        assert e.message == ShoppingCartLimitException.TOTAL_LIMIT
    }

    def "get inventory for product"() {
        setup:
        skuGrpcClient.getInventoryForSku(_) >> 10
        grpcConfig.skuGrpcClient() >> skuGrpcClient
        addToShoppingCartApp.grpcConfig = grpcConfig

        when:
        int inventory = addToShoppingCartApp.getInventoryBySkuId(skuId)
        then:
        inventory == 10
    }

    def "check inventory for product success"() {
        setup:
        skuGrpcClient.getInventoryForSku(_) >> 10
        grpcConfig.skuGrpcClient() >> skuGrpcClient
        addToShoppingCartApp.grpcConfig = grpcConfig

        when:
        addToShoppingCartApp.checkInventory(1001L, 3)
        then:
        noExceptionThrown()
    }

    def "check inventory unavailable"() {
        setup:
        final Status status = Status.NOT_FOUND;
        skuGrpcClient.getInventoryForSku(_) >> { throw new StatusRuntimeException(status) }
        grpcConfig.skuGrpcClient() >> skuGrpcClient
        addToShoppingCartApp.grpcConfig = grpcConfig

        when:
        addToShoppingCartApp.checkInventory(skuId, 3)
        then:
        RuntimeException e = thrown()
        assert e.message == ShoppingCartInventoryException.PRODUCT_UNAVAILABLE
    }

    def "check inventory zero"() {
        setup:
        skuGrpcClient.getInventoryForSku(_) >> 0
        grpcConfig.skuGrpcClient() >> skuGrpcClient
        addToShoppingCartApp.grpcConfig = grpcConfig

        when:
        addToShoppingCartApp.checkInventory(skuId, 3)
        then:
        RuntimeException e1 = thrown()
        assert e1.message == ShoppingCartInventoryException.ZERO_INVENTORY
    }

    def "check inventory less"() {
        setup:
        skuGrpcClient.getInventoryForSku(_) >> 2
        grpcConfig.skuGrpcClient() >> skuGrpcClient
        addToShoppingCartApp.grpcConfig = grpcConfig

        when:
        addToShoppingCartApp.checkInventory(skuId, 3)
        then:
        RuntimeException e2 = thrown()
        assert e2.message == ShoppingCartInventoryException.LESS_INVENTORY
    }

    def "add to shopping cart"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        ShoppingCart shoppingCartForSaveMock = new ShoppingCart(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId, quantity: 1)
        shoppingCartConfigService.getTotalQuantityLimit() >> 0
        shoppingCartService.getTotalQuantityForCustomer(_) >> quantity
        shoppingCartService.save(_) >> shoppingCartForSaveMock
        skuGrpcClient.getInventoryForSku(_) >> 10
        grpcConfig.skuGrpcClient() >> skuGrpcClient
        addToShoppingCartApp.grpcConfig = grpcConfig
        addToShoppingCartApp.shoppingCartService = shoppingCartService
        addToShoppingCartApp.shoppingCartConfigService = shoppingCartConfigService

        when:
        ShoppingCart shoppingCartSaved = addToShoppingCartApp.addToShoppingCart(requestSC)
        then:
        shoppingCartSaved == shoppingCartForSaveMock
    }
}
