package io.reactivesw.shoppingcart.domain.service.impl;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.service.ShoppingCartService;
import io.reactivesw.shoppingcart.infrastructure.common.ConstantsUtility;
import io.reactivesw.shoppingcart.infrastructure.persistence.ShoppingCartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

import javax.transaction.Transactional;

/**
 * shopping cart service for repository.
 * 
 * @author janeli
 *
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

  /**
   * shopping cart repository.
   */
  @Autowired
  private transient ShoppingCartRepository shoppingCartRepository;

  /**
   * save shopping cart record.
   * 
   * @param shoppingCart ShoppingCart
   * @return shoppingCart ShoppingCart
   */
  public ShoppingCart save(ShoppingCart shoppingCart) {
    shoppingCart.setModifiedDate(new Date());
    return shoppingCartRepository.save(shoppingCart);
  }

  /**
   * find shopping cart record by customer id and sku id.
   * 
   * @param customerId String
   * @param skuId String
   * @return shoppingCart ShoppingCart
   */
  public ShoppingCart findOneByCustomerIdAndSkuId(String customerId, String skuId) {
    ShoppingCart shoppingCart = null;
    final boolean customerEmpty = StringUtils.isEmpty(customerId);
    final boolean skuEmpty = StringUtils.isEmpty(skuId);
    if (!customerEmpty && !skuEmpty) {
      shoppingCart = shoppingCartRepository.findOneByCustomerIdAndSkuId(customerId, skuId);
    }
    return shoppingCart;
  }

  /**
   * find shopping cart record by session id and sku id.
   * 
   * @param sessionId String
   * @param skuId String
   * @return shoppingCart ShoppingCart
   */
  public ShoppingCart findOneBySessionIdAndSkuId(String sessionId, String skuId) {
    ShoppingCart shoppingCart = null;
    final boolean sessionEmpty = StringUtils.isEmpty(sessionId);
    final boolean skuEmpty = StringUtils.isEmpty(skuId);
    if (!sessionEmpty && !skuEmpty) {
      shoppingCart = shoppingCartRepository.findOneBySessionIdAndSkuId(sessionId, skuId);
    }
    return shoppingCart;
  }

  /**
   * delete shopping cart record by customer id and sku id.
   * 
   * @param customerId String
   * @param skuId String
   * @return Long
   */
  @Transactional
  public Long deleteByCustomerIdAndSkuId(String customerId, String skuId) {
    Long deleteCount = ConstantsUtility.ERROR_COUNT;
    final boolean customerEmpty = StringUtils.isEmpty(customerId);
    final boolean skuEmpty = StringUtils.isEmpty(skuId);
    if (!customerEmpty && !skuEmpty) {
      deleteCount = shoppingCartRepository.deleteByCustomerIdAndSkuId(customerId, skuId);
    }
    return deleteCount;
  }

  /**
   * delete shopping cart record by session id and sku id.
   * 
   * @param sessionId String
   * @param skuId String
   * @return Long
   */
  @Transactional
  public Long deleteBySessionIdAndSkuId(String sessionId, String skuId) {
    Long deleteCount = ConstantsUtility.ERROR_COUNT;
    final boolean sessionEmpty = StringUtils.isEmpty(sessionId);
    final boolean skuEmpty = StringUtils.isEmpty(skuId);
    if (!sessionEmpty && !skuEmpty) {
      deleteCount = shoppingCartRepository.deleteBySessionIdAndSkuId(sessionId, skuId);
    }
    return deleteCount;
  }

}
