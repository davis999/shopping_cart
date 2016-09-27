package io.reactivesw.shoppingcart.application.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import io.reactivesw.shoppingcart.application.AddToShoppingCartApp;
import io.reactivesw.shoppingcart.application.ListShoppingCartApp;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.grpc.*;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartLimitException;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException;

import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * grpc server that convert grpc request and add product to shopping cart service.
 * @author janeli
 */
@GRpcService
public class ShoppingCartGrpcService extends ShoppingCartGrpc.ShoppingCartImplBase {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartGrpcService.class);

  /**
   * add to product application.
   */
  @Autowired
  private transient AddToShoppingCartApp addToShoppingCartHandler;

  /**
   * list shopping cart application.
   */
  @Autowired
  private transient ListShoppingCartApp listShoppingCartHandler;

  /**
   * add product to shopping cart and reply to the grpc client.
   * @param request ShoppingCartOuterClass.AddRequest
   * @param responseObserver StreamObserver ShoppingCartOuterClass.AddReply
   */
  @Override
  public void addToShoppingCart(AddRequest request,
                                StreamObserver<AddReply> responseObserver) {
    LOGGER.info("grpc server: add product to shopping cart start, request: {}", request);
    try {
      final ShoppingCart shoppingCart = ShoppingCartGrpcStream.grpcRequestToShoppingCart(request);
      final ShoppingCart addResult = addToShoppingCartHandler.addToShoppingCart(shoppingCart);
      final GrpcShoppingCart grpcShoppingCart = ShoppingCartGrpcStream.
          shoppingCartToGrpcReply(addResult);
      final AddReply replyMessage = AddReply.newBuilder().setShoppingCart(grpcShoppingCart).build();
      ShoppingCartGrpcUtility.completeResponse(responseObserver, replyMessage);
      LOGGER.info("grpc server: add product to shopping cart finished, reply: {}", replyMessage);
    } catch (ShoppingCartParamException scpException) {
      LOGGER.debug("parameters are invalid, throw ShoppingCartParamException: {}", scpException);
      final Status status = Status.INVALID_ARGUMENT.withDescription(ShoppingCartGrpcUtility
          .INVALID_ARGUMENT_MSG);
      throw new StatusRuntimeException(status);
    } catch (ShoppingCartLimitException sclException) {
      LOGGER.debug("quantity is out of range, throw ShoppingCartLimitException: {}", sclException);
      final Status status = Status.OUT_OF_RANGE.withDescription(ShoppingCartGrpcUtility
          .OUT_OF_RANGE_MSG);
      throw new StatusRuntimeException(status);
    } catch (ShoppingCartInventoryException sciException) {
      LOGGER.debug("inventory is exhausted, throw ShoppingCartInventoryException: {}",
          sciException);
      final Status status = Status.RESOURCE_EXHAUSTED.withDescription(ShoppingCartGrpcUtility
          .RESOURCE_EXHAUSTED_MSG);
      throw new StatusRuntimeException(status);
    }
  }

  /**
   * list customer shopping cart and reply to grpc client.
   * @param request ShoppingCartOuterClass.CustomerListRequest
   * @param responseObserver StreamObserver ShoppingCartListReply
   */
  @Override
  public void listShoppingCartForCustomer(CustomerShoppingCartListRequest request,
                                          StreamObserver<ShoppingCartListReply> responseObserver) {
    LOGGER.info("grpc server: list shopping cart for customer start----");
    final List<ShoppingCart> cartList =
        listShoppingCartHandler.listCustomerShoppingCart(request.getCustomerId());
    LOGGER.info("list shopping cart for customer finished----");
    // convert shopping cart list to reply builder
    final ShoppingCartListReply.Builder replyBuilder =
        ShoppingCartGrpcStream.repeatShoppingCart(cartList);
    ShoppingCartGrpcUtility.completeResponse(responseObserver, replyBuilder.build());
  }

  /**
   * list session shopping cart and reply to grpc client.
   * @param request ShoppingCartOuterClass.SessionListRequest
   * @param responseObserver StreamObserver ShoppingCartListReply
   */
  @Override
  public void listShoppingCartForSession(SessionShoppingCartListRequest request,
                                         StreamObserver<ShoppingCartListReply> responseObserver) {
    LOGGER.info("grpc server: list shopping cart for customer start----");
    final List<ShoppingCart> cartList =
        listShoppingCartHandler.listSessionShoppingCart(request.getSessionId());
    LOGGER.info("list shopping cart for customer finished----");
    // convert shopping cart list to reply builder
    final ShoppingCartListReply.Builder replyBuilder =
        ShoppingCartGrpcStream.repeatShoppingCart(cartList);
    ShoppingCartGrpcUtility.completeResponse(responseObserver, replyBuilder.build());
  }

}
