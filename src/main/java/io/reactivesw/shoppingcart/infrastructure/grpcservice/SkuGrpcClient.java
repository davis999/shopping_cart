package io.reactivesw.shoppingcart.infrastructure.grpcservice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.reactivesw.catalog.grpc.IntValue;
import io.reactivesw.catalog.grpc.LongValue;
import io.reactivesw.catalog.grpc.SkuIdList;
import io.reactivesw.catalog.grpc.SkuInformation;
import io.reactivesw.catalog.grpc.SkuInformationList;
import io.reactivesw.catalog.grpc.SkuServiceGrpc;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This client is for connect to sku grpc service.
 * Created by janeli on 9/26/16.
 */
public class SkuGrpcClient {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SkuGrpcClient.class);

  /**
   * message channel.
   */
  private final transient ManagedChannel channel;

  /**
   * sku grpc service.
   */
  private final transient SkuServiceGrpc.SkuServiceBlockingStub blockingStub;

  /**
   * Construct client connecting to SKU server at {@code host:port}.
   * @param host String server host
   * @param port int port
   */
  public SkuGrpcClient(String host, int port) {
    // Channels are secure by default (via SSL/TLS).
    LOGGER.debug("grpc sku client: new channel host {} port {}", host, port);
    // For the example we disable TLS to avoid needing certificates.
    channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    blockingStub = SkuServiceGrpc.newBlockingStub(channel);
  }

  /**
   * shut down the client channel.
   * @throws InterruptedException Exception
   */
  public void shutdown() throws InterruptedException {
    LOGGER.debug("grpc sku client: shut down");
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /**
   * get inventory for sku from sku grpc service.
   * @param skuId long
   */
  public int getInventoryForSku(long skuId) {
    LOGGER.debug("grpc sku client: get inventory for sku id {}", skuId);
    int inventory;
    try {
      final LongValue skuIdRequest = SkuGrpcStream.convertToLongValue(skuId);
      IntValue inventoryResponse = blockingStub.querySkuInventory(skuIdRequest);
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
      SkuInformation skuInfo = blockingStub.querySkuSimpleInformation(skuIdRequest);
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
      SkuInformationList skuInfoList = blockingStub.querySkuInformationList(skuList);
      LOGGER.debug("grpc client response sku info list: {}", skuInfoList);
      scSkuList = SkuGrpcStream.convertToShoppingCartSkuList(skuInfoList);
    } catch (StatusRuntimeException statusEx) {
      LOGGER.error("grpc client failed: {}", statusEx.getStatus());
      throw new ShoppingCartInventoryException(ShoppingCartInventoryException.PRODUCT_UNAVAILABLE);
    }
    return scSkuList;
  }

}
