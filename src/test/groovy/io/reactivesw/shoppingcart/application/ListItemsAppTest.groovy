package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException
import spock.lang.Shared
import spock.lang.Specification

class ListItemsAppTest extends Specification {

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

    ListItemsApp listItemsApp = new ListItemsApp()

    ShoppingCartService shoppingCartService = Stub(ShoppingCartService)
    GetSkuInfoListApp getSkuInfoListApp = Stub(GetSkuInfoListApp)
    ShoppingCart findSC1 = new ShoppingCart(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId, quantity: quantity)
    ShoppingCart findSC2 = new ShoppingCart(shoppingCartId: shppingCartId, sessionId: sessionId, skuId: skuId, quantity: quantity)
    List<ShoppingCartSku> infoList = new ArrayList<>()

    def "list for customer"() {
        setup:
        List<ShoppingCart> cartList = new ArrayList<>()
        cartList.add(findSC1)
        shoppingCartService.listShoppingCartByCustomerId(_) >> cartList
        getSkuInfoListApp.getShoppingCartSkuInfoList(_) >> infoList
        listItemsApp.shoppingCartService = shoppingCartService
        listItemsApp.getSkuInfoListApp = getSkuInfoListApp

        when:
        List shoppingCartListCust = listItemsApp.listByCustomerId(customerId)
        then:
        shoppingCartListCust == infoList
    }

    def "list for customer error"() {
        when:
        List listByCustomerId = listItemsApp.listByCustomerId(0)
        then:
        RuntimeException e = thrown()
        assert e.message == ShoppingCartParamException.CUSTOMER_ID_REQUIRED
    }

    def "list for session"() {
        setup:
        List<ShoppingCart> cartList = new ArrayList<>()
        cartList.add(findSC2)
        shoppingCartService.listShoppingCartBySessionId(_) >> cartList
        getSkuInfoListApp.getShoppingCartSkuInfoList(_) >> infoList
        listItemsApp.shoppingCartService = shoppingCartService
        listItemsApp.getSkuInfoListApp = getSkuInfoListApp

        when:
        List shoppingCartListSess = listItemsApp.listBySessionId(sessionId)
        then:
        shoppingCartListSess == infoList
    }

    def "list for session error"() {
        when:
        List shoppingCartListSess = listItemsApp.listBySessionId(null)
        then:
        RuntimeException e = thrown()
        assert e.message == ShoppingCartParamException.SESSION_ID_REQUIRED
    }

}
