package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartConfigService;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService;
import io.reactivesw.shoppingcart.infrastructure.common.ConstantsUtility;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartLimitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * business service that add product to shopping cart.
 * @author janeli
 */
@Service
public class CheckConfigLimitApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(CheckConfigLimitApp.class);

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
   * check total quantity limit for shopping cart.
   * @param shoppingCart ShoppingCart
   */
  public void checkQuantityLimit(ShoppingCart shoppingCart) {
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
}
