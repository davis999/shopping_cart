package io.reactivesw.shoppingcart.application;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.reactivesw.shoppingcart.application.grpc.ShoppingCartGrpcService;
import io.reactivesw.shoppingcart.common.ConstantsUtility;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService;
import io.reactivesw.shoppingcart.grpc.ShoppingCartOuterClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * business service that add product to shopping cart.
 * 
 * @author janeli
 *
 */
@Service
public class AddProductToShoppingCart {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartGrpcService.class);

  /**
   * shopping cart service.
   */
  @Resource
  private transient ShoppingCartService shoppingCartService;

  /**
   * organize the request data and add product to shopping cart.
   * 
   * @param grpcRequest ShoppingCartOuterClass.AddRequest
   * @return shoppingCartId shopping cart id
   */
  public String addProductToShoppingCart(ShoppingCartOuterClass.AddRequest grpcRequest) {
    LOGGER.debug("add product to shopping cart start service---");
    // validate grpc request parameters.
    validateRequestParams(grpcRequest);
    // convert grpc request to shopping cart.
    ShoppingCart shoppingCart = grpcRequestToShoppingCart(grpcRequest);
    // merge existed sku qty which has been in shopping cart.
    shoppingCart = mergeSkuQty(shoppingCart);
    // check if sku inventory is enough.
    validateSkuInventory(shoppingCart, grpcRequest.getInventory());
    // save shopping cart to DB.
    final ShoppingCart shoppingCartSaved = shoppingCartService.save(shoppingCart);
    if (shoppingCartSaved == null) {
      LOGGER.error("add product to shopping cart start failed: INTERNAL---");
      final Status status = Status.INTERNAL.withDescription(ConstantsUtility.GRPC_INTERNAL_MSG);
      throw new StatusRuntimeException(status);
    }
    return shoppingCartSaved.getShoppingCartId().toString();
  }

  /**
   * check if the request parameters is valid.
   * 
   * @param grpcRequest ShoppingCartOuterClass.AddRequest
   */
  protected void validateRequestParams(ShoppingCartOuterClass.AddRequest grpcRequest) {
    LOGGER.debug("check parameters of request---");
    // need one of customer id and session id.
    final boolean customerEmpty = ConstantsUtility.stringEmpty(grpcRequest.getCustomerId());
    final boolean sessionEmpty = ConstantsUtility.stringEmpty(grpcRequest.getSessionId());
    // sku required.
    final boolean skuEmpty = ConstantsUtility.stringEmpty(grpcRequest.getSku());
    // inventory required.
    final boolean inventoryEmpty = grpcRequest.getInventory() == ConstantsUtility.ZERO_QTY;
    final boolean validParams = (!customerEmpty || !sessionEmpty) && !skuEmpty && !inventoryEmpty;
    // invalid parameters exception.
    if (!validParams) {
      LOGGER.error("add product to shopping cart start failed: INVALID_ARGUMENT---");
      final Status status =
          Status.INVALID_ARGUMENT.withDescription(ConstantsUtility.GRPC_INVALID_ARGUMENT_MSG);
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
    if (ConstantsUtility.stringEmpty(grpcRequest.getCustomerId())) {
      shoppingCart.setSessionId(grpcRequest.getSessionId());
    } else {
      shoppingCart.setCustomerId(grpcRequest.getCustomerId());
    }
    shoppingCart.setSku(grpcRequest.getSku());
    // set sku qty which will be added.
    final int skuQty = setAddSkuQty(grpcRequest.getQty());
    shoppingCart.setQty(skuQty);
    return shoppingCart;
  }

  /**
   * set the qty of the added sku.
   * 
   * @param qty int
   * @return skuQty
   */
  protected int setAddSkuQty(int qty) {
    LOGGER.debug("set new sku qty---");
    int skuQty;
    // set default qty.
    if (qty == ConstantsUtility.ZERO_QTY) {
      skuQty = ConstantsUtility.DEFAULT_QTY;
    } else {
      skuQty = qty;
    }
    return skuQty;
  }

  /**
   * merge request shopping cart to the stored shopping cart.
   * 
   * @param shoppingCart ShoppingCart
   * @return shoppingCart
   */
  protected ShoppingCart mergeSkuQty(ShoppingCart shoppingCart) {
    LOGGER.debug("merge request and stored qty---");
    ShoppingCart oldCart;
    // only when customer is empty, find existed sku record of session.
    if (ConstantsUtility.stringEmpty(shoppingCart.getCustomerId())) {
      oldCart =
          shoppingCartService.findOneBySessionIdAndSku(shoppingCart.getSessionId(),
              shoppingCart.getSku());
    } else {
      // find existed sku record of customer.
      oldCart =
          shoppingCartService.findOneByCustomerIdAndSku(shoppingCart.getCustomerId(),
              shoppingCart.getSku());
    }
    // merge existed qty to the shopping cart.
    if (oldCart != null) {
      shoppingCart.setShoppingCartId(oldCart.getShoppingCartId());
      final int newQty = shoppingCart.getQty() + oldCart.getQty();
      shoppingCart.setQty(newQty);
    }
    return shoppingCart;
  }

  /**
   * check if the inventory is enough.
   * 
   * @param shoppingCart ShoppingCart
   * @param inventory int
   */
  protected void validateSkuInventory(ShoppingCart shoppingCart, int inventory) {
    LOGGER.debug("validate inventory---");
    final boolean validateInventory = shoppingCart.getQty() > inventory ? false : true;
    // inventory exhausted exception.
    if (!validateInventory) {
      LOGGER.error("add product to shopping cart start failed: RESOURCE_EXHAUSTED---");
      final Status status =
          Status.RESOURCE_EXHAUSTED.withDescription(ConstantsUtility.GRPC_RESOURCE_EXHAUSTED_MSG);
      throw new StatusRuntimeException(status);
    }
  }
}
