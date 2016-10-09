package io.reactivesw.shoppingcart.application.grpc;

import io.grpc.stub.StreamObserver;
import io.reactivesw.shoppingcart.application.DeleteItemApp;
import io.reactivesw.shoppingcart.grpc.CustomerSkuDeleteRequest;
import io.reactivesw.shoppingcart.grpc.SessionSkuDeleteRequest;
import io.reactivesw.shoppingcart.grpc.ShoppingCartServiceGrpc;
import io.reactivesw.shoppingcart.grpc.SkuDeleteReply;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * grpc server that convert grpc request and add product to shopping cart service.
 * @author janeli
 */
@GRpcService
public class ScGrpcDeleteService extends ShoppingCartServiceGrpc.ShoppingCartServiceImplBase {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScGrpcDeleteService.class);

  /**
   * delete shopping cart application.
   */
  @Autowired
  private transient DeleteItemApp deleteItemApp;

  /**
   * delete sku from customer shopping cart and reply to grpc client.
   * @param request ShoppingCartOuterClass.CustomerSkuDeleteRequest
   * @param responseObserver StreamObserver SkuDeleteReply
   */
  @Override
  public void deleteSkuForCustomer(CustomerSkuDeleteRequest request,
                                   StreamObserver<SkuDeleteReply> responseObserver) {
    long customerId = request.getCustomerId();
    long skuId = request.getSkuId();
    LOGGER.debug("grpc server: delete sku {} from shopping cart by customer {}", skuId, customerId);
    boolean deleted = deleteItemApp.deleteSkuByCustomerId(customerId, skuId);
    // convert shopping cart list to reply builder
    SkuDeleteReply replyMessage = SkuDeleteReply.newBuilder().setDeleted(deleted).build();
    ScGrpcUtility.completeResponse(responseObserver, replyMessage);
    LOGGER.debug("grpc server: list shopping cart for customer finished, reply: {}", replyMessage);
  }

  /**
   * delete sku from session shopping cart and reply to grpc client.
   * @param request ShoppingCartOuterClass.SessionSkuDeleteRequest
   * @param responseObserver StreamObserver SkuDeleteReply
   */
  @Override
  public void deleteSkuForSession(SessionSkuDeleteRequest request,
                                  StreamObserver<SkuDeleteReply> responseObserver) {
    String sessionId = request.getSessionId();
    long skuId = request.getSkuId();
    LOGGER.debug("grpc server: delete sku {} from shopping cart by session {}", skuId, sessionId);
    boolean deleted = deleteItemApp.deleteSkuBySessionId(sessionId, skuId);
    // convert shopping cart list to reply builder
    SkuDeleteReply replyMessage = SkuDeleteReply.newBuilder().setDeleted(deleted).build();
    ScGrpcUtility.completeResponse(responseObserver, replyMessage);
    LOGGER.debug("grpc server: list shopping cart for session finished, reply: {}", replyMessage);
  }

}
