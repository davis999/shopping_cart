package io.reactivesw.shoppingcart.application.grpc

import io.grpc.stub.StreamObserver
import io.reactivesw.shoppingcart.application.AddItemApp
import io.reactivesw.shoppingcart.application.ListItemsApp
import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.grpc.AddReply
import io.reactivesw.shoppingcart.grpc.AddRequest
import io.reactivesw.shoppingcart.grpc.GrpcShoppingCartSku
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartLimitException
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException

import spock.lang.Shared
import spock.lang.Specification

class ScGrpcServiceTest extends Specification {

  @Shared
  long customerId = 1001L

  @Shared
  String sessionId = "test_session_001"

  @Shared
  long skuId = 1001L

  @Shared
  int quantity = 1

  ScGrpcService scGrpc = new ScGrpcService()
  AddRequest request
  GrpcShoppingCartSku grpcShoppingCartSku = new GrpcShoppingCartSku()
  StreamObserver<AddReply> responseObserver = Mock()

  ShoppingCart requestSC
  AddItemApp addItemApp = Stub(AddItemApp)
  ListItemsApp listItemsApp = Stub(ListItemsApp)

  def setup() {
    request = AddRequest.newBuilder().
            setCustomerId(customerId).
            setSessionId(sessionId).
            setSkuId(skuId).
            setQuantity(quantity).build()
    requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)

    ScGrpcStream.grpcRequestToShoppingCart(_) >> requestSC
    ScGrpcStream.shoppingCartToGrpcReply(_) >> grpcShoppingCartSku
    scGrpc.addItemApp = addItemApp
  }

  def "add to shopping cart" () {
    setup:
    requestSC.setShoppingCartId(1001L)
    addItemApp.addToShoppingCart(_) >> requestSC
    when:
    scGrpc.addToShoppingCart(request, responseObserver)
    then:
    noExceptionThrown()
  }

  def "add throw invalid paramter exception" () {
    setup:
    addItemApp.addToShoppingCart(_) >> {throw new ShoppingCartParamException()}
    scGrpc.addItemApp = addItemApp
    when:
    scGrpc.addToShoppingCart(request, responseObserver)
    then:
    RuntimeException e = thrown()
    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
  }

  def "add throw out of range exception" () {
    setup:
    addItemApp.addToShoppingCart(_) >> {throw new ShoppingCartLimitException()}
    scGrpc.addItemApp = addItemApp
    when:
    scGrpc.addToShoppingCart(request, responseObserver)
    then:
    RuntimeException e = thrown()
    assert e.message == "OUT_OF_RANGE: quantity is out of range"
  }

  def "add throw resource exhausted exception" () {
    setup:
    addItemApp.addToShoppingCart(_) >> {throw new ShoppingCartInventoryException()}
    scGrpc.addItemApp = addItemApp
    when:
    scGrpc.addToShoppingCart(request, responseObserver)
    then:
    RuntimeException e = thrown()
    assert e.message == "RESOURCE_EXHAUSTED: inventory resource is exhausted"
  }

//  def "shopping cart list to repeated"() {
//    setup:
//    ShoppingCart findSC = new ShoppingCart()
//    List<ShoppingCart> cartList = new ArrayList<>()
//    findSC.setCustomerId(customerId)
//    findSC.setSessionId(sessionId)
//    findSC.setQuantity(quantity)
//    findSC.setSkuId(skuId)
//    findSC.setShoppingCartId(1001L)
//    findSC.setModifiedDate(new Date())
//    cartList.add(findSC)
//    when:
//    ShoppingCartListReply.Builder replyBuilder = scGrpc.repeatShoppingCart(cartList)
//    then:
//    replyBuilder.getShoppingCart(0).customerId == findSC.getCustomerId()
//    replyBuilder.getShoppingCart(0).skuId == findSC.getSkuId()
//  }
}
