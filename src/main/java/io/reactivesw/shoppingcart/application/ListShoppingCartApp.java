package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.application.grpc.ShoppingCartGrpcService;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * business service for shopping cart list.
 * @author janeli
 */
@Service
public class ListShoppingCartApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartGrpcService.class);

  /**
   * shopping cart service.
   */
  @Autowired
  private transient ShoppingCartService shoppingCartService;

  /**
   * get shopping cart for customer.
   * @param customerId long customer id
   * @return List
   */
  public List<ShoppingCart> listCustomerShoppingCart(long customerId) {
    LOGGER.info("list shopping cart for customer----");
    LOGGER.debug("customer id is: {}", customerId);
    return shoppingCartService.listShoppingCartByCustomerId(customerId);
  }

  /**
   * get shopping cart for session.
   * @param sessionId String session id
   * @return List
   */
  public List<ShoppingCart> listSessionShoppingCart(String sessionId) {
    LOGGER.info("list shopping cart for session----");
    LOGGER.debug("session id is: {}", sessionId);
    return shoppingCartService.listShoppingCartBySessionId(sessionId);
  }

}
