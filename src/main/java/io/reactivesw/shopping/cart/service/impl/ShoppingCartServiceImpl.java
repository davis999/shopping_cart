package io.reactivesw.shopping.cart.service.impl;

import io.reactivesw.shopping.cart.common.ConstantsUtility;
import io.reactivesw.shopping.cart.domain.ShoppingCart;
import io.reactivesw.shopping.cart.repository.ShoppingCartRepository;
import io.reactivesw.shopping.cart.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
   * find shopping cart record by customer id and sku.
   * 
   * @param customerId String
   * @param sku String
   * @return shoppingCart ShoppingCart
   */
  public ShoppingCart findOneByCustomerIdAndSku(String customerId, String sku) {
    ShoppingCart shoppingCart = null;
    final boolean customerEmpty = ConstantsUtility.stringEmpty(customerId);
    final boolean skuEmpty = ConstantsUtility.stringEmpty(sku);
    if (!customerEmpty && !skuEmpty) {
      shoppingCart = shoppingCartRepository.findOneByCustomerIdAndSku(customerId, sku);
    }
    return shoppingCart;
  }

  /**
   * find shopping cart record by session id and sku.
   * 
   * @param sessionId String
   * @param sku String
   * @return shoppingCart ShoppingCart
   */
  public ShoppingCart findOneBySessionIdAndSku(String sessionId, String sku) {
    ShoppingCart shoppingCart = null;
    final boolean sessionEmpty = ConstantsUtility.stringEmpty(sessionId);
    final boolean skuEmpty = ConstantsUtility.stringEmpty(sku);
    if (!sessionEmpty && !skuEmpty) {
      shoppingCart = shoppingCartRepository.findOneBySessionIdAndSku(sessionId, sku);
    }
    return shoppingCart;
  }

  /**
   * delete shopping cart record by customer id and sku.
   * 
   * @param customerId String
   * @param sku String
   * @return Long
   */
  @Transactional
  public Long deleteByCustomerIdAndSku(String customerId, String sku) {
    Long deleteCount = ConstantsUtility.ERROR_COUNT;
    final boolean customerEmpty = ConstantsUtility.stringEmpty(customerId);
    final boolean skuEmpty = ConstantsUtility.stringEmpty(sku);
    if (!customerEmpty && !skuEmpty) {
      deleteCount = shoppingCartRepository.deleteByCustomerIdAndSku(customerId, sku);
    }
    return deleteCount;
  }

  /**
   * delete shopping cart record by session id and sku.
   * 
   * @param sessionId String
   * @param sku String
   * @return Long
   */
  @Transactional
  public Long deleteBySessionIdAndSku(String sessionId, String sku) {
    Long deleteCount = ConstantsUtility.ERROR_COUNT;
    final boolean sessionEmpty = ConstantsUtility.stringEmpty(sessionId);
    final boolean skuEmpty = ConstantsUtility.stringEmpty(sku);
    if (!sessionEmpty && !skuEmpty) {
      deleteCount = shoppingCartRepository.deleteBySessionIdAndSku(sessionId, sku);
    }
    return deleteCount;
  }

}
