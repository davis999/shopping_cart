//package io.reactivesw.shoppingcart.domain.service
//
//import io.reactivesw.shoppingcart.ShoppingCartServiceStarter
//import io.reactivesw.shoppingcart.domain.model.ShoppingCart
//import io.reactivesw.shoppingcart.infrastructure.persistence.ShoppingCartRepository
//
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.context.ContextConfiguration
//
//import spock.lang.Shared
//import spock.lang.Specification
//
//import javax.annotation.Resource
//
//@SpringBootTest
//@ContextConfiguration(classes=[ShoppingCartServiceStarter.class])
//class ShoppingCartServiceTest extends Specification {
//  
//  @Resource
//  ShoppingCartService shoppingCartService
//
//  @Shared
//  String customerId = "test_customer_1002"
//
//  @Shared
//  String sessionId = "test_session_002"
//
//  @Shared
//  String sku = "test_sku_002"
//
//  @Shared
//  int qty = 1
//  
//  ShoppingCart shoppingCartModelSave = Mock()
//  ShoppingCart shoppingCartModelFindByCust = Mock()
//  ShoppingCart shoppingCartModelFindBySess = Mock()
//  ShoppingCartRepository shoppingCartRepo = Mock()
//  
//  def setup() {
//    shoppingCartService.setShoppingCartRepository(shoppingCartRepo)
//    shoppingCartRepo.save(ShoppingCart.class) >> shoppingCartModelSave
//    shoppingCartRepo.findOneByCustomerIdAndSku(String, String) >> shoppingCartModelFindByCust
//    shoppingCartRepo.findOneBySessionIdAndSku(String, String) >> shoppingCartModelFindBySess
//    shoppingCartRepo.deleteByCustomerIdAndSku(String, String) >> 1L
//    shoppingCartRepo.deleteBySessionIdAndSku(String, String) >> 1L
//  }
//
//  def "save a customer shopping cart record"() {
//    setup:
//    ShoppingCart shoppingCartCustomer = new ShoppingCart()
//    shoppingCartCustomer.setCustomerId(customerId)
//    shoppingCartCustomer.setSku(sku)
//    shoppingCartCustomer.setQty(qty)
//
//    when: "save to shopping cart"
//    ShoppingCart shoppingCartCustomerSaved = shoppingCartService.save(shoppingCartCustomer)
//
//    then: "save success"
//    1 * shoppingCartRepo.save(_)
//  }
//
//  def "save a session shopping cart record"() {
//    setup:
//    ShoppingCart shoppingCartSession = new ShoppingCart()
//    shoppingCartSession.setSessionId(sessionId)
//    shoppingCartSession.setSku(sku)
//    shoppingCartSession.setQty(qty)
//
//    when: "save to shopping cart"
//    ShoppingCart shoppingCartSessionSaved = shoppingCartService.save(shoppingCartSession)
//
//    then: "save success"
//    1 * shoppingCartRepo.save(_)
//  }
//
//  def "find a record by customer id and sku"() {
//    when: "find a customer and sku specified shopping cart"
//    ShoppingCart shoppingCartFound = shoppingCartService.findOneByCustomerIdAndSku(customerId, sku)
//    then: "find success"
//    1 * shoppingCartRepo.findOneByCustomerIdAndSku(customerId, sku)
//  }
//
//  def "find a record by null customer and sku"() {
//    when: "find a null customer and sku specified shopping cart"
//    ShoppingCart shoppingCartFound = shoppingCartService.findOneByCustomerIdAndSku(null, sku)
//    then: "find failed"
//    0 * shoppingCartRepo.findOneByCustomerIdAndSku(null, sku)
//  }
//
//  def "find a record by customer id and null sku"() {
//    when: "find a customer and null sku specified shopping cart"
//    ShoppingCart shoppingCartFound = shoppingCartService.findOneByCustomerIdAndSku(customerId, null)
//    then: "find failed"
//    0 * shoppingCartRepo.findOneByCustomerIdAndSku(customerId, null)
//  }
//
//  def "find a record by session id and sku"() {
//    when: "find a session and sku specified shopping cart"
//    ShoppingCart shoppingCartFound = shoppingCartService.findOneBySessionIdAndSku(sessionId, sku)
//    then: "find success"
//    1 * shoppingCartRepo.findOneBySessionIdAndSku(sessionId, sku)
//  }
//
//  def "find a record by session id and null sku"() {
//    when: "find a session and null sku specified shopping cart"
//    ShoppingCart shoppingCartFound = shoppingCartService.findOneBySessionIdAndSku(sessionId, null)
//    then: "find failed"
//    0 * shoppingCartRepo.findOneBySessionIdAndSku(sessionId, null)
//  }
//
//  def "find a record by null session and sku"() {
//    when: "find a null session and sku specified shopping cart"
//    ShoppingCart shoppingCartFound = shoppingCartService.findOneBySessionIdAndSku(null, sku)
//    then: "find failed"
//    0 * shoppingCartRepo.findOneBySessionIdAndSku(null, sku)
//  }
//
//  def "delete a record by customer id and sku"() {
//    when: "delete a customer and sku specified shopping cart"
//    Long deleteNum = shoppingCartService.deleteByCustomerIdAndSku(customerId, sku)
//    then: "delete success"
//    1 * shoppingCartRepo.deleteByCustomerIdAndSku(customerId, sku)
//  }
//
//  def "delete a record by customer id and null sku"() {
//    when: "delete a customer and null sku specified shopping cart"
//    Long deleteNum = shoppingCartService.deleteByCustomerIdAndSku(customerId, null)
//    then: "delete failed"
//    0 * shoppingCartRepo.deleteByCustomerIdAndSku(customerId, null)
//  }
//
//  def "delete a record by null customer and sku"() {
//    when: "delete a null customer and sku specified shopping cart"
//    Long deleteNum = shoppingCartService.deleteByCustomerIdAndSku(null, sku)
//    then: "delete failed"
//    0 * shoppingCartRepo.deleteByCustomerIdAndSku(null, sku)
//  }
//
//  def "delete a record by session id and sku"() {
//    when: "delete a session and sku specified shopping cart"
//    Long deleteNum = shoppingCartService.deleteBySessionIdAndSku(sessionId, sku)
//    then: "delete success"
//    1 * shoppingCartRepo.deleteBySessionIdAndSku(sessionId, sku)
//  }
//
//  def "delete a record by session id and null sku"() {
//    when: "delete a session and null sku specified shopping cart"
//    Long deleteNum = shoppingCartService.deleteBySessionIdAndSku(sessionId, null)
//    then: "delete failed"
//    0 * shoppingCartRepo.deleteBySessionIdAndSku(sessionId, null)
//  }
//
//  def "delete a record by null session and sku"() {
//    when: "delete a null session and sku specified shopping cart"
//    Long deleteNum = shoppingCartService.deleteBySessionIdAndSku(null, sku)
//    then: "delete failed"
//    0 * shoppingCartRepo.deleteBySessionIdAndSku(null, sku)
//  }
//}
