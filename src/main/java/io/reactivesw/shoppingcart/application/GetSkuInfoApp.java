package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.application.grpc.ScGrpcAddService;
import io.reactivesw.shoppingcart.application.grpc.SkuGrpcClient;
import io.reactivesw.shoppingcart.application.grpc.config.SkuGrpcConfig;
import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku;
import io.reactivesw.shoppingcart.infrastructure.common.ConstantsUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * business service for shopping cart list.
 * @author janeli
 */
@Service
public class GetSkuInfoApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScGrpcAddService.class);

  /**
   * grpc configuration.
   */
  @Autowired
  private transient SkuGrpcConfig skuGrpcConfig;

  /**
   * get sku info.
   * @param skuId long
   * @return ShoppingCartSku
   */
  public ShoppingCartSku getSkuInfo(long skuId) {
    LOGGER.debug("app service: get sku info for sku id {}", skuId);
    SkuGrpcClient skuClient = skuGrpcConfig.skuGrpcClient();
    return skuClient.getSkuInfo(skuId);
  }

  /**
   * get sku info list.
   * @param skuIdList List long
   * @return List ShoppingCartSku
   */
  public List<ShoppingCartSku> getSkuInfoList(List<Long> skuIdList) {
    LOGGER.debug("app service: get sku info for sku id list {}", skuIdList);
    SkuGrpcClient skuClient = skuGrpcConfig.skuGrpcClient();
    return skuClient.getSkuInfoList(skuIdList);
  }

  /**
   * organize the shopping cart sku info.
   * @param sc ShoppingCart
   * @param scProduct ShoppingCartSku
   * @return ShoppingCartSku
   */
  public ShoppingCartSku organizeShoppingCartSku(ShoppingCart sc,
                                                 ShoppingCartSku scProduct) {
    LOGGER.debug("app service: get shopping cart product info.");
    scProduct.setQuantity(sc.getQuantity());
    scProduct.setShoppingCartId(sc.getShoppingCartId());
    if (sc.getCustomerId() == ConstantsUtility.INVALID_CUSTOMER_ID) {
      LOGGER.debug("app service: session id is {}", sc.getSessionId());
      scProduct.setSessionId(sc.getSessionId());
    } else {
      LOGGER.debug("app service: customer id is {}", sc.getCustomerId());
      scProduct.setCustomerId(sc.getCustomerId());
    }
    LOGGER.debug("app service: shopping cart product info {}", scProduct);
    return scProduct;
  }

  /**
   * organize the shopping cart sku info list.
   * @param shoppingCartList List ShoppingCart
   * @param skuInfoList List ShoppingCartSku
   * @return List ShoppingCartSku
   */
  public List<ShoppingCartSku> organizeShoppingCartSkuList(List<ShoppingCart> shoppingCartList,
                                                           List<ShoppingCartSku> skuInfoList) {
    LOGGER.debug("app service: get shopping cart product info list.");
    LOGGER.debug("app service: shopping cart list {} and sku info list {}",
        shoppingCartList, skuInfoList);
    List<ShoppingCartSku> scProductList = new ArrayList<>();
    for (ShoppingCartSku scProduct : skuInfoList) {
      LOGGER.debug("app service: get shopping cart product info use sku info {}", scProduct);
      shoppingCartList.stream()
          .filter(sc -> scProduct.getSkuId() == sc.getSkuId())
          .forEach(sc -> {
            LOGGER.debug("app service: shopping cart object {}", sc);
            ShoppingCartSku scpOrg = this.organizeShoppingCartSku(sc, scProduct);
            LOGGER.debug("app service: shopping cart product info {}", scpOrg);
            scProductList.add(scpOrg);
          });

    }
    LOGGER.debug("app service: shopping cart product list {}", scProductList);
    return scProductList;
  }

}
