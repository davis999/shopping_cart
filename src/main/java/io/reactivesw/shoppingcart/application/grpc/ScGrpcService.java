package io.reactivesw.shoppingcart.application.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import io.reactivesw.shoppingcart.application.AddItemApp;
import io.reactivesw.shoppingcart.application.ListItemsApp;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartProduct;
import io.reactivesw.shoppingcart.grpc.AddReply;
import io.reactivesw.shoppingcart.grpc.AddRequest;
import io.reactivesw.shoppingcart.grpc.CustomerShoppingCartListRequest;
import io.reactivesw.shoppingcart.grpc.GrpcShoppingCart;
import io.reactivesw.shoppingcart.grpc.SessionShoppingCartListRequest;
import io.reactivesw.shoppingcart.grpc.ShoppingCartGrpc;
import io.reactivesw.shoppingcart.grpc.ShoppingCartListReply;
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
public class ScGrpcService extends ShoppingCartGrpc.ShoppingCartImplBase {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScGrpcService.class);

  /**
   * add to product application.
   */
  @Autowired
  private transient AddItemApp addItemApp;

  /**
   * list shopping cart application.
   */
  @Autowired
  private transient ListItemsApp listItemsApp;

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
      ShoppingCart shoppingCart = ScGrpcStream.grpcRequestToShoppingCart(request);
      ShoppingCart addResult = addItemApp.addToShoppingCart(shoppingCart);
      GrpcShoppingCart grpcShoppingCart = ScGrpcStream.shoppingCartToGrpcReply(addResult);
      AddReply replyMessage = AddReply.newBuilder().setShoppingCart(grpcShoppingCart).build();
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
      LOGGER.debug("inventory is exhausted, throw ShoppingCartInventoryException: {}",sciException);
      Status status = Status.RESOURCE_EXHAUSTED.withDescription(ScGrpcUtility
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
    LOGGER.debug("grpc server: list shopping cart for customer. request: {}", request);
    List<ShoppingCartProduct> cartList = listItemsApp.listByCustomerId(request.getCustomerId());
    LOGGER.debug("list shopping cart for customer. shopping cart list: {}", cartList);
    // convert shopping cart list to reply builder
    ShoppingCartListReply replyMessage = ScGrpcStream.repeatShoppingCart(cartList).build();
    ScGrpcUtility.completeResponse(responseObserver, replyMessage);
    LOGGER.debug("grpc server: list shopping cart for customer finished, reply: {}", replyMessage);
  }

  /**
   * list session shopping cart and reply to grpc client.
   * @param request ShoppingCartOuterClass.SessionListRequest
   * @param responseObserver StreamObserver ShoppingCartListReply
   */
  @Override
  public void listShoppingCartForSession(SessionShoppingCartListRequest request,
                                         StreamObserver<ShoppingCartListReply> responseObserver) {
    LOGGER.debug("grpc server: list shopping cart for session. request: {}", request);
    List<ShoppingCartProduct> cartList = listItemsApp.listBySessionId(request.getSessionId());
    LOGGER.info("list shopping cart for session. shopping cart list: {}", cartList);
    // convert shopping cart list to reply builder
    ShoppingCartListReply replyMessage = ScGrpcStream.repeatShoppingCart(cartList).build();
    ScGrpcUtility.completeResponse(responseObserver, replyMessage);
    LOGGER.debug("grpc server: list shopping cart for session finished, reply: {}", replyMessage);
  }

}
