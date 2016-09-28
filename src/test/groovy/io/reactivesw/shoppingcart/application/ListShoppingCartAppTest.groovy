package io.reactivesw.shoppingcart.application

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService

import spock.lang.Shared
import spock.lang.Specification

class ListShoppingCartAppTest extends Specification {

  @Shared
  String customerId = "test_customer_1001"

  @Shared
  String sessionId = "test_session_001"
  
  ListShoppingCartApp listShoppingCartApp = new ListShoppingCartApp()
  
  ShoppingCart findSC = new ShoppingCart()
  
  List cartList = Mock()
  
  def setup() {
    findSC.setCustomerId(customerId)
    findSC.setSessionId(sessionId)
    findSC.setShoppingCartId(1001L)
    findSC.setModifiedDate(new Date())
    cartList << findSC
    ShoppingCartService shoppingCartService = Mock(ShoppingCartService)
    shoppingCartService.listShoppingCartByCustomerId(_) >> cartList
    shoppingCartService.listShoppingCartBySessionId(_) >> cartList
    listShoppingCartApp.shoppingCartService = shoppingCartService
  }
}
