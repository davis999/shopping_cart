package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * business service for shopping cart list.
 * @author janeli
 */
@Service
public class GetSkuInfoSingleApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(GetSkuInfoSingleApp.class);

  /**
   * get sku info service.
   */
  @Autowired
  private transient GetSkuInfoApp getSkuInfoApp;

  /**
   * get sku info for shopping cart.
   * @param item ShoppingCart
   * @return ShoppingCartSku
   */
  public ShoppingCartSku getShoppingCartSkuInfo(ShoppingCart item) {
    LOGGER.debug("app service: get shopping cart sku info for list {}", item);
    ShoppingCartSku skuInfo = getSkuInfoApp.getSkuInfo(item.getSkuId());
    return getSkuInfoApp.organizeShoppingCartSku(item, skuInfo);
  }

}
