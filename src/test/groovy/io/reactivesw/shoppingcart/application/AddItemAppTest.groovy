package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService
import spock.lang.Shared
import spock.lang.Specification

class AddItemAppTest extends Specification {

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

    @Shared
    Date createdTime = new Date()

    AddItemApp addItemApp = new AddItemApp()

    ShoppingCart requestSC
    ShoppingCartService shoppingCartService = Stub(ShoppingCartService)
    ValidateParamsApp validateParamsApp = Stub(ValidateParamsApp)
    CheckConfigLimitApp checkConfigLimitApp = Stub(CheckConfigLimitApp)
    CheckInventoryApp checkInventoryApp = Stub(CheckInventoryApp)
    GetSkuInfoSingleApp getSkuInfoSingleApp = Stub(GetSkuInfoSingleApp)

    def "merge existed quantity not existed"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        shoppingCartService.findOneBySkuIdForCustomer(_) >> null
        addItemApp.shoppingCartService = shoppingCartService

        when:
        ShoppingCart shoppingCartMerged1 = addItemApp.mergeExistedQuantity(requestSC)
        then:
        shoppingCartMerged1 == requestSC
    }

    def "merge existed quantity existed"() {
        setup:
        requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)
        ShoppingCart shoppingCartExisted = new ShoppingCart(shoppingCartId: shppingCartId, customerId: customerId, skuId: skuId, quantity: 1, createdTime: createdTime)
        shoppingCartService.findOneBySkuIdForCustomer(_) >> shoppingCartExisted
        addItemApp.shoppingCartService = shoppingCartService

        when:
        ShoppingCart shoppingCartMerged = addItemApp.mergeExistedQuantity(requestSC)
        then:
        shoppingCartMerged.getQuantity() == quantity + 1
        shoppingCartMerged.getShoppingCartId() == shppingCartId
    }

    def "add to shopping cart"() {
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
        addItemApp.validateParamsApp = validateParamsApp
        addItemApp.checkConfigLimitApp = checkConfigLimitApp
        addItemApp.checkInventoryApp = checkInventoryApp
        addItemApp.shoppingCartService = shoppingCartService
        addItemApp.getSkuInfoSingleApp = getSkuInfoSingleApp

        when:
        ShoppingCartSku scSku = addItemApp.addToShoppingCart(requestSC)
        then:
        scSku == scProd
    }
}
