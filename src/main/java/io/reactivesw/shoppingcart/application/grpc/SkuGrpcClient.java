package io.reactivesw.shoppingcart.application.grpc;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.reactivesw.catalog.grpc.SkuServiceGrpc;
import io.reactivesw.shoppingcart.infrastructure.exception.ShoppingCartInventoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * This client is for connect to sku grpc service.
 * Created by janeli on 9/26/16.
 */
public class SkuGrpcClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(SkuGrpcClient.class);

  private final ManagedChannel channel;
  private final SkuServiceGrpc.SkuServiceBlockingStub blockingStub;

  /**
   * Construct client connecting to SKU server at {@code host:port}.
   * @param host
   * @param port
   */
  public SkuGrpcClient(String host, int port) {
    // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
    // needing certificates.
    LOGGER.debug("grpc sku client: new channel host {} port {}", host, port);
    channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    blockingStub = SkuServiceGrpc.newBlockingStub(channel);
  }

  /**
   * shut down the client channel.
   * @throws InterruptedException
   */
  public void shutdown() throws InterruptedException {
    LOGGER.debug("grpc sku client: shut down");
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /**
   * get inventory for sku from sku grpc service.
   * @param skuId
   */
  public int getInventoryForSku(long skuId) {
    LOGGER.debug("grpc sku client: get inventory for sku id {}", skuId);
    int inventory = 0;
    try {
      final Int64Value skuIdRequest = Int64Value.newBuilder().setValue(skuId).build();
      Int32Value inventoryResponse = blockingStub.querySkuInventory(skuIdRequest);
      inventory = inventoryResponse.getValue();
      LOGGER.debug("grpc client response inventory: {}", inventory);
    } catch (StatusRuntimeException e) {
      LOGGER.error("grpc client failed: {}", e.getStatus());
      throw new ShoppingCartInventoryException(ShoppingCartInventoryException.PRODUCT_UNAVAILABLE);
    }
    return inventory;
  }

}
