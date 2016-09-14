package io.reactivesw.shoppingcart.application.grpc

import io.reactivesw.shoppingcart.ShoppingCartServiceStarter
import io.reactivesw.shoppingcart.application.AddProductToShoppingCartApp
import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.grpc.ShoppingCartOuterClass

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import spock.lang.Shared
import spock.lang.Specification

import javax.annotation.Resource

@SpringBootTest
@ContextConfiguration(classes=[ShoppingCartServiceStarter.class])
class ShoppingCartGrpcServiceTest extends Specification {

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

  AddProductToShoppingCartApp addProductToShoppingCartHandler = Mock()
  ShoppingCart shoppingCartModel = Mock()
  
  @Resource
  ShoppingCartGrpcService scGrpc

  def setup() {
    scGrpc.addProductToShoppingCartHandler = addProductToShoppingCartHandler
    addProductToShoppingCartHandler.addProductToShoppingCart(_, _) >> shoppingCartModel
  }

  def "valid and convert product request" () {
    setup:
    ShoppingCartOuterClass.AddRequest request = ShoppingCartOuterClass.AddRequest.newBuilder().
      setCustomerId(customerId).
      setSessionId(sessionId).
      setSkuId(skuId).
      setInventory(inventory).
      setQuantity(quantity).build()

    when:
    ShoppingCart convertResult = scGrpc.requestToShoppingCart(request)
    then:
    convertResult.getCustomerId() == customerId
  }

  def "valid session request" () {
    setup:
    ShoppingCartOuterClass.AddRequest request = ShoppingCartOuterClass.AddRequest.newBuilder().
      setSessionId(sessionId).
      setSkuId(skuId).
      setInventory(inventory).
      setQuantity(quantity).build()

    when:
    ShoppingCart convertResult = scGrpc.requestToShoppingCart(request)
    then:
    convertResult.getSessionId() == sessionId
  }

  def "valid null customer and null session" () {
    setup:
    ShoppingCartOuterClass.AddRequest request = ShoppingCartOuterClass.AddRequest.newBuilder().
      setSkuId(skuId).
      setInventory(inventory).
      setQuantity(quantity).build()

    when:
    ShoppingCart convertResult = scGrpc.requestToShoppingCart(request)
    then:
    RuntimeException e = thrown()
    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
  }

  def "null sku id request" () {
    setup:
    ShoppingCartOuterClass.AddRequest request = ShoppingCartOuterClass.AddRequest.newBuilder().
      setCustomerId(customerId).
      setInventory(inventory).
      setQuantity(quantity).build()

    when:
    ShoppingCart convertResult = scGrpc.requestToShoppingCart(request)
    then:
    RuntimeException e = thrown()
    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
  }

  def "null inventory request" () {
    setup:
    ShoppingCartOuterClass.AddRequest request = ShoppingCartOuterClass.AddRequest.newBuilder().
      setCustomerId(customerId).
      setSessionId(sessionId).
      setSkuId(skuId).
      setInventory(0).
      setQuantity(quantity).build()

    when:
    ShoppingCart convertResult = scGrpc.requestToShoppingCart(request)
    then:
    RuntimeException e = thrown()
    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
  }

  def "zero quantity request" () {
    setup:
    ShoppingCartOuterClass.AddRequest request = ShoppingCartOuterClass.AddRequest.newBuilder().
      setCustomerId(customerId).
      setSkuId(skuId).
      setInventory(inventory).
      setQuantity(0).build()

    when:
    ShoppingCart convertResult = scGrpc.requestToShoppingCart(request)
    then:
    convertResult.getCustomerId() == customerId
  }

//  def "add product request" () {
//    setup:
//    ShoppingCart requestSC = new ShoppingCart()
//    requestSC.setCustomerId(customerId)
//    requestSC.setSessionId(sessionId)
//    requestSC.setSkuId(skuId)
//    requestSC.setQuantity(quantity)
//
//    when:
//    ShoppingCart addResult = scGrpc.addToShoppingCart(requestSC, inventory)
//    then:
//    addResult == shoppingCartModel
//  }
}
