package io.reactivesw.shoppingcart.application;

import io.grpc.StatusRuntimeException;
import io.reactivesw.shoppingcart.application.grpc.SkuGrpcClient;
import io.reactivesw.shoppingcart.application.grpc.config.SkuGrpcConfig;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartConfigService;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService;
import io.reactivesw.shoppingcart.infrastructure.common.ConstantsUtility;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartException;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartLimitException;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * business service that add product to shopping cart.
 * @author janeli
 */
@Service
public class AddToShoppingCartApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(AddToShoppingCartApp.class);

  /**
   * shopping cart service.
   */
  @Autowired
  private transient ShoppingCartService shoppingCartService;

  /**
   * shopping cart config service.
   */
  @Autowired
  private transient ShoppingCartConfigService shoppingCartConfigService;

  /**
   * grpc configuration.
   */
  @Autowired
  private transient SkuGrpcConfig grpcConfig;

  /**
   * organize the request data and add product to shopping cart.
   * @param shoppingCart ShoppingCart
   * @return shoppingCartSaved shopping cart object
   */
  public ShoppingCart addToShoppingCart(ShoppingCart shoppingCart) {
    LOGGER.info("app service: add to shopping cart start service. shopping cart: {}",
        shoppingCart);
    this.validateRequestParams(shoppingCart);
    // merge existed sku quantity which has been in shopping cart.
    final ShoppingCart mergedShoppingCart = this.mergeExistedQuantity(shoppingCart);
    // check quantity limit value
    this.checkQuantityLimit(mergedShoppingCart);
    // check if sku inventory is enough.
    this.checkInventory(mergedShoppingCart.getSkuId(), mergedShoppingCart.getQuantity());
    // save shopping cart to DB.
    final ShoppingCart shoppingCartSaved = shoppingCartService.save(mergedShoppingCart);
    LOGGER.info("app service: add to shopping cart service finished. saved shopping cart: {}",
        shoppingCartSaved);
    return shoppingCartSaved;
  }

  /**
   * merge request shopping cart to the stored shopping cart.
   * @param shoppingCart ShoppingCart
   * @return shoppingCart
   */
  private ShoppingCart mergeExistedQuantity(ShoppingCart shoppingCart) {
    LOGGER.debug("app service: merge existed shopping cart start. shopping cart: {}", shoppingCart);
    final ShoppingCart oldCart = shoppingCartService.findOneBySkuIdForCustomer(shoppingCart);
    LOGGER.debug("app service: find an old shopping cart {}", oldCart);
    if (oldCart != null) {
      LOGGER.debug("app service: old shopping cart id: {} existed quantity: {}",
          oldCart.getShoppingCartId(), oldCart.getQuantity());
      shoppingCart.setShoppingCartId(oldCart.getShoppingCartId());
      final int newQuantity = shoppingCart.getQuantity() + oldCart.getQuantity();
      shoppingCart.setQuantity(newQuantity);
      LOGGER.debug("app service: after merge. shopping cart id: {} new quantity: {}",
          oldCart.getShoppingCartId(), newQuantity);
    }
    return shoppingCart;
  }

  /**
   * check total quantity limit for shopping cart.
   * @param shoppingCart ShoppingCart
   */
  private void checkQuantityLimit(ShoppingCart shoppingCart) {
    LOGGER.debug("app service: check shoppingCart total quantity. shopping cart: {}", shoppingCart);
    final int totalQuantity = shoppingCartService.getTotalQuantityForCustomer(shoppingCart);
    final int limitQuantity = shoppingCartConfigService.getTotalQuantityLimit();
    LOGGER.debug("app service: total quantity is {} and limit is {}", totalQuantity, limitQuantity);
    if (limitQuantity != ConstantsUtility.QUANTITY_UNLIMITED) {
      LOGGER.debug("app service: quantity is limited");
      final boolean validateQuantityLimit = limitQuantity < totalQuantity ? false : true;
      if (!validateQuantityLimit) {
        LOGGER.error("app service: total quantity {} is larger than limit value {}",
            totalQuantity, limitQuantity);
        throw new ShoppingCartLimitException(ShoppingCartLimitException.TOTAL_LIMIT);
      }
    }
  }

  /**
   * check if the request parameters is valid.
   * @param shoppingCart ShoppingCart
   */
  private void validateRequestParams(ShoppingCart shoppingCart) {
    LOGGER.debug("app service: check shoppingCart parameters. shopping cart: {}", shoppingCart);
    // need one of customer id and session id.
    this.validateCustomer(shoppingCart.getCustomerId(), shoppingCart.getSessionId());
    // quantity required.
    this.validateQuantity(shoppingCart.getQuantity());
    LOGGER.debug("app service: validate shoppingCart parameters success.");
  }

  /**
   * check: need one of customer id and session id.
   * @param customerId long
   * @param sessionId String
   */
  private void validateCustomer(long customerId, String sessionId) {
    LOGGER.debug("app service: check customer. customer id: {}, session id: {}", customerId,
        sessionId);
    final boolean customerEmpty = customerId == ConstantsUtility.INVALID_CUSTOMER_ID;
    final boolean sessionEmpty = StringUtils.isEmpty(sessionId);
    if (customerEmpty && sessionEmpty) {
      LOGGER.error("app service: customer and session are null. throw ShoppingCartParamException.");
      throw new ShoppingCartParamException(ShoppingCartParamException.CUSTOMER_REQUIRED);
    }
  }

  /**
   * check: quantity required and valid.
   * @param quantity int
   */
  private void validateQuantity(int quantity) {
    LOGGER.debug("app service: check quantity. quantity: {}", quantity);
    final boolean quantityEmpty = quantity == ConstantsUtility.INVALID_ZERO_QUANTITY;
    if (quantityEmpty) {
      LOGGER.error("app service: quantity is null. throw ShoppingCartParamException.");
      throw new ShoppingCartParamException(ShoppingCartParamException.QUANTITY_REQUIRED);
    } else if (quantity < ConstantsUtility.INVALID_ZERO_QUANTITY) {
      LOGGER.error("app service: quantity {} is invalid. throw ShoppingCartParamException.",
          quantity);
      throw new ShoppingCartParamException(ShoppingCartParamException.QUANTITY_INVALID);
    }
  }

  /**
   * check if the inventory is enough.
   * @param skuId long
   * @param quantity int
   */
  private void checkInventory(long skuId, int quantity) {
    LOGGER.debug("app service: check sku id {} inventory. quantity: {}", skuId, quantity);
    int inventory;
    try {
      inventory = this.getInventoryBySkuId(skuId);
      LOGGER.debug("app service: sku id {} inventory is {}", inventory);
    } catch (InterruptedException interruptedEx) {
      LOGGER.error("app service: client shut down throw InterruptedException {}", interruptedEx);
      throw new ShoppingCartException(ShoppingCartException.INTERNAL);
    }
    if (inventory == ConstantsUtility.NULL_INVENTORY) {
      LOGGER.error("app service: zero inventory for sku id {}", skuId);
      throw new ShoppingCartInventoryException(ShoppingCartInventoryException.ZERO_INVENTORY);
    }
    final boolean validateInventory = quantity > inventory ? false : true;
    // inventory exhausted exception.
    if (!validateInventory) {
      LOGGER.error("app service: less inventory for sku id {}", skuId);
      throw new ShoppingCartInventoryException(ShoppingCartInventoryException.LESS_INVENTORY);
    }
  }

  /**
   * call product service to get inventory for sku.
   * @param skuId long
   * @return long
   */
  private int getInventoryBySkuId(long skuId) throws InterruptedException {
    LOGGER.debug("app service: call product service to get inventory for sku id {}", skuId);
    int inventory;
    SkuGrpcClient skuClient = grpcConfig.skuGrpcClient();
    try {
      inventory = skuClient.getInventoryForSku(skuId);
    } catch (StatusRuntimeException grpcEx) {
      LOGGER.error("app service: product is unavailable. sku id: {}. StatusRuntimeException {}",
          skuId, grpcEx);
      throw new ShoppingCartInventoryException(ShoppingCartInventoryException.PRODUCT_UNAVAILABLE);
    } finally {
      skuClient.shutdown();
    }
    return inventory;
  }
}
