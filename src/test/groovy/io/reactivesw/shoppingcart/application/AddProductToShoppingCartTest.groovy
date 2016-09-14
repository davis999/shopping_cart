//package io.reactivesw.shoppingcart.application
//
//import io.reactivesw.shoppingcart.ShoppingCartServiceStarter
//import io.reactivesw.shoppingcart.domain.model.ShoppingCart
//import io.reactivesw.shoppingcart.domain.service.ShoppingCartService
//import io.reactivesw.shoppingcart.domain.service.impl.ShoppingCartServiceImpl
//import io.reactivesw.shoppingcart.grpc.ShoppingCartOuterClass
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
//class AddProductToShoppingCartTest extends Specification {
//
//  @Shared
//  String customerId = "test_customer_1001"
//
//  @Shared
//  String sessionId = "test_session_001"
//
//  @Shared
//  String sku = "test_sku_001"
//
//  @Shared
//  int qty = 1
//
//  @Shared
//  int inventory = 10
//
//  @Resource
//  AddProductToShoppingCartApp addProductToShoppingCartService
//  
//  @Resource
//  ShoppingCartService shoppingCartService
//  
//  ShoppingCart shoppingCartModel = Mock(ShoppingCart)
//  ShoppingCartRepository shoppingCartRepo = Mock(ShoppingCartRepository)
//  
//  def setup() {
//    shoppingCartRepo.save(ShoppingCart.class) >> shoppingCartModel
//    shoppingCartService.setShoppingCartRepository(shoppingCartRepo)
//    addProductToShoppingCartService.setShoppingCartService(shoppingCartService)
//  }
//
//  def "add with customer and session" () {
//    setup:
//    shoppingCartService.deleteByCustomerIdAndSku(customerId, sku)
//    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
//    setCustomerId(customerId).
//    setSessionId(sessionId).
//    setSku(sku).
//    setQty(qty).
//    setInventory(inventory).build()
//    
//    when: "grpc request to shopping cart not existed"
//    String addResult = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add success"
//    1 * shoppingCartRepo.save(_)
//    
//    when: "grpc request to shopping cart existed"
//    String addResultExisted = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add success"
//    1 * shoppingCartRepo.save(_)
//  }
//
//  def "add product to shopping cart with customer" () {
//    setup:
//    shoppingCartService.deleteByCustomerIdAndSku(customerId, sku)
//    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
//    setCustomerId(customerId).
//    setSku(sku).
//    setQty(qty).
//    setInventory(inventory).build()
//    
//    when: "grpc request to shopping cart not existed"
//    String addResult = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add success"
//    1 * shoppingCartRepo.save(_)
//    
//    when: "grpc request to shopping cart existed"
//    String addResultExisted = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add success"
//    1 * shoppingCartRepo.save(_)
//  }
//
//  def "add product to shopping cart with session" () {
//    setup:
//    shoppingCartService.deleteBySessionIdAndSku(sessionId, sku)
//    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
//    setSessionId(sessionId).
//    setSku(sku).
//    setQty(qty).
//    setInventory(inventory).build()
//    
//    when: "grpc request to shopping cart not existed"
//    String addResult = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add success"
//    1 * shoppingCartRepo.save(_)
//    
//    when: "grpc request to shopping cart existed"
//    String addResultExisted = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add success"
//    1 * shoppingCartRepo.save(_)
//  }
//
//  def "add product to shopping cart without customer and session" () {
//    setup:
//    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
//    setSku(sku).
//    setQty(qty).
//    setInventory(inventory).build()
//    when: "grpc request to shopping cart"
//    addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add failed"
//    RuntimeException e = thrown()
//    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
//  }
//
//  def "add product to shopping cart without sku" () {
//    setup:
//    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
//    setCustomerId(customerId).
//    setQty(qty).
//    setInventory(inventory).build()
//    when: "grpc request to shopping cart"
//    addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add failed"
//    RuntimeException e = thrown()
//    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
//  }
//
//  def "add product to shopping cart without inventory" () {
//    setup:
//    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
//    setCustomerId(customerId).
//    setSku(sku).
//    setQty(qty).
//    setInventory(0).build()
//    when: "grpc request to shopping cart"
//    addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add failed"
//    RuntimeException e = thrown()
//    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
//  }
//
//  def "add product to shopping cart with exhausted inventory by customer" () {
//    setup:
//    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
//    setCustomerId(customerId).
//    setSku(sku).
//    setQty(qty+1).
//    setInventory(1).build()
//    when: "grpc request to shopping cart"
//    addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add failed"
//    RuntimeException e = thrown()
//    assert e.message == "RESOURCE_EXHAUSTED: resource is exhausted"
//  }
//
//  def "add product to shopping cart with exhausted inventory by session" () {
//    setup:
//    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
//    setSessionId(sessionId).
//    setSku(sku).
//    setQty(qty+1).
//    setInventory(1).build()
//    when: "grpc request to shopping cart"
//    addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
//    then: "add failed"
//    RuntimeException e = thrown()
//    assert e.message == "RESOURCE_EXHAUSTED: resource is exhausted"
//  }
//}
