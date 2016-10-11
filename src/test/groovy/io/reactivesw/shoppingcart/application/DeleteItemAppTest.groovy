package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.infrastructure.persistence.ShoppingCartRepository
import spock.lang.Shared
import spock.lang.Specification

class DeleteItemAppTest extends Specification {

    @Shared
    long customerId = 1001L

    @Shared
    String sessionId = "test_session_001"

    @Shared
    long skuId = 1001L

    DeleteItemApp deleteItemApp = new DeleteItemApp()

    ShoppingCartRepository shoppingCartRepository = Stub(ShoppingCartRepository)

    def "delete for customer"() {
        setup:
        shoppingCartRepository.deleteByCustomerIdAndSkuId(_, _) >> 1L
        deleteItemApp.shoppingCartRepository = shoppingCartRepository

        when:
        boolean deleted = deleteItemApp.deleteSkuByCustomerId(customerId, skuId)
        then:
        deleted == true
    }

    def "delete for customer none"() {
        setup:
        shoppingCartRepository.deleteByCustomerIdAndSkuId(_, _) >> -1L
        deleteItemApp.shoppingCartRepository = shoppingCartRepository

        when:
        boolean deleted = deleteItemApp.deleteSkuByCustomerId(customerId, skuId)
        then:
        deleted == false
    }

    def "delete all for customer"() {
        setup:
        shoppingCartRepository.deleteByCustomerId(_) >> 1L
        deleteItemApp.shoppingCartRepository = shoppingCartRepository

        when:
        boolean deleted = deleteItemApp.deleteByCustomerId(customerId)
        then:
        deleted == true
    }

    def "delete all for customer none"() {
        setup:
        shoppingCartRepository.deleteByCustomerId(_) >> -1L
        deleteItemApp.shoppingCartRepository = shoppingCartRepository

        when:
        boolean deleted = deleteItemApp.deleteByCustomerId(customerId)
        then:
        deleted == false
    }

    def "delete for session"() {
        setup:
        shoppingCartRepository.deleteBySessionIdAndSkuId(_, _) >> 1L
        deleteItemApp.shoppingCartRepository = shoppingCartRepository

        when:
        boolean deleted = deleteItemApp.deleteSkuBySessionId(sessionId, skuId)
        then:
        deleted == true
    }

    def "delete for session none"() {
        setup:
        shoppingCartRepository.deleteBySessionIdAndSkuId(_, _) >> -1L
        deleteItemApp.shoppingCartRepository = shoppingCartRepository

        when:
        boolean deleted = deleteItemApp.deleteSkuBySessionId(sessionId, skuId)
        then:
        deleted == false
    }

    def "delete all for session"() {
        setup:
        shoppingCartRepository.deleteBySessionId(_) >> 1L
        deleteItemApp.shoppingCartRepository = shoppingCartRepository

        when:
        boolean deleted = deleteItemApp.deleteBySessionId(sessionId)
        then:
        deleted == true
    }

    def "delete all for session none"() {
        setup:
        shoppingCartRepository.deleteBySessionId(_) >> -1L
        deleteItemApp.shoppingCartRepository = shoppingCartRepository

        when:
        boolean deleted = deleteItemApp.deleteBySessionId(sessionId)
        then:
        deleted == false
    }

}
