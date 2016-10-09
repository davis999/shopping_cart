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
    LOGGER.info("delete customer {} sku {} from shopping cart", customerId, skuId);
    final long deleteCount = shoppingCartRepository.deleteByCustomerIdAndSkuId(customerId, skuId);
    LOGGER.info("delete count: {}", deleteCount);
    return deleteCount == -1 ? false : true;
  }

  /**
   * delete the sku from session shopping cart.
   * @param sessionId String
   * @param skuId long
   * @return boolean
   */
  public boolean deleteSkuBySessionId(String sessionId, long skuId) {
    LOGGER.info("delete customer {} sku {} from shopping cart", sessionId, skuId);
    final long deleteCount = shoppingCartRepository.deleteBySessionIdAndSkuId(sessionId, skuId);
    LOGGER.info("delete count: {}", deleteCount);
    return deleteCount == -1 ? false : true;
  }
}
