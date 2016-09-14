package io.reactivesw.shoppingcart.domain.service;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;

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
   * find shopping cart record by customer id and sku id.
   * 
   * @param customerId String
   * @param skuId String
   * @return shoppingCart ShoppingCart
   */
  ShoppingCart findOneByCustomerIdAndSkuId(String customerId, String skuId);

  /**
   * find shopping cart record by session id and sku id.
   * 
   * @param sessionId String
   * @param skuId String
   * @return shoppingCart ShoppingCart
   */
  ShoppingCart findOneBySessionIdAndSkuId(String sessionId, String skuId);

  /**
   * delete shopping cart record by customer id and sku id.
   * 
   * @param customerId String
   * @param skuId String
   * @return Long
   */
  Long deleteByCustomerIdAndSkuId(String customerId, String skuId);

  /**
   * delete shopping cart record by session id and sku id.
   * 
   * @param sessionId String
   * @param skuId String
   * @return Long
   */
  Long deleteBySessionIdAndSkuId(String sessionId, String skuId);

}
