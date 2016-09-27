package io.reactivesw.shoppingcart.domain.service;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.infrastructure.common.ConstantsUtility;
import io.reactivesw.shoppingcart.infrastructure.persistence.ShoppingCartRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * shopping cart service for repository.
 * @author janeli
 */
@Service
public class ShoppingCartService {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartService.class);

  /**
   * shopping cart repository.
   */
  @Autowired
  private transient ShoppingCartRepository shoppingCartRepository;

  /**
   * save shopping cart record.
   * @param shoppingCart ShoppingCart
   * @return shoppingCart ShoppingCart
   */
  public ShoppingCart save(ShoppingCart shoppingCart) {
    LOGGER.debug("domain service: save shopping cart {}", shoppingCart);
    if (shoppingCart.getShoppingCartId() == ConstantsUtility.NULL_SHOPPING_CART_ID) {
      shoppingCart.setCreatedTime(new Date());
    }
    shoppingCart.setModifiedTime(new Date());
    LOGGER.debug("domain service: modified time {}", shoppingCart.getModifiedTime());
    return shoppingCartRepository.save(shoppingCart);
  }

  /**
   * find shopping cart record by sku id and customer id or session id.
   * @param shoppingCart ShoppingCart
   * @return shoppingCart ShoppingCart
   */
  public ShoppingCart findOneBySkuIdForCustomer(ShoppingCart shoppingCart) {
    LOGGER.debug("domain service: find existed shopping cart");
    ShoppingCart existedCart;
    final String sessionId = shoppingCart.getSessionId();
    final long customerId = shoppingCart.getCustomerId();
    final long skuId = shoppingCart.getSkuId();
    LOGGER.debug("domain service: find by sku id {} customer id {} session id {}",
        skuId, customerId, sessionId);
    // only when customer is empty, find existed sku record of session.
    if (shoppingCart.getCustomerId() == ConstantsUtility.INVALID_CUSTOMER_ID) {
      LOGGER.debug("domain service: null customer id. sku id {} session id {}", skuId, sessionId);
      existedCart =
          shoppingCartRepository.findOneBySessionIdAndSkuId(sessionId, skuId);
    } else {
      // find existed sku record of customer.
      LOGGER.debug("domain service: null session id. sku id {} customer id {}", skuId, customerId);
      existedCart =
          shoppingCartRepository.findOneByCustomerIdAndSkuId(customerId, skuId);
    }
    LOGGER.debug("domain service: found an existed shopping cart {}", existedCart);
    return existedCart;
  }

  /**
   * delete shopping cart record by sku id and customer id or session id.
   * @param shoppingCart ShoppingCart
   * @return long
   */
  public long deleteBySkuIdForCustomer(ShoppingCart shoppingCart) {
    LOGGER.debug("domain service: delete existed shopping cart");
    long deleteCount;
    final String sessionId = shoppingCart.getSessionId();
    final long customerId = shoppingCart.getCustomerId();
    final long skuId = shoppingCart.getSkuId();
    LOGGER.debug("domain service: delete by sku id {} customer id {} session id {}",
        skuId, customerId, sessionId);
    // only when customer is empty, find existed sku record of session.
    if (shoppingCart.getCustomerId() == ConstantsUtility.INVALID_CUSTOMER_ID) {
      LOGGER.debug("domain service: null customer id. sku id {} session id {}", skuId, sessionId);
      deleteCount = shoppingCartRepository.deleteBySessionIdAndSkuId(sessionId, skuId);
    } else {
      LOGGER.debug("domain service: null session id. sku id {} customer id {}", skuId, customerId);
      deleteCount = shoppingCartRepository.deleteByCustomerIdAndSkuId(customerId, skuId);
    }
    LOGGER.debug("domain service: total delete count is {}", deleteCount);
    return deleteCount;
  }

  /**
   * get total quantity of the customer's shopping cart.
   * @param shoppingCart ShoppingCart
   * @return totalQuantity int
   */
  public int getTotalQuantityForCustomer(ShoppingCart shoppingCart) {
    LOGGER.debug("domain service: get total shopping cart quantity");
    int totalQuantity = 0;
    final List<ShoppingCart> resultList = this.listShoppingCartForCustomer(shoppingCart);
    LOGGER.debug("domain service: find shopping cart list {}", resultList);
    for (final ShoppingCart scItem : resultList) {
      if (scItem.getSkuId() == shoppingCart.getSkuId()) {
        LOGGER.debug("domain service: current shopping cart sku id {} quantity {}",
            shoppingCart.getSkuId(), shoppingCart.getQuantity());
        totalQuantity = totalQuantity + shoppingCart.getQuantity();
      } else {
        LOGGER.debug("domain service: existed shopping cart sku id {} quantity {}",
            scItem.getSkuId(), scItem.getQuantity());
        totalQuantity = totalQuantity + scItem.getQuantity();
      }
    }
    LOGGER.debug("domain service: total quantity is {}", totalQuantity);
    return totalQuantity;
  }

  /**
   * list shopping cart record for customer.
   * @param shoppingCart ShoppingCart
   * @return List
   */
  public List<ShoppingCart> listShoppingCartForCustomer(ShoppingCart shoppingCart) {
    LOGGER.debug("domain service: get customer shopping cart");
    List<ShoppingCart> resultList;
    final String sessionId = shoppingCart.getSessionId();
    final long customerId = shoppingCart.getCustomerId();
    LOGGER.debug("domain service: find by customer id {} session id {}", customerId, sessionId);
    // only when customer is empty, find shopping cart record by session.
    if (shoppingCart.getCustomerId() == ConstantsUtility.INVALID_CUSTOMER_ID) {
      LOGGER.debug("domain service: null customer. session id {}", sessionId);
      resultList = this.listShoppingCartBySessionId(sessionId);
    } else {
      LOGGER.debug("domain service: null session. customer id {}", customerId);
      resultList = this.listShoppingCartByCustomerId(customerId);
    }
    return resultList;
  }

  /**
   * list shopping cart record by customer id.
   * @param customerId long customer id
   * @return List
   */
  public List<ShoppingCart> listShoppingCartByCustomerId(long customerId) {
    LOGGER.debug("domain service: get shopping cart by customer id {}", customerId);
    List<ShoppingCart> resultList = new ArrayList<>();
    if (customerId != ConstantsUtility.INVALID_CUSTOMER_ID) {
      LOGGER.debug("domain service: find by customer id {}", customerId);
      resultList = shoppingCartRepository.findByCustomerId(customerId);
    }
    return resultList;
  }

  /**
   * list shopping cart record by session id.
   * @param sessionId String session id
   * @return List
   */
  public List<ShoppingCart> listShoppingCartBySessionId(String sessionId) {
    LOGGER.debug("domain service: get shopping cart by session id {}", sessionId);
    List<ShoppingCart> resultList = new ArrayList<>();
    if (!StringUtils.isEmpty(sessionId)) {
      LOGGER.debug("domain service: find by session id {}", sessionId);
      resultList = shoppingCartRepository.findBySessionId(sessionId);
    }
    return resultList;
  }

}
