package io.reactivesw.shopping.cart.grpc;

import io.grpc.stub.StreamObserver;
import io.reactivesw.grpc.ShoppingCartGrpc;
import io.reactivesw.grpc.ShoppingCartOuterClass;
import io.reactivesw.shopping.cart.handler.AddProductToShoppingCartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * grpc server that add product to shopping cart service.
 * 
 * @author janeli
 *
 */
@org.lognet.springboot.grpc.GRpcService
public class ShoppingCartGrpcService extends ShoppingCartGrpc.ShoppingCartImplBase {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartGrpcService.class);

  /**
   * add to product normal service.
   */
  @Resource
  private transient AddProductToShoppingCartService addProductToShoppingCartHandler;

  /**
   * add product to shopping cart and reply to the grpc client.
   * 
   * @param request ShoppingCartOuterClass.AddRequest
   * @param responseObserver StreamObserver ShoppingCartOuterClass.AddReply
   */
  @Override
  public void addProductToShoppingCart(ShoppingCartOuterClass.AddRequest request,
      StreamObserver<ShoppingCartOuterClass.AddReply> responseObserver) {
    LOGGER.debug("grpc server: add product to shopping cart start----");
    // add requested product(sku) to shopping cart
    final String addResult = addProductToShoppingCartHandler.addProductToShoppingCart(request);
    // grpc reply message
    LOGGER.debug("add product to shopping cart finished----");
    final ShoppingCartOuterClass.AddReply.Builder replyBuilder =
        ShoppingCartOuterClass.AddReply.newBuilder().setShoppingCartId(addResult);
    responseObserver.onNext(replyBuilder.build());
    responseObserver.onCompleted();
  }
}
