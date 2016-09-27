package io.reactivesw.shoppingcart.infrastructure.persistence;

import io.reactivesw.shoppingcart.domain.model.ShoppingCartConfig;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * shopping cart repository.
 * @author janeli
 */
@Repository
public interface ShoppingCartConfigRepository extends CrudRepository<ShoppingCartConfig, Long> {

  /**
   * find shopping cart config record by config key.
   * @param configKey String
   * @return shoppingCartConfig ShoppingCartConfig
   */
  ShoppingCartConfig findOneByConfigKey(String configKey);

}
