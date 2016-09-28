package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.application.grpc.ScGrpcService;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartProduct;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService;
import io.reactivesw.shoppingcart.infrastructure.common.ConstantsUtility;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartParamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * business service for shopping cart list.
 * @author janeli
 */
@Service
public class ListItemsApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScGrpcService.class);

  /**
   * shopping cart service.
   */
  @Autowired
  private transient ShoppingCartService shoppingCartService;

  /**
   * get product info for shopping cart list service.
   */
  @Autowired
  private transient ListSkuInfoApp listSkuInfoApp;

  /**
   * get shopping cart for customer.
   * @param customerId long customer id
   * @return List ShoppingCartProduct
   */
  public List<ShoppingCartProduct> listByCustomerId(long customerId) {
    LOGGER.debug("app service: list shopping cart for customer id {}", customerId);
    if (customerId == ConstantsUtility.INVALID_CUSTOMER_ID) {
      LOGGER.error("app service: customer id is null. throw ShoppingCartParamException.");
      throw new ShoppingCartParamException(ShoppingCartParamException.CUSTOMER_ID_REQUIRED);
    }
    List<ShoppingCart> itemList = shoppingCartService.listShoppingCartByCustomerId(customerId);
    LOGGER.debug("app service: list shopping cart {}", itemList);
    return listSkuInfoApp.listShoppingCartProductInfo(itemList);
  }

  /**
   * get shopping cart for session.
   * @param sessionId String session id
   * @return List
   */
  public List<ShoppingCartProduct> listBySessionId(String sessionId) {
    LOGGER.debug("app service: list shopping cart for session id {}", sessionId);
    if (StringUtils.isEmpty(sessionId)) {
      LOGGER.error("app service: session id is null. throw ShoppingCartParamException.");
      throw new ShoppingCartParamException(ShoppingCartParamException.SESSION_ID_REQUIRED);
    }
    List<ShoppingCart> itemList = shoppingCartService.listShoppingCartBySessionId(sessionId);
    LOGGER.debug("app service: list shopping cart {}", itemList);
    return listSkuInfoApp.listShoppingCartProductInfo(itemList);
  }

}
