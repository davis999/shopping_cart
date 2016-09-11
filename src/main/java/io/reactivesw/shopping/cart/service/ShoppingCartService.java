package io.reactivesw.shopping.cart.service;

import io.reactivesw.shopping.cart.domain.ShoppingCart;

/**
 * shopping cart service for repository.
 * 
 * @author janeli
 *
 */
public interface ShoppingCartService {

  /**
   * save shopping cart record.
   * 
   * @param shoppingCart ShoppingCart
   * @return shoppingCart ShoppingCart
   */
  ShoppingCart save(ShoppingCart shoppingCart);

  /**
   * find shopping cart record by customer id and sku.
   * 
   * @param customerId String
   * @param sku String
   * @return shoppingCart ShoppingCart
   */
  ShoppingCart findOneByCustomerIdAndSku(String customerId, String sku);

  /**
   * find shopping cart record by session id and sku.
   * 
   * @param sessionId String
   * @param sku String
   * @return shoppingCart ShoppingCart
   */
  ShoppingCart findOneBySessionIdAndSku(String sessionId, String sku);

  /**
   * delete shopping cart record by customer id and sku.
   * 
   * @param customerId String
   * @param sku String
   * @return Long
   */
  Long deleteByCustomerIdAndSku(String customerId, String sku);

  /**
   * delete shopping cart record by session id and sku.
   * 
   * @param sessionId String
   * @param sku String
   * @return Long
   */
  Long deleteBySessionIdAndSku(String sessionId, String sku);

}
