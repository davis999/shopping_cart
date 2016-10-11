//package io.reactivesw.shoppingcart.infrastructure.grpc
//
//import io.grpc.stub.StreamObserver
//import io.reactivesw.shoppingcart.application.ListItemsApp
//import io.reactivesw.shoppingcart.domain.model.ShoppingCart
//import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku
//import io.reactivesw.shoppingcart.grpc.GrpcShoppingCartSku
//import io.reactivesw.shoppingcart.grpc.ShoppingCartListReply
//import spock.lang.Shared
//import spock.lang.Specification
//
//class ScGrpcListServiceTest extends Specification {
//
//  @Shared
//  long customerId = 1001L
//
//  @Shared
//  String sessionId = "test_session_001"
//
//  @Shared
//  long skuId = 1001L
//
//  @Shared
//  int quantity = 1
//
//  @Shared
//  long shppingCartId = 1001L
//
//  @Shared
//  String skuNumber = "test_sku_number_001"
//
//  @Shared
//  String skuName = "test_sku_name_a"
//
//  @Shared
//  String mediaUrl = "http://sample.com/test_001.jpg"
//
//  @Shared
//  String price = "42.00"
//
//  ScGrpcAddService scGrpc = new ScGrpcAddService()
//  GrpcShoppingCartSku grpcShoppingCartSku = new GrpcShoppingCartSku()
//
//  ShoppingCart requestSC
//  ShoppingCartSku scSku
//  ListItemsApp listItemsApp = Stub(ListItemsApp)
//
//  def "shopping cart list to repeated"() {
//    setup:
//    ShoppingCart findSC = new ShoppingCart()
//    List<ShoppingCart> cartList = new ArrayList<>()
//    findSC.setCustomerId(customerId)
//    findSC.setSessionId(sessionId)
//    findSC.setQuantity(quantity)
//    findSC.setSkuId(skuId)
//    findSC.setShoppingCartId(1001L)
//    findSC.setModifiedTime(new Date())
//    cartList.add(findSC)
//    when:
//    ShoppingCartListReply.Builder replyBuilder = scGrpc.repeatShoppingCart(cartList)
//    then:
//    replyBuilder.getShoppingCart(0).customerId == findSC.getCustomerId()
//    replyBuilder.getShoppingCart(0).skuId == findSC.getSkuId()
//  }
//}
