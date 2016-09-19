package io.reactivesw.shoppingcart.domain.service

import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.domain.service.impl.ShoppingCartServiceImpl
import io.reactivesw.shoppingcart.infrastructure.persistence.ShoppingCartRepository

import spock.lang.Shared
import spock.lang.Specification

class ShoppingCartServiceTest extends Specification {

  @Shared
  String customerId = "test_customer_1002"

  @Shared
  String sessionId = "test_session_002"

  @Shared
  String skuId = "test_sku_002"

  @Shared
  int quantity = 1
  
  ShoppingCartServiceImpl shoppingCartService = new ShoppingCartServiceImpl()
  ShoppingCartRepository shoppingCartRepo = Mock()
  
  ShoppingCart savedSC = new ShoppingCart()
  
  def setup() {
    savedSC.setCustomerId(customerId)
    savedSC.setSessionId(sessionId)
    savedSC.setSkuId(skuId)
    savedSC.setQuantity(quantity)
    savedSC.setShoppingCartId(1001L)
    savedSC.setModifiedDate(new Date())
    
    shoppingCartRepo.save(_) >> savedSC
    shoppingCartRepo.findOneByCustomerIdAndSkuId(_, _) >> savedSC
    shoppingCartRepo.findOneBySessionIdAndSkuId(_, _) >> savedSC
    shoppingCartRepo.deleteByCustomerIdAndSkuId(_, _) >> 1L
    shoppingCartRepo.deleteBySessionIdAndSkuId(_, _) >> 1L
    shoppingCartService.shoppingCartRepository = shoppingCartRepo
  }

  def "save a customer shopping cart record"() {
    setup:
    ShoppingCart shoppingCartCustomer = new ShoppingCart()
    shoppingCartCustomer.setCustomerId(customerId)
    shoppingCartCustomer.setSkuId(skuId)
    shoppingCartCustomer.setQuantity(quantity)

    when: "save to shopping cart"
    ShoppingCart shoppingCartCustomerSaved = shoppingCartService.save(shoppingCartCustomer)

    then: "save success"
    shoppingCartCustomerSaved == savedSC
  }

  def "save a session shopping cart record"() {
    setup:
    ShoppingCart shoppingCartSession = new ShoppingCart()
    shoppingCartSession.setSessionId(sessionId)
    shoppingCartSession.setSkuId(skuId)
    shoppingCartSession.setQuantity(quantity)

    when: "save to shopping cart"
    ShoppingCart shoppingCartSessionSaved = shoppingCartService.save(shoppingCartSession)

    then: "save success"
    shoppingCartSessionSaved == savedSC
  }

  def "find a record by customer id and sku"() {
    when: "find a customer and sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneByCustomerIdAndSkuId(customerId, skuId)
    then: "find success"
    shoppingCartFound == savedSC
  }

  def "find a record by null customer and sku"() {
    when: "find a null customer and sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneByCustomerIdAndSkuId(null, skuId)
    then: "find failed"
    shoppingCartFound == null
  }

  def "find a record by customer id and null sku"() {
    when: "find a customer and null sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneByCustomerIdAndSkuId(customerId, null)
    then: "find failed"
    shoppingCartFound == null
  }

  def "find a record by session id and sku"() {
    when: "find a session and sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneBySessionIdAndSkuId(sessionId, skuId)
    then: "find success"
    shoppingCartFound == savedSC
  }

  def "find a record by session id and null sku"() {
    when: "find a session and null sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneBySessionIdAndSkuId(sessionId, null)
    then: "find failed"
    shoppingCartFound == null
  }

  def "find a record by null session and sku"() {
    when: "find a null session and sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneBySessionIdAndSkuId(null, skuId)
    then: "find failed"
    shoppingCartFound == null
  }

  def "delete a record by customer id and sku"() {
    when: "delete a customer and sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteByCustomerIdAndSkuId(customerId, skuId)
    then: "delete success"
    deleteNum == 1
  }

  def "delete a record by customer id and null sku"() {
    when: "delete a customer and null sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteByCustomerIdAndSkuId(customerId, null)
    then: "delete failed"
    deleteNum == -1
  }

  def "delete a record by null customer and sku"() {
    when: "delete a null customer and sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteByCustomerIdAndSkuId(null, skuId)
    then: "delete failed"
    deleteNum == -1
  }

  def "delete a record by session id and sku"() {
    when: "delete a session and sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteBySessionIdAndSkuId(sessionId, skuId)
    then: "delete success"
    deleteNum == 1
  }

  def "delete a record by session id and null sku"() {
    when: "delete a session and null sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteBySessionIdAndSkuId(sessionId, null)
    then: "delete failed"
    deleteNum == -1
  }

  def "delete a record by null session and sku"() {
    when: "delete a null session and sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteBySessionIdAndSkuId(null, skuId)
    then: "delete failed"
    deleteNum == -1
  }
  
  def "find shopping cart by customer or session"() {
    setup:
    List cartList = Mock()
    cartList << savedSC
    shoppingCartRepo.findByCustomerId(_) >> cartList
    shoppingCartRepo.findBySessionId(_) >> cartList
    shoppingCartService.shoppingCartRepository = shoppingCartRepo
    when: "customer and session not null"
    List shoppingCartListCust = shoppingCartService.listShoppingCartByCustomerId(customerId)
    List shoppingCartListSess = shoppingCartService.listShoppingCartBySessionId(sessionId)
    then:
    shoppingCartListCust == cartList
    shoppingCartListSess == cartList
  }
  
  def "find shopping cart by null"() {
    when: "customer and session null"
    List shoppingCartListCust = shoppingCartService.listShoppingCartByCustomerId(null)
    List shoppingCartListSess = shoppingCartService.listShoppingCartBySessionId(null)
    then: "null returen"
    shoppingCartListCust.size() == 0
    shoppingCartListSess.size() == 0
  }
}
