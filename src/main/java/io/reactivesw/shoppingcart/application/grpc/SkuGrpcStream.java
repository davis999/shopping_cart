package io.reactivesw.shoppingcart.application.grpc;

import io.reactivesw.catalog.grpc.SkuIdList;
import io.reactivesw.catalog.grpc.SkuInformation;
import io.reactivesw.catalog.grpc.SkuInformationList;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartProduct;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * supprt grpc request and reply convert.
 * @author janeli
 */
public final class SkuGrpcStream {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SkuGrpcStream.class);

  /**
   * constructor.
   */
  private SkuGrpcStream() {

  }

  /**
   * convert sku id list to grpc repeated list.
   * @param skuIdList List Long
   * @return SkuIdList
   */
  public static SkuIdList repeatSkuId(List<Long> skuIdList) {
    LOGGER.debug("grpc repeated sku id list. sku id: {}", skuIdList);
    SkuIdList.Builder requestBuilder = SkuIdList.newBuilder();
    for (Long skuId : skuIdList) {
      requestBuilder.addSkuId(skuId);
    }
    return requestBuilder.build();
  }

  /**
   * convert sku info list to shopping cart product list.
   * @param skuInfoList List SkuInformation
   * @return builder
   */
  public static List<ShoppingCartProduct> convertToShoppingCartProduct(
      SkuInformationList skuInfoList) {
    LOGGER.debug("convert grpc repeated list to shopping cart product list. repeated list {}",
        skuInfoList);
    List<ShoppingCartProduct> scProductList = new ArrayList<>();
    ModelMapper modelMapper = new ModelMapper();
    List<SkuInformation> listProduct = modelMapper.map(skuInfoList, List.class);
    for (SkuInformation prodItem : listProduct) {
      // use model mapper to convert java class to grpc message class
      ShoppingCartProduct scProduct = modelMapper.map(prodItem, ShoppingCartProduct.class);
      // repeated shopping cart
      scProductList.add(scProduct);
    }
    return scProductList;
  }

}
