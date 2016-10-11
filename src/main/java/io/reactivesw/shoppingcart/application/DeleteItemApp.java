package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.infrastructure.persistence.ShoppingCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * business service that delete product from shopping cart.
 * @author janeli
 */
@Service
public class DeleteItemApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteItemApp.class);

  /**
   * shopping cart repository.
   */
  @Autowired
  private transient ShoppingCartRepository shoppingCartRepository;

  /**
   * delete the sku from customer shopping cart.
   * @param customerId long
   * @param skuId long
   * @return boolean
   */
  public boolean deleteSkuByCustomerId(long customerId, long skuId) {
    LOGGER.debug("delete customer {} sku {} from shopping cart", customerId, skuId);
    final long deleteCount = shoppingCartRepository.deleteByCustomerIdAndSkuId(customerId, skuId);
    LOGGER.debug("delete sku for customer shopping cart count: {}", deleteCount);
    return deleteCount == -1 ? false : true;
  }

  /**
   * delete customer shopping cart.
   * @param customerId long
   * @return boolean
   */
  public boolean deleteByCustomerId(long customerId) {
    LOGGER.debug("delete customer {} shopping cart", customerId);
    final long deleteCount = shoppingCartRepository.deleteByCustomerId(customerId);
    LOGGER.debug("delete customer shopping cart count: {}", deleteCount);
    return deleteCount == -1 ? false : true;
  }

  /**
   * delete the sku from session shopping cart.
   * @param sessionId String
   * @param skuId long
   * @return boolean
   */
  public boolean deleteSkuBySessionId(String sessionId, long skuId) {
    LOGGER.debug("delete session {} sku {} from shopping cart", sessionId, skuId);
    final long deleteCount = shoppingCartRepository.deleteBySessionIdAndSkuId(sessionId, skuId);
    LOGGER.debug("delete sku for session shopping cart count: {}", deleteCount);
    return deleteCount == -1 ? false : true;
  }

  /**
   * delete session shopping cart.
   * @param sessionId String
   * @return boolean
   */
  public boolean deleteBySessionId(String sessionId) {
    LOGGER.debug("delete session {} shopping cart", sessionId);
    final long deleteCount = shoppingCartRepository.deleteBySessionId(sessionId);
    LOGGER.debug("delete session shopping cart count: {}", deleteCount);
    return deleteCount == -1 ? false : true;
  }
}
