package io.reactivesw.shoppingcart.infrastructure;

import io.reactivesw.shoppingcart.domain.ShoppingCart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * shopping cart repository.S
 * 
 * @author janeli
 *
 */
@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

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
