package io.reactivesw.shoppingcart.application.grpc.config;

import io.reactivesw.shoppingcart.application.grpc.SkuGrpcClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by janeli on 9/26/16.
 */
@Configuration
@ConfigurationProperties("grpc.sku")
public class SkuGrpcConfig {

  /**
   * grpc server host.
   */
  private String host;

  /**
   * grpc port.
   */
  private int port;

  /**
   * get grpc server host.
   * @return String
   */
  public String getHost() {
    return host;
  }

  /**
   * set grpc server host.
   * @param host String
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * get grpc port.
   * @return int
   */
  public int getPort() {
    return port;
  }

  /**
   * set grpc port.
   * @param port
   */
  public void setPort(int port) {
    this.port = port;
  }

  /**
   * sku grpc client configration.
   * @return SkuGrpcClient
   */
  @Bean
  public SkuGrpcClient skuGrpcClient() {
    return new SkuGrpcClient(this.getHost(), this.getPort());
  }
}
