package io.reactivesw.shoppingcart.application.grpc

import io.reactivesw.shoppingcart.application.AddProductToShoppingCartApp
import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.grpc.ShoppingCartOuterClass

import java.util.List;

import spock.lang.Shared
import spock.lang.Specification

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

  ShoppingCartGrpcService scGrpc = new ShoppingCartGrpcService()

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

  def "add product request" () {
    setup:
    ShoppingCart requestSC = new ShoppingCart()
    requestSC.setCustomerId(customerId)
    requestSC.setSessionId(sessionId)
    requestSC.setSkuId(skuId)
    requestSC.setQuantity(quantity)

    AddProductToShoppingCartApp addProductToShoppingCartHandler = Mock(AddProductToShoppingCartApp)
    ShoppingCart addedSC = new ShoppingCart()
    addedSC.setShoppingCartId(1001L)
    addProductToShoppingCartHandler.addProductToShoppingCart(_, _) >> addedSC
    scGrpc.addProductToShoppingCartHandler = addProductToShoppingCartHandler

    when:
    def addId = scGrpc.addToShoppingCart(requestSC, inventory)
    then:
    addId == 1001L
  }
  
  def "shopping cart list to repeated"() {
    setup:
    ShoppingCart findSC = new ShoppingCart()
    List<ShoppingCart> cartList = new ArrayList<>()
    findSC.setCustomerId(customerId)
    findSC.setSessionId(sessionId)
    findSC.setQuantity(quantity)
    findSC.setSkuId(skuId)
    findSC.setShoppingCartId(1001L)
    findSC.setModifiedDate(new Date())
    cartList.add(findSC)
    when:
    ShoppingCartOuterClass.ShoppingCartListReply.Builder replyBuilder = scGrpc.repeatShoppingCart(cartList)
    then:
    replyBuilder.getShoppingCart(0).customerId == findSC.getCustomerId()
    replyBuilder.getShoppingCart(0).skuId == findSC.getSkuId()
  }
}
