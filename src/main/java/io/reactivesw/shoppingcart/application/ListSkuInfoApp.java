package io.reactivesw.shoppingcart.application;

import io.reactivesw.shoppingcart.application.grpc.ScGrpcService;
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
import java.util.stream.Collectors;

/**
 * business service for shopping cart list.
 * @author janeli
 */
@Service
public class ListSkuInfoApp {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScGrpcService.class);

  /**
   * grpc configuration.
   */
  @Autowired
  private transient SkuGrpcConfig skuGrpcConfig;

  /**
   * get sku info for shopping cart.
   * @param itemList List ShoppingCart
   * @return List
   */
  public List<ShoppingCartSku> listShoppingCartSkuInfo(List<ShoppingCart> itemList) {
    LOGGER.debug("app service: get product info list for shopping cart list {}", itemList);
    List<Long> skuIdList = this.getSkuIdList(itemList);
    List<ShoppingCartSku> skuInfoList = this.getSkuInfoList(skuIdList);
    return this.listShoppingCartSku(itemList, skuInfoList);
  }

  /**
   * get sku id list.
   * @param itemList ShoppingCart List
   * @return List long
   */
  private List getSkuIdList(List<ShoppingCart> itemList) {
    LOGGER.debug("app service: get sku id list of shopping cart list {}", itemList);
    List<Long> skuIdList =
        itemList.stream().map(ShoppingCart::getSkuId).collect(Collectors.toList());
    LOGGER.debug("app service: sku id list {}", skuIdList);
    return skuIdList;
  }

  /**
   * get sku info list.
   * @param skuIdList List long
   * @return List ShoppingCart
   */
  private List<ShoppingCartSku> getSkuInfoList(List<Long> skuIdList) {
    LOGGER.debug("app service: get sku info for sku id list {}", skuIdList);
    SkuGrpcClient skuClient = skuGrpcConfig.skuGrpcClient();
    return skuClient.getSkuInfoList(skuIdList);
  }

  /**
   * generate shopping cart list.
   * @param shoppingCartList List ShoppingCart
   * @param skuInfoList List ShoppingCartSku
   * @return List ShoppingCartSku
   */
  private List<ShoppingCartSku> listShoppingCartSku(List<ShoppingCart> shoppingCartList,
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

  /**
   * organize the shopping cart product info.
   * @param sc ShoppingCart
   * @param scProduct ShoppingCartSku
   * @return ShoppingCartSku
   */
  private ShoppingCartSku organizeShoppingCartSku(ShoppingCart sc,
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

}
