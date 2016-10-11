package io.reactivesw.shoppingcart.application

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException
import io.reactivesw.shoppingcart.infrastructure.grpcservice.SkuGrpcClient
import io.reactivesw.shoppingcart.infrastructure.grpcservice.config.SkuGrpcConfig
import spock.lang.Shared
import spock.lang.Specification

class CheckInventoryAppTest extends Specification {

    @Shared
    long skuId = 1001L

    CheckInventoryApp checkInventoryApp = new CheckInventoryApp()

    SkuGrpcConfig skuGrpcConfig = Stub(SkuGrpcConfig)
    SkuGrpcClient skuGrpcClient = Stub(SkuGrpcClient)

    def "get inventory for product"() {
        setup:
        skuGrpcClient.getInventoryForSku(_) >> 10
        skuGrpcConfig.skuGrpcClient() >> skuGrpcClient
        checkInventoryApp.skuGrpcConfig = skuGrpcConfig

        when:
        int inventory = checkInventoryApp.getInventoryBySkuId(skuId)
        then:
        inventory == 10
    }

    def "check inventory for product success"() {
        setup:
        skuGrpcClient.getInventoryForSku(_) >> 10
        skuGrpcConfig.skuGrpcClient() >> skuGrpcClient
        checkInventoryApp.skuGrpcConfig = skuGrpcConfig

        when:
        checkInventoryApp.checkInventory(1001L, 3)
        then:
        noExceptionThrown()
    }

    def "check inventory unavailable"() {
        setup:
        final Status status = Status.NOT_FOUND;
        skuGrpcClient.getInventoryForSku(_) >> { throw new StatusRuntimeException(status) }
        skuGrpcConfig.skuGrpcClient() >> skuGrpcClient
        checkInventoryApp.skuGrpcConfig = skuGrpcConfig

        when:
        checkInventoryApp.checkInventory(skuId, 3)
        then:
        RuntimeException e = thrown()
        assert e.message == ShoppingCartInventoryException.PRODUCT_UNAVAILABLE
    }

    def "check inventory zero"() {
        setup:
        skuGrpcClient.getInventoryForSku(_) >> 0
        skuGrpcConfig.skuGrpcClient() >> skuGrpcClient
        checkInventoryApp.skuGrpcConfig = skuGrpcConfig

        when:
        checkInventoryApp.checkInventory(skuId, 3)
        then:
        RuntimeException e1 = thrown()
        assert e1.message == ShoppingCartInventoryException.ZERO_INVENTORY
    }

    def "check inventory less"() {
        setup:
        skuGrpcClient.getInventoryForSku(_) >> 2
        skuGrpcConfig.skuGrpcClient() >> skuGrpcClient
        checkInventoryApp.skuGrpcConfig = skuGrpcConfig

        when:
        checkInventoryApp.checkInventory(skuId, 3)
        then:
        RuntimeException e2 = thrown()
        assert e2.message == ShoppingCartInventoryException.LESS_INVENTORY
    }
}
