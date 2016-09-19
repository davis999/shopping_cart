package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService
import io.reactivesw.shoppingcart.infrastructure.common.ShoppingCartException

import spock.lang.Shared
import spock.lang.Specification

class AddProductToShoppingCartTest extends Specification {

  @Shared
  String customerId = "test_customer_1001"

  @Shared
  String sessionId = "test_session_001"

  @Shared
  String skuId = "test_sku_001"

  @Shared
  int quantity = 1

  @Shared
  int inventory = 10

  AddProductToShoppingCartApp addProductToShoppingCartService = new AddProductToShoppingCartApp()

  def "add with customer and shopping cart not existed" () {
    setup:
    ShoppingCart requestSC = new ShoppingCart()
    requestSC.setCustomerId(customerId)
    requestSC.setSkuId(skuId)
    requestSC.setQuantity(quantity)
    
    ShoppingCartService shoppingCartService = Mock(ShoppingCartService)
    shoppingCartService.findOneByCustomerIdAndSkuId(String, String) >> null
    ShoppingCart addedSC = new ShoppingCart()
    addedSC.setShoppingCartId(1001L)
    shoppingCartService.save(_) >> addedSC
    addProductToShoppingCartService.shoppingCartService = shoppingCartService
    
    when: "add to shopping cart not existed"
    ShoppingCart addResult = addProductToShoppingCartService.addProductToShoppingCart(requestSC, inventory)
    then: "add success"
    addResult == addedSC
  }
  
  def "add with customer and shopping cart existed" () {
    setup:
    ShoppingCart requestSC = new ShoppingCart()
    requestSC.setCustomerId(customerId)
    requestSC.setSkuId(skuId)
    requestSC.setQuantity(quantity)
    
    ShoppingCart addedSC = new ShoppingCart()
    addedSC.setShoppingCartId(1001L)
    addedSC.setSkuId(skuId)
    addedSC.setQuantity(2)
    ShoppingCartService shoppingCartService = Mock(ShoppingCartService)
    shoppingCartService.findOneByCustomerIdAndSkuId(String, String) >> addedSC
    addedSC.setQuantity(quantity+2)
    shoppingCartService.save(_) >> addedSC
    addProductToShoppingCartService.shoppingCartService = shoppingCartService
    
    when: "add to shopping cart existed"
    ShoppingCart addResult = addProductToShoppingCartService.addProductToShoppingCart(requestSC, inventory)
    then: "add success"
    addResult == addedSC
  }

  def "add with session and shopping cart not existed" () {
    setup:
    ShoppingCart requestSC = new ShoppingCart()
    requestSC.setSessionId(sessionId)
    requestSC.setSkuId(skuId)
    requestSC.setQuantity(quantity)
    
    ShoppingCartService shoppingCartService = Mock(ShoppingCartService)
    shoppingCartService.findOneBySessionIdAndSkuId(String, String) >> null
    ShoppingCart addedSC = new ShoppingCart()
    addedSC.setShoppingCartId(1001L)
    shoppingCartService.save(_) >> addedSC
    addProductToShoppingCartService.shoppingCartService = shoppingCartService
    
    when: "add to shopping cart not existed"
    ShoppingCart addResult = addProductToShoppingCartService.addProductToShoppingCart(requestSC, inventory)
    then: "add success"
    addResult == addedSC
  }

  def "add with session and shopping cart existed" () {
    setup:
    ShoppingCart requestSC = new ShoppingCart()
    requestSC.setSessionId(sessionId)
    requestSC.setSkuId(skuId)
    requestSC.setQuantity(quantity)
    
    ShoppingCart addedSC = new ShoppingCart()
    addedSC.setShoppingCartId(1001L)
    addedSC.setSkuId(skuId)
    addedSC.setQuantity(2)
    ShoppingCartService shoppingCartService = Mock(ShoppingCartService)
    shoppingCartService.findOneBySessionIdAndSkuId(String, String) >> null
    addedSC.setQuantity(quantity+2)
    shoppingCartService.save(_) >> addedSC
    addProductToShoppingCartService.shoppingCartService = shoppingCartService
    
    when: "add to shopping cart not existed"
    ShoppingCart addResult = addProductToShoppingCartService.addProductToShoppingCart(requestSC, inventory)
    then: "add success"
    addResult == addedSC
  }

  def "add product to shopping cart with exhausted inventory by customer" () {
    setup:
    ShoppingCart requestSC = new ShoppingCart()
    requestSC.setCustomerId(customerId)
    requestSC.setSkuId(skuId)
    requestSC.setQuantity(quantity+1)
    
    ShoppingCartService shoppingCartService = Mock(ShoppingCartService)
    shoppingCartService.findOneByCustomerIdAndSkuId(String, String) >> null
    addProductToShoppingCartService.shoppingCartService = shoppingCartService
    
    when: "add to shopping cart"
    addProductToShoppingCartService.addProductToShoppingCart(requestSC, 1)
    then: "add failed"
    ShoppingCartException e = thrown()
    assert e.message == ShoppingCartException.RESOURCE_EXHAUSTED
  }

  def "add product to shopping cart with exhausted inventory by session" () {
    setup:
    ShoppingCart requestSC = new ShoppingCart()
    requestSC.setSessionId(sessionId)
    requestSC.setSkuId(skuId)
    requestSC.setQuantity(quantity+1)
    
    ShoppingCartService shoppingCartService = Mock(ShoppingCartService)
    shoppingCartService.findOneBySessionIdAndSkuId(String, String) >> null
    addProductToShoppingCartService.shoppingCartService = shoppingCartService
    
    when: "add to shopping cart"
    addProductToShoppingCartService.addProductToShoppingCart(requestSC, 1)
    then: "add failed"
    ShoppingCartException e = thrown()
    assert e.message == ShoppingCartException.RESOURCE_EXHAUSTED
  }
}
