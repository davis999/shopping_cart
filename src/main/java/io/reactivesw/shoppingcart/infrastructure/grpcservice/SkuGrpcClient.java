package io.reactivesw.shoppingcart.infrastructure.grpcservice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.reactivesw.catalog.grpc.SkuServiceGrpc;
import io.reactivesw.shoppingcart.infrastructure.grpcservice.config.SkuGrpcConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * This client is for connect to sku grpc service.
 * Created by janeli on 9/26/16.
 */
@Service
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
  public final transient SkuServiceGrpc.SkuServiceBlockingStub blockingStub;

  /**
   * Construct client connecting to SKU server at {@code host:port}.
   */
  public SkuGrpcClient(@Autowired SkuGrpcConfig skuGrpcConfig) {
    String host = skuGrpcConfig.getHost();
    int port = skuGrpcConfig.getPort();
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

}
