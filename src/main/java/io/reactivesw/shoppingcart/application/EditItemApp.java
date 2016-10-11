package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku;
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
public class EditItemApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(EditItemApp.class);

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
   * get sku info service.
   */
  @Autowired
  private transient GetSkuInfoSingleApp getSkuInfoSingleApp;

  /**
   * organize the request data and add product to shopping cart.
   * @param shoppingCart ShoppingCart
   * @return scSku shopping cart sku object
   */
  public ShoppingCartSku editShoppingCart(ShoppingCart shoppingCart) {
    LOGGER.debug("app service: add to shopping cart start service. shopping cart: {}",
        shoppingCart);
    validateParamsApp.validateRequestParams(shoppingCart);
    // check quantity limit value
    checkConfigLimitApp.checkQuantityLimit(shoppingCart);
    // check if sku inventory is enough.
    checkInventoryApp.checkInventory(shoppingCart.getSkuId(), shoppingCart.getQuantity());
    // save shopping cart to DB.
    final ShoppingCart preparedShoppingCart = this.existedShoppingCart(shoppingCart);
    final ShoppingCart shoppingCartSaved = shoppingCartService.save(preparedShoppingCart);

    final ShoppingCartSku scSku = getSkuInfoSingleApp.getShoppingCartSkuInfo(shoppingCartSaved);
    LOGGER.debug("app service: add to shopping cart service finished. info: {}", scSku);
    return scSku;
  }

  /**
   * get the stored shopping cart.
   * @param shoppingCart ShoppingCart
   * @return shoppingCart
   */
  private ShoppingCart existedShoppingCart(ShoppingCart shoppingCart) {
    LOGGER.debug("app service: request more quantity start. shopping cart: {}", shoppingCart);
    final ShoppingCart oldCart = shoppingCartService.findOneBySkuIdForCustomer(shoppingCart);
    LOGGER.debug("app service: find an old shopping cart {}", oldCart);

    if (oldCart != null) {
      LOGGER.debug("app service: old shopping cart id: {}", oldCart.getShoppingCartId());
      shoppingCart.setShoppingCartId(oldCart.getShoppingCartId());
    }
    return shoppingCart;
  }
}
