package io.reactivesw.shoppingcart.infrastructure.grpcservice;

import io.grpc.StatusRuntimeException;
import io.reactivesw.catalog.grpc.IntValue;
import io.reactivesw.catalog.grpc.LongValue;
import io.reactivesw.catalog.grpc.SkuIdList;
import io.reactivesw.catalog.grpc.SkuInformation;
import io.reactivesw.catalog.grpc.SkuInformationList;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException;
import io.reactivesw.shoppingcart.infrastructure.grpcservice.utils.SkuGrpcStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This client is for connect to sku grpc service.
 * Created by janeli on 9/26/16.
 */
@Service
public class SkuGrpcService {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SkuGrpcService.class);

  /**
   * sku grpc client.
   */
  @Autowired
  private transient SkuGrpcClient skuGrpcClient;

  /**
   * get inventory for sku from sku grpc service.
   * @param skuId long
   */
  public int getInventoryForSku(long skuId) {
    LOGGER.debug("grpc sku client: get inventory for sku id {}", skuId);
    int inventory;
    try {
      final LongValue skuIdRequest = SkuGrpcStream.convertToLongValue(skuId);
      IntValue inventoryResponse = skuGrpcClient.blockingStub.querySkuInventory(skuIdRequest);
      inventory = inventoryResponse.getValue();
      LOGGER.debug("grpc client response inventory: {}", inventory);
    } catch (StatusRuntimeException statusEx) {
      LOGGER.error("grpc client failed: {}", statusEx.getStatus());
      throw new ShoppingCartInventoryException(ShoppingCartInventoryException.PRODUCT_UNAVAILABLE);
    }
    return inventory;
  }

  /**
   * get shopping cart sku info.
   * @param skuId long
   * @return ShoppingCartSku
   */
  public ShoppingCartSku getSkuInfo(long skuId) {
    LOGGER.debug("grpc sku client: get sku info by sku id {}", skuId);
    ShoppingCartSku scSku;
    try {
      LongValue skuIdRequest = SkuGrpcStream.convertToLongValue(skuId);
      SkuInformation skuInfo = skuGrpcClient.blockingStub.querySkuSimpleInformation(skuIdRequest);
      LOGGER.debug("grpc client response sku info: {}", skuInfo);
      scSku = SkuGrpcStream.convertToShoppingCartSku(skuInfo);
    } catch (StatusRuntimeException statusEx) {
      LOGGER.error("grpc client failed: {}", statusEx.getStatus());
      throw new ShoppingCartInventoryException(ShoppingCartInventoryException.PRODUCT_UNAVAILABLE);
    }
    return scSku;
  }

  /**
   * get shopping cart sku info list.
   * @param skuIdList List long
   * @return List ShoppingCartSku
   */
  public List<ShoppingCartSku> getSkuInfoList(List<Long> skuIdList) {
    LOGGER.debug("grpc sku client: get sku info list. sku id list {}", skuIdList);
    List<ShoppingCartSku> scSkuList;
    try {
      SkuIdList skuList = SkuGrpcStream.repeatSkuId(skuIdList);
      SkuInformationList skuInfoList = skuGrpcClient.blockingStub.querySkuInformationList(skuList);
      LOGGER.debug("grpc client response sku info list: {}", skuInfoList);
      scSkuList = SkuGrpcStream.convertToShoppingCartSkuList(skuInfoList);
    } catch (StatusRuntimeException statusEx) {
      LOGGER.error("grpc client failed: {}", statusEx.getStatus());
      throw new ShoppingCartInventoryException(ShoppingCartInventoryException.PRODUCT_UNAVAILABLE);
    }
    return scSkuList;
  }

}
