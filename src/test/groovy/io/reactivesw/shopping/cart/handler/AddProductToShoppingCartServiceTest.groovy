package io.reactivesw.shopping.cart.handler

import javax.annotation.Resource;

import io.reactivesw.grpc.ShoppingCartOuterClass
import io.reactivesw.shopping.cart.ShoppingCartServiceStarter
import io.reactivesw.shopping.cart.domain.ShoppingCart
import io.reactivesw.shopping.cart.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration(classes=[ShoppingCartServiceStarter.class])
class AddProductToShoppingCartServiceTest extends Specification {

  @Shared
  String customerId = "1001"

  @Shared
  String sessionId = "session_001"

  @Shared
  String sku = "sku_001"

  @Shared
  int qty = 1

  @Shared
  int inventory = 10

  @Resource
  AddProductToShoppingCartService addProductToShoppingCartService
  
  @Resource
  ShoppingCartService shoppingCartService

  def "add with customer and session" () {
    setup:
    shoppingCartService.deleteByCustomerIdAndSku(customerId, sku)
    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
    setCustomerId(customerId).
    setSessionId(sessionId).
    setSku(sku).
    setQty(qty).
    setInventory(inventory).build()
    
    when: "grpc request to shopping cart not existed"
    String addResult = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add success"
    assert addResult != null
    
    when: "grpc request to shopping cart existed"
    String addResultExisted = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add success"
    assert addResultExisted != null
  }

  def "add product to shopping cart with customer" () {
    setup:
    shoppingCartService.deleteByCustomerIdAndSku(customerId, sku)
    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
    setCustomerId(customerId).
    setSku(sku).
    setQty(qty).
    setInventory(inventory).build()
    
    when: "grpc request to shopping cart not existed"
    String addResult = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add success"
    assert addResult != null
    
    when: "grpc request to shopping cart existed"
    String addResultExisted = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add success"
    assert addResultExisted != null
  }

  def "add product to shopping cart with session" () {
    setup:
    shoppingCartService.deleteBySessionIdAndSku(sessionId, sku)
    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
    setSessionId(sessionId).
    setSku(sku).
    setQty(qty).
    setInventory(inventory).build()
    
    when: "grpc request to shopping cart not existed"
    String addResult = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add success"
    assert addResult != null
    
    when: "grpc request to shopping cart existed"
    String addResultExisted = addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add success"
    assert addResultExisted != null
  }

  def "add product to shopping cart without customer and session" () {
    setup:
    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
    setSku(sku).
    setQty(qty).
    setInventory(inventory).build()
    when: "grpc request to shopping cart"
    addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add failed"
    RuntimeException e = thrown()
    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
  }

  def "add product to shopping cart without sku" () {
    setup:
    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
    setCustomerId(customerId).
    setQty(qty).
    setInventory(inventory).build()
    when: "grpc request to shopping cart"
    addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add failed"
    RuntimeException e = thrown()
    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
  }

  def "add product to shopping cart without inventory" () {
    setup:
    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
    setCustomerId(customerId).
    setSku(sku).
    setQty(qty).
    setInventory(0).build()
    when: "grpc request to shopping cart"
    addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add failed"
    RuntimeException e = thrown()
    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
  }

  def "add product to shopping cart with exhausted inventory by customer" () {
    setup:
    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
    setCustomerId(customerId).
    setSku(sku).
    setQty(qty+1).
    setInventory(1).build()
    when: "grpc request to shopping cart"
    addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add failed"
    RuntimeException e = thrown()
    assert e.message == "RESOURCE_EXHAUSTED: resource is exhausted"
  }

  def "add product to shopping cart with exhausted inventory by session" () {
    setup:
    ShoppingCartOuterClass.AddRequest grpcRequest = new ShoppingCartOuterClass.AddRequest().newBuilder().
    setSessionId(sessionId).
    setSku(sku).
    setQty(qty+1).
    setInventory(1).build()
    when: "grpc request to shopping cart"
    addProductToShoppingCartService.addProductToShoppingCart(grpcRequest)
    then: "add failed"
    RuntimeException e = thrown()
    assert e.message == "RESOURCE_EXHAUSTED: resource is exhausted"
  }
}
