package io.reactivesw.shoppingcart.infrastructure.grpcservice;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import io.reactivesw.shoppingcart.application.AddItemApp;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku;
import io.reactivesw.shoppingcart.grpc.GrpcShoppingCartSku;
import io.reactivesw.shoppingcart.grpc.ShoppingCartAddServiceGrpc;
import io.reactivesw.shoppingcart.grpc.ShoppingCartReply;
import io.reactivesw.shoppingcart.grpc.ShoppingCartRequest;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartLimitException;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException;
import io.reactivesw.shoppingcart.infrastructure.grpcservice.utils.ScGrpcStream;
import io.reactivesw.shoppingcart.infrastructure.grpcservice.utils.ScGrpcUtility;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * grpc server that convert grpc request and add product to shopping cart service.
 * @author janeli
 */
@GRpcService
public class ScGrpcAddService extends ShoppingCartAddServiceGrpc.ShoppingCartAddServiceImplBase {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScGrpcAddService.class);

  /**
   * add to product application.
   */
  @Autowired
  private transient AddItemApp addItemApp;

  /**
   * add product to shopping cart and reply to the grpc client.
   * @param request ShoppingCartOuterClass.ShoppingCartRequest
   * @param responseObserver StreamObserver ShoppingCartOuterClass.ShoppingCartReply
   */
  @Override
  public void addToShoppingCart(ShoppingCartRequest request,
                                StreamObserver<ShoppingCartReply> responseObserver) {
    LOGGER.info("grpc server: add product to shopping cart start, request: {}", request);
    try {
      ShoppingCart shoppingCart = ScGrpcStream.grpcRequestToShoppingCart(request);
      ShoppingCartSku addResult = addItemApp.addToShoppingCart(shoppingCart);
      GrpcShoppingCartSku grpcShoppingCartSku = ScGrpcStream.shoppingCartToGrpcReply(addResult);

      ShoppingCartReply replyMessage =
          ShoppingCartReply.newBuilder().setShoppingCart(grpcShoppingCartSku).build();
      ScGrpcUtility.completeResponse(responseObserver, replyMessage);
      LOGGER.info("grpc server: add product to shopping cart finished, reply: {}", replyMessage);
    } catch (ShoppingCartParamException scpException) {
      LOGGER.debug("parameters are invalid, throw ShoppingCartParamException: {}", scpException);
      Status status = Status.INVALID_ARGUMENT.withDescription(ScGrpcUtility.INVALID_ARGUMENT_MSG);
      throw new StatusRuntimeException(status);
    } catch (ShoppingCartLimitException sclException) {
      LOGGER.debug("quantity is out of range, throw ShoppingCartLimitException: {}", sclException);
      Status status = Status.OUT_OF_RANGE.withDescription(ScGrpcUtility.OUT_OF_RANGE_MSG);
      throw new StatusRuntimeException(status);
    } catch (ShoppingCartInventoryException sciException) {
      LOGGER
          .debug("inventory is exhausted, throw ShoppingCartInventoryException: {}", sciException);
      Status status = Status.RESOURCE_EXHAUSTED.withDescription(ScGrpcUtility
          .RESOURCE_EXHAUSTED_MSG);
      throw new StatusRuntimeException(status);
    }
  }

}
