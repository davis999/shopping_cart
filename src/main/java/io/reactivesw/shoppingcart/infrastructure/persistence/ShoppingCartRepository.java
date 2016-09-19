package io.reactivesw.shoppingcart.infrastructure.persistence;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * shopping cart repository.
 * 
 * @author janeli
 *
 */
@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

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
  
  /**
   * list shopping cart record by customer id.
   * 
   * @param customerId String customer id
   * @return List
   */
  List<ShoppingCart> findByCustomerId(String customerId);
  
  /**
   * list shopping cart record by session id.
   * 
   * @param sessionId String session id
   * @return List
   */
  List<ShoppingCart> findBySessionId(String sessionId);

}
