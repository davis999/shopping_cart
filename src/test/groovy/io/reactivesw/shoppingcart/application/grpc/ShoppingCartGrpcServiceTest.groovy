package io.reactivesw.shoppingcart.application.grpc

import io.grpc.stub.StreamObserver
import io.reactivesw.shoppingcart.application.AddToShoppingCartApp
import io.reactivesw.shoppingcart.application.ListShoppingCartApp
import io.reactivesw.shoppingcart.domain.model.ShoppingCart
import io.reactivesw.shoppingcart.grpc.AddReply
import io.reactivesw.shoppingcart.grpc.AddRequest
import io.reactivesw.shoppingcart.grpc.GrpcShoppingCart
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartLimitException
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException

import spock.lang.Shared
import spock.lang.Specification

class ShoppingCartGrpcServiceTest extends Specification {

  @Shared
  long customerId = 1001L

  @Shared
  String sessionId = "test_session_001"

  @Shared
  long skuId = 1001L

  @Shared
  int quantity = 1

  ShoppingCartGrpcService scGrpc = new ShoppingCartGrpcService()
  AddRequest request
  GrpcShoppingCart grpcShoppingCart = new GrpcShoppingCart()
  StreamObserver<AddReply> responseObserver = Mock()

  ShoppingCart requestSC
  AddToShoppingCartApp addToShoppingCartHandler = Stub(AddToShoppingCartApp)
  ListShoppingCartApp listShoppingCartHandler = Stub(ListShoppingCartApp)

  def setup() {
    request = AddRequest.newBuilder().
            setCustomerId(customerId).
            setSessionId(sessionId).
            setSkuId(skuId).
            setQuantity(quantity).build()
    requestSC = new ShoppingCart(customerId: customerId, sessionId: sessionId, skuId: skuId, quantity: quantity)

    ShoppingCartGrpcStream.grpcRequestToShoppingCart(_) >> requestSC
    ShoppingCartGrpcStream.shoppingCartToGrpcReply(_) >> grpcShoppingCart
    scGrpc.addToShoppingCartHandler = addToShoppingCartHandler
  }

  def "add to shopping cart" () {
    setup:
    requestSC.setShoppingCartId(1001L)
    addToShoppingCartHandler.addToShoppingCart(_) >> requestSC
    when:
    scGrpc.addToShoppingCart(request, responseObserver)
    then:
    noExceptionThrown()
  }

  def "add throw invalid paramter exception" () {
    setup:
    addToShoppingCartHandler.addToShoppingCart(_) >> {throw new ShoppingCartParamException()}
    scGrpc.addToShoppingCartHandler = addToShoppingCartHandler
    when:
    scGrpc.addToShoppingCart(request, responseObserver)
    then:
    RuntimeException e = thrown()
    assert e.message == "INVALID_ARGUMENT: grpc request parameters are invalid"
  }

  def "add throw out of range exception" () {
    setup:
    addToShoppingCartHandler.addToShoppingCart(_) >> {throw new ShoppingCartLimitException()}
    scGrpc.addToShoppingCartHandler = addToShoppingCartHandler
    when:
    scGrpc.addToShoppingCart(request, responseObserver)
    then:
    RuntimeException e = thrown()
    assert e.message == "OUT_OF_RANGE: quantity is out of range"
  }

  def "add throw resource exhausted exception" () {
    setup:
    addToShoppingCartHandler.addToShoppingCart(_) >> {throw new ShoppingCartInventoryException()}
    scGrpc.addToShoppingCartHandler = addToShoppingCartHandler
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
