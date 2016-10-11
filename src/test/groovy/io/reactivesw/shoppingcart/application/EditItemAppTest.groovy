package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService
import spock.lang.Shared
import spock.lang.Specification

class EditItemAppTest extends Specification {

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

    EditItemApp editItemApp = new EditItemApp()

    ShoppingCart requestSC
    ShoppingCartService shoppingCartService = Stub(ShoppingCartService)
    ValidateParamsApp validateParamsApp = Stub(ValidateParamsApp)
    CheckConfigLimitApp checkConfigLimitApp = Stub(CheckConfigLimitApp)
    CheckInventoryApp checkInventoryApp = Stub(CheckInventoryApp)
    GetSkuInfoSingleApp getSkuInfoSingleApp = Stub(GetSkuInfoSingleApp)

    def "existed shopping cart not existed"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        shoppingCartService.findOneBySkuIdForCustomer(_) >> null
        editItemApp.shoppingCartService = shoppingCartService

        when:
        ShoppingCart shoppingCartExisted = editItemApp.existedShoppingCart(requestSC)
        then:
        shoppingCartExisted == requestSC
    }

    def "existed shopping cart existed"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        ShoppingCart shoppingCartExisted = new ShoppingCart(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId, quantity: 1)
        shoppingCartService.findOneBySkuIdForCustomer(_) >> shoppingCartExisted
        editItemApp.shoppingCartService = shoppingCartService

        when:
        ShoppingCart shoppingCartExistedFind = editItemApp.existedShoppingCart(requestSC)
        then:
        shoppingCartExistedFind.getShoppingCartId() == shppingCartId
    }

    def "edit shopping cart"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        ShoppingCart shoppingCartForSaveMock = new ShoppingCart(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId, quantity: 1)
        ShoppingCartSku scProd = new ShoppingCartSku(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId,
                quantity: quantity, skuNumber: "sku_no_001", skuName: "name 001", mediaUrl: "http://reactivesw.io/img001.jpg", price: "42.00")
        validateParamsApp.validateRequestParams(_) >> {}
        checkConfigLimitApp.checkQuantityLimit(_) >> {}
        checkInventoryApp.checkInventory(_, _) >> {}
        shoppingCartService.findOneBySkuIdForCustomer(_) >> null
        shoppingCartService.save(_) >> shoppingCartForSaveMock
        getSkuInfoSingleApp.getShoppingCartSkuInfo(_) >> scProd
        editItemApp.validateParamsApp = validateParamsApp
        editItemApp.checkConfigLimitApp = checkConfigLimitApp
        editItemApp.checkInventoryApp = checkInventoryApp
        editItemApp.shoppingCartService = shoppingCartService
        editItemApp.getSkuInfoSingleApp = getSkuInfoSingleApp

        when:
        ShoppingCartSku scSku = editItemApp.editShoppingCart(requestSC)
        then:
        scSku == scProd
    }
}
