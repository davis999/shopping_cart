package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * business service that add product to shopping cart.
 * @author janeli
 */
@Service
public class AddItemApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(AddItemApp.class);

  /**
   * shopping cart service.
   */
  @Autowired
  private transient ShoppingCartService shoppingCartService;

  /**
   * validation service.
   */
  @Autowired
  private transient ValidateParamsApp validateParamsApp;

  /**
   * check shopping cart config service.
   */
  @Autowired
  private transient CheckConfigLimitApp checkConfigLimitApp;

  /**
   * check inventory service.
   */
  @Autowired
  private transient CheckInventoryApp checkInventoryApp;

  /**
   * organize the request data and add product to shopping cart.
   * @param shoppingCart ShoppingCart
   * @return shoppingCartSaved shopping cart object
   */
  public ShoppingCart addToShoppingCart(ShoppingCart shoppingCart) {
    LOGGER.info("app service: add to shopping cart start service. shopping cart: {}",
        shoppingCart);
    validateParamsApp.validateRequestParams(shoppingCart);
    // merge existed sku quantity which has been in shopping cart.
    final ShoppingCart mergedShoppingCart = this.mergeExistedQuantity(shoppingCart);
    // check quantity limit value
    checkConfigLimitApp.checkQuantityLimit(mergedShoppingCart);
    // check if sku inventory is enough.
    checkInventoryApp
        .checkInventory(mergedShoppingCart.getSkuId(), mergedShoppingCart.getQuantity());
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
}
