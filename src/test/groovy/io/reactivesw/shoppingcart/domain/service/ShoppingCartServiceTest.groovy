package io.reactivesw.shoppingcart.domain.service

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService;
import io.reactivesw.shoppingcart.ShoppingCartServiceStarter

import javax.annotation.Resource

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration(classes=[ShoppingCartServiceStarter.class])
class ShoppingCartServiceTest extends Specification {

  @Resource
  ShoppingCartService shoppingCartService

  @Shared
  String customerId = "test_customer_1002"

  @Shared
  String sessionId = "test_session_002"

  @Shared
  String sku = "test_sku_002"

  @Shared
  int qty = 1

  def "save a customer shopping cart record"() {
    setup:
    ShoppingCart shoppingCartCustomer = new ShoppingCart()
    shoppingCartCustomer.setCustomerId(customerId)
    shoppingCartCustomer.setSku(sku)
    shoppingCartCustomer.setQty(qty)
    shoppingCartCustomer.setModifiedDate(new Date())

    when: "save to shopping cart"
    ShoppingCart shoppingCartCustomerSaved = shoppingCartService.save(shoppingCartCustomer)

    then: "save success"
    !shoppingCartCustomerSaved.equals(null)
    assert shoppingCartCustomerSaved.getCustomerId() == customerId
    assert shoppingCartCustomerSaved.getSku() == sku
    assert shoppingCartCustomerSaved.getQty() == qty
  }

  def "save a session shopping cart record"() {
    setup:
    ShoppingCart shoppingCartSession = new ShoppingCart()
    shoppingCartSession.setSessionId(sessionId)
    shoppingCartSession.setSku(sku)
    shoppingCartSession.setQty(qty)
    shoppingCartSession.setModifiedDate(new Date())

    when: "save to shopping cart"
    ShoppingCart shoppingCartSessionSaved = shoppingCartService.save(shoppingCartSession)

    then: "save success"
    !shoppingCartSessionSaved.equals(null)
    assert shoppingCartSessionSaved.getSessionId() == sessionId
    assert shoppingCartSessionSaved.getSku() == sku
    assert shoppingCartSessionSaved.getQty() == qty
  }

  def "find a record by customer id and sku"() {
    when: "find a customer and sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneByCustomerIdAndSku(customerId, sku)
    then: "find success"
    !shoppingCartFound.equals(null)
    assert shoppingCartFound.getCustomerId() == customerId
    assert shoppingCartFound.getSku() == sku
    assert shoppingCartFound.getQty() == qty
  }

  def "find a record by null customer and sku"() {
    when: "find a null customer and sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneByCustomerIdAndSku(null, sku)
    then: "find failed"
    shoppingCartFound.equals(null)
  }

  def "find a record by customer id and null sku"() {
    when: "find a customer and null sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneByCustomerIdAndSku(customerId, null)
    then: "find failed"
    shoppingCartFound.equals(null)
  }

  def "find a record by session id and sku"() {
    when: "find a session and sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneBySessionIdAndSku(sessionId, sku)
    then: "find success"
    !shoppingCartFound.equals(null)
    assert shoppingCartFound.getSessionId() == sessionId
    assert shoppingCartFound.getSku() == sku
    assert shoppingCartFound.getQty() == qty
  }

  def "find a record by session id and null sku"() {
    when: "find a session and null sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneBySessionIdAndSku(sessionId, null)
    then: "find failed"
    shoppingCartFound.equals(null)
  }

  def "find a record by null session and sku"() {
    when: "find a null session and sku specified shopping cart"
    ShoppingCart shoppingCartFound = shoppingCartService.findOneBySessionIdAndSku(null, sku)
    then: "find failed"
    shoppingCartFound.equals(null)
  }

  def "delete a record by customer id and sku"() {
    when: "delete a customer and sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteByCustomerIdAndSku(customerId, sku)
    then: "delete success"
    assert deleteNum == 1
  }

  def "delete a record by customer id and null sku"() {
    when: "delete a customer and null sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteByCustomerIdAndSku(customerId, null)
    then: "delete success"
    assert deleteNum == -1L
  }

  def "delete a record by null customer and sku"() {
    when: "delete a null customer and sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteByCustomerIdAndSku(null, sku)
    then: "delete success"
    assert deleteNum == -1L
  }

  def "delete a record by session id and sku"() {
    when: "delete a session and sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteBySessionIdAndSku(sessionId, sku)
    then: "delete success"
    assert deleteNum == 1
  }

  def "delete a record by session id and null sku"() {
    when: "delete a session and null sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteBySessionIdAndSku(sessionId, null)
    then: "delete success"
    assert deleteNum == -1L
  }

  def "delete a record by null session and sku"() {
    when: "delete a null session and sku specified shopping cart"
    Long deleteNum = shoppingCartService.deleteBySessionIdAndSku(null, sku)
    then: "delete success"
    assert deleteNum == -1L
  }
}
