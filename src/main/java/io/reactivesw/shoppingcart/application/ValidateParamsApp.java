package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.infrastructure.common.ConstantsUtility;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * business service that add product to shopping cart.
 * @author janeli
 */
@Service
public class ValidateParamsApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ValidateParamsApp.class);

  /**
   * check if the request parameters is valid.
   * @param shoppingCart ShoppingCart
   */
  public void validateRequestParams(ShoppingCart shoppingCart) {
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
}
