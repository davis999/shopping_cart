package io.reactivesw.shoppingcart.application.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import io.reactivesw.shoppingcart.application.AddProductToShoppingCartApp;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.grpc.ShoppingCartGrpc;
import io.reactivesw.shoppingcart.grpc.ShoppingCartOuterClass;
import io.reactivesw.shoppingcart.infrastructure.common.ConstantsUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * grpc server that convert grpc request and add product to shopping cart service.
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
   * grpc request parameters are invalid.
   */
  public static final String GRPC_INVALID_ARGUMENT_MSG = "grpc request parameters are invalid";

  /**
   * resource is exhausted.
   */
  public static final String GRPC_RESOURCE_EXHAUSTED_MSG = "resource is exhausted";

  /**
   * add product to shopping cart failed.
   */
  public static final String GRPC_INTERNAL_MSG = "add product to shopping cart failed";

  /**
   * add to product normal service.
   */
  @Resource
  public transient AddProductToShoppingCartApp addProductToShoppingCartHandler;

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
    // validate and convert request to shopping cart
    final ShoppingCart shoppingCart = requestToShoppingCart(request);
    // add product(sku) to shopping cart
    final Long addedId = addToShoppingCart(shoppingCart, request.getInventory());
    // grpc reply message
    LOGGER.debug("add product to shopping cart finished----");
    final ShoppingCartOuterClass.AddReply.Builder replyBuilder =
        ShoppingCartOuterClass.AddReply.newBuilder().setId(addedId);
    responseObserver.onNext(replyBuilder.build());
    responseObserver.onCompleted();
  }

  /**
   * validate and convert request to shopping cart.
   * 
   * @param request ShoppingCartOuterClass.AddRequest
   * @return shoppingCart ShoppingCart
   */
  public ShoppingCart requestToShoppingCart(ShoppingCartOuterClass.AddRequest request) {
    LOGGER.debug("format grpc request to valid shopping cart---");
    // validate grpc request parameters.
    validateRequestParams(request);
    // convert grpc request to shopping cart.
    return grpcRequestToShoppingCart(request);
  }

  /**
   * call to application and add product(sku) to shopping cart.
   * 
   * @param shoppingCart ShoppingCart
   * @param inventory Long inventory
   * @return Long
   */
  public Long addToShoppingCart(ShoppingCart shoppingCart, int inventory) {
    LOGGER.debug("call shopping cart service to add product---");
    // add requested product(sku) to shopping cart
    final ShoppingCart addResult =
        addProductToShoppingCartHandler.addProductToShoppingCart(shoppingCart, inventory);
    if (addResult == null) {
      LOGGER.error("add product to shopping cart start failed: INTERNAL---");
      final Status status = Status.INTERNAL.withDescription(GRPC_INTERNAL_MSG);
      throw new StatusRuntimeException(status);
    }
    return addResult.getShoppingCartId();
  }

  /**
   * check if the request parameters is valid.
   * 
   * @param grpcRequest ShoppingCartOuterClass.AddRequest
   */
  protected void validateRequestParams(ShoppingCartOuterClass.AddRequest grpcRequest) {
    LOGGER.debug("check parameters of request---");
    // need one of customer id and session id.
    final boolean customerEmpty = StringUtils.isEmpty(grpcRequest.getCustomerId());
    final boolean sessionEmpty = StringUtils.isEmpty(grpcRequest.getSessionId());
    // sku id required.
    final boolean skuEmpty = StringUtils.isEmpty(grpcRequest.getSkuId());
    // inventory required.
    final boolean inventoryEmpty = grpcRequest.getInventory() == ConstantsUtility.ZERO_QUANTITY;
    final boolean validParams = (!customerEmpty || !sessionEmpty) && !skuEmpty && !inventoryEmpty;
    // invalid parameters exception.
    if (!validParams) {
      LOGGER.error("add product to shopping cart start failed: INVALID_ARGUMENT---");
      final Status status = Status.INVALID_ARGUMENT.withDescription(GRPC_INVALID_ARGUMENT_MSG);
      throw new StatusRuntimeException(status);
    }
  }

  /**
   * convert grpc request to shopping cart domain.
   * 
   * @param grpcRequest ShoppingCartOuterClass.AddRequest
   * @return shoppingCart
   */
  protected ShoppingCart grpcRequestToShoppingCart(ShoppingCartOuterClass.AddRequest grpcRequest) {
    LOGGER.debug("convert request to shopping cart---");
    final ShoppingCart shoppingCart = new ShoppingCart();
    // only when customer empty, choose session.
    if (StringUtils.isEmpty(grpcRequest.getCustomerId())) {
      shoppingCart.setSessionId(grpcRequest.getSessionId());
    } else {
      shoppingCart.setCustomerId(grpcRequest.getCustomerId());
    }
    shoppingCart.setSkuId(grpcRequest.getSkuId());
    // set sku quantity which will be added.
    final int skuQuantity = setAddSkuQuantity(grpcRequest.getQuantity());
    shoppingCart.setQuantity(skuQuantity);
    return shoppingCart;
  }

  /**
   * set the quantity of the added sku.
   * 
   * @param quantity int
   * @return skuQuantity
   */
  protected int setAddSkuQuantity(int quantity) {
    LOGGER.debug("set new sku quantity---");
    int skuQuantity;
    // set default quantity.
    if (quantity == ConstantsUtility.ZERO_QUANTITY) {
      skuQuantity = ConstantsUtility.DEFAULT_QUANTITY;
    } else {
      skuQuantity = quantity;
    }
    return skuQuantity;
  }
}
