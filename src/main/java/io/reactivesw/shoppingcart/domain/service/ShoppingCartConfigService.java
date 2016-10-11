package io.reactivesw.shoppingcart.domain.service;

import io.reactivesw.shoppingcart.domain.model.ShoppingCartConfig;
import io.reactivesw.shoppingcart.infrastructure.persistence.ShoppingCartConfigRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * shopping cart service for repository.
 * @author janeli
 */
@Service
public class ShoppingCartConfigService {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartConfigService.class);

  /**
   * shopping cart config repository.
   */
  @Autowired
  private transient ShoppingCartConfigRepository shoppingCartConfigRepository;

  /**
   * get total quantity limit.
   * @return limit int
   */
  public int getTotalQuantityLimit() {
    LOGGER.debug("domain service: get shopping cart config");
    int limit = 0;
    final ShoppingCartConfig shoppingCartConfig = shoppingCartConfigRepository
        .findOneByConfigKey("total_limit");

    if (shoppingCartConfig != null) {
      LOGGER.debug("domain service: find a shopping cart config {}", shoppingCartConfig);
      limit = Integer.parseInt(shoppingCartConfig.getConfigValue());
    }
    LOGGER.debug("domain service: total quantity limit {}", limit);
    return limit;
  }

}
