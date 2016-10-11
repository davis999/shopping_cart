package io.reactivesw.shoppingcart.infrastructure.grpcservice;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import io.reactivesw.shoppingcart.application.EditItemApp;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku;
import io.reactivesw.shoppingcart.grpc.AddReply;
import io.reactivesw.shoppingcart.grpc.AddRequest;
import io.reactivesw.shoppingcart.grpc.GrpcShoppingCartSku;
import io.reactivesw.shoppingcart.grpc.ShoppingCartServiceGrpc;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartLimitException;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * grpc server that convert grpc request and add product to shopping cart service.
 * @author janeli
 */
@GRpcService
public class ScGrpcEditService extends ShoppingCartServiceGrpc.ShoppingCartServiceImplBase {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScGrpcEditService.class);

  /**
   * edit shopping cart application.
   */
  @Autowired
  private transient EditItemApp editItemApp;

  /**
   * add product to shopping cart and reply to the grpc client.
   * @param request ShoppingCartOuterClass.AddRequest
   * @param responseObserver StreamObserver ShoppingCartOuterClass.AddReply
   */
  @Override
  public void editShoppingCart(AddRequest request,
                                StreamObserver<AddReply> responseObserver) {
    LOGGER.info("grpc server: edit shopping cart start, request: {}", request);
    try {
      ShoppingCart shoppingCart = ScGrpcStream.grpcRequestToShoppingCart(request);
      ShoppingCartSku addResult = editItemApp.editShoppingCart(shoppingCart);
      GrpcShoppingCartSku grpcShoppingCartSku = ScGrpcStream.shoppingCartToGrpcReply(addResult);

      AddReply replyMessage = AddReply.newBuilder().setShoppingCart(grpcShoppingCartSku).build();
      ScGrpcUtility.completeResponse(responseObserver, replyMessage);
      LOGGER.info("grpc server: edit shopping cart finished, reply: {}", replyMessage);
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
