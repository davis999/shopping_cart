package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.application.grpc.ShoppingCartGrpcService;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService;
import io.reactivesw.shoppingcart.infrastructure.common.ShoppingCartException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * business service that add product to shopping cart.
 * 
 * @author janeli
 *
 */
@Service
public class AddProductToShoppingCartApp {

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
   * @param shoppingCart ShoppingCart
   * @param inventory int
   * @return shoppingCartSaved shopping cart object
   */
  public ShoppingCart addProductToShoppingCart(ShoppingCart shoppingCart, int inventory) {
    LOGGER.debug("add product to shopping cart start service---");
    // merge existed sku quantity which has been in shopping cart.
    final ShoppingCart mergedShoppingCart = mergeSkuQuantity(shoppingCart);
    // check if sku inventory is enough.
    validateSkuInventory(mergedShoppingCart, inventory);
    // save shopping cart to DB.
    final ShoppingCart shoppingCartSaved = shoppingCartService.save(mergedShoppingCart);
    LOGGER.debug("add product to shopping cart service end---");
    return shoppingCartSaved;
  }

  /**
   * merge request shopping cart to the stored shopping cart.
   * 
   * @param shoppingCart ShoppingCart
   * @return shoppingCart
   */
  protected ShoppingCart mergeSkuQuantity(ShoppingCart shoppingCart) {
    LOGGER.debug("merge request and stored quantity---");
    ShoppingCart oldCart;
    // only when customer is empty, find existed sku record of session.
    if (StringUtils.isEmpty(shoppingCart.getCustomerId())) {
      oldCart =
          shoppingCartService.findOneBySessionIdAndSkuId(shoppingCart.getSessionId(),
              shoppingCart.getSkuId());
    } else {
      // find existed sku record of customer.
      oldCart =
          shoppingCartService.findOneByCustomerIdAndSkuId(shoppingCart.getCustomerId(),
              shoppingCart.getSkuId());
    }
    // merge existed quantity to the shopping cart.
    if (oldCart != null) {
      shoppingCart.setShoppingCartId(oldCart.getShoppingCartId());
      final int newQuantity = shoppingCart.getQuantity() + oldCart.getQuantity();
      shoppingCart.setQuantity(newQuantity);
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
    final boolean validateInventory = shoppingCart.getQuantity() > inventory ? false : true;
    // inventory exhausted exception.
    if (!validateInventory) {
      LOGGER.error("add product to shopping cart start failed: RESOURCE_EXHAUSTED---");
      throw new ShoppingCartException(ShoppingCartException.RESOURCE_EXHAUSTED);
    }
  }
}
