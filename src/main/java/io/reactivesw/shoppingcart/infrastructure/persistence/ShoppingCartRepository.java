package io.reactivesw.shoppingcart.infrastructure.persistence;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * shopping cart repository.
 * @author janeli
 */
@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

  /**
   * find shopping cart record by customer id and sku id.
   * @param customerId long
   * @param skuId long
   * @return shoppingCart ShoppingCart
   */
  ShoppingCart findOneByCustomerIdAndSkuId(long customerId, long skuId);

  /**
   * find shopping cart record by session id and sku id.
   * @param sessionId String
   * @param skuId long
   * @return shoppingCart ShoppingCart
   */
  ShoppingCart findOneBySessionIdAndSkuId(String sessionId, long skuId);

  /**
   * delete shopping cart record by customer id and sku id.
   * @param customerId long
   * @param skuId long
   * @return long
   */
  @Transactional(timeout = 10)
  Long deleteByCustomerIdAndSkuId(long customerId, long skuId);

  /**
   * delete shopping cart record by customer id.
   * @param customerId long
   * @return long
   */
  @Transactional(timeout = 10)
  Long deleteByCustomerId(long customerId);

  /**
   * delete shopping cart record by session id and sku id.
   * @param sessionId String
   * @param skuId long
   * @return long
   */
  @Transactional(timeout = 10)
  Long deleteBySessionIdAndSkuId(String sessionId, long skuId);

  /**
   * delete shopping cart record by session id.
   * @param sessionId String
   * @return long
   */
  @Transactional(timeout = 10)
  Long deleteBySessionId(String sessionId);

  /**
   * list shopping cart record by customer id.
   * @param customerId long customer id
   * @return List
   */
  List<ShoppingCart> findByCustomerId(long customerId);

  /**
   * list shopping cart record by session id.
   * @param sessionId String session id
   * @return List
   */
  List<ShoppingCart> findBySessionId(String sessionId);

}
