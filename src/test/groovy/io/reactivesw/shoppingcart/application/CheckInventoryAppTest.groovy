package io.reactivesw.shoppingcart.application

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException
import io.reactivesw.shoppingcart.infrastructure.grpcservice.SkuGrpcService
import spock.lang.Shared
import spock.lang.Specification

class CheckInventoryAppTest extends Specification {

    @Shared
    long skuId = 1001L

    CheckInventoryApp checkInventoryApp = new CheckInventoryApp()

    SkuGrpcService skuGrpcService = Stub(SkuGrpcService)

    def "get inventory for product"() {
        setup:
        skuGrpcService.getInventoryForSku(_) >> 10
        checkInventoryApp.skuGrpcService = skuGrpcService

        when:
        int inventory = checkInventoryApp.getInventoryBySkuId(skuId)
        then:
        inventory == 10
    }

    def "check inventory for product success"() {
        setup:
        skuGrpcService.getInventoryForSku(_) >> 10
        checkInventoryApp.skuGrpcService = skuGrpcService

        when:
        checkInventoryApp.checkInventory(1001L, 3)
        then:
        noExceptionThrown()
    }

    def "check inventory unavailable"() {
        setup:
        final Status status = Status.NOT_FOUND;
        skuGrpcService.getInventoryForSku(_) >> { throw new StatusRuntimeException(status) }
        checkInventoryApp.skuGrpcService = skuGrpcService

        when:
        checkInventoryApp.checkInventory(skuId, 3)
        then:
        RuntimeException e = thrown()
        assert e.message == ShoppingCartInventoryException.PRODUCT_UNAVAILABLE
    }

    def "check inventory zero"() {
        setup:
        skuGrpcService.getInventoryForSku(_) >> 0
        checkInventoryApp.skuGrpcService = skuGrpcService

        when:
        checkInventoryApp.checkInventory(skuId, 3)
        then:
        RuntimeException e1 = thrown()
        assert e1.message == ShoppingCartInventoryException.ZERO_INVENTORY
    }

    def "check inventory less"() {
        setup:
        skuGrpcService.getInventoryForSku(_) >> 2
        checkInventoryApp.skuGrpcService = skuGrpcService

        when:
        checkInventoryApp.checkInventory(skuId, 3)
        then:
        RuntimeException e2 = thrown()
        assert e2.message == ShoppingCartInventoryException.LESS_INVENTORY
    }
}
