package io.reactivesw.shoppingcart.application;

import io.grpc.StatusRuntimeException;
import io.reactivesw.shoppingcart.application.grpc.SkuGrpcClient;
import io.reactivesw.shoppingcart.application.grpc.config.SkuGrpcConfig;
import io.reactivesw.shoppingcart.infrastructure.common.ConstantsUtility;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartException;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * business service that add product to shopping cart.
 * @author janeli
 */
@Service
public class CheckInventoryApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(CheckInventoryApp.class);

  /**
   * grpc configuration.
   */
  @Autowired
  private transient SkuGrpcConfig skuGrpcConfig;

  /**
   * check if the inventory is enough.
   * @param skuId long
   * @param quantity int
   */
  public void checkInventory(long skuId, int quantity) {
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
    SkuGrpcClient skuClient = skuGrpcConfig.skuGrpcClient();
    try {
      inventory = skuClient.getInventoryForSku(skuId);
    } catch (StatusRuntimeException grpcEx) {
      LOGGER.error("app service: product is unavailable. sku id: {}. StatusRuntimeException {}",
          skuId, grpcEx);
      throw new ShoppingCartInventoryException(ShoppingCartInventoryException.PRODUCT_UNAVAILABLE);
    }
    return inventory;
  }
}
