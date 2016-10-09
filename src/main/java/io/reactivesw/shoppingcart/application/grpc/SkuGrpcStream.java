package io.reactivesw.shoppingcart.application.grpc;

import io.reactivesw.catalog.grpc.LongValue;
import io.reactivesw.catalog.grpc.SkuIdList;
import io.reactivesw.catalog.grpc.SkuInformation;
import io.reactivesw.catalog.grpc.SkuInformationList;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku;
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
   * convert sku info list to shopping cart sku list.
   * @param skuInfoList List SkuInformation
   * @return List ShoppingCartSku
   */
  public static List<ShoppingCartSku> convertToShoppingCartSkuList(
      SkuInformationList skuInfoList) {
    LOGGER.debug("convert grpc repeated list to shopping cart sku list. repeated list {}",
        skuInfoList);
    List<ShoppingCartSku> scSkuList = new ArrayList<>();
    ModelMapper modelMapper = new ModelMapper();
    List<SkuInformation> listSku = skuInfoList.getSkuInformationList();
    for (SkuInformation prodItem : listSku) {
      // use model mapper to convert java class to grpc message class
      ShoppingCartSku scSku = modelMapper.map(prodItem, ShoppingCartSku.class);
      // repeated shopping cart
      scSkuList.add(scSku);
    }
    return scSkuList;
  }

  /**
   * conver java long to grpc long value.
   * @param value long
   * @return LongValue
   */
  public static LongValue convertToLongValue(long value) {
    LOGGER.debug("convert value {} to grpc long value", value);
    return LongValue.newBuilder().setValue(value).build();
  }

  /**
   * convert sku info to shopping cart sku.
   * @param skuInfo SkuInformation
   * @return builder
   */
  public static ShoppingCartSku convertToShoppingCartSku(
      SkuInformation skuInfo) {
    LOGGER.debug("convert grpc sku info {} to shopping cart sku", skuInfo);
    ModelMapper modelMapper = new ModelMapper();
    // use model mapper to convert java class to grpc message class
    ShoppingCartSku scSku = modelMapper.map(skuInfo, ShoppingCartSku.class);
    LOGGER.debug("converted shopping cart sku", scSku);
    return scSku;
  }

}
