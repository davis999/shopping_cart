package io.reactivesw.shoppingcart.infrastructure.grpcservice;

import io.grpc.stub.StreamObserver;
import io.reactivesw.shoppingcart.application.DeleteItemApp;
import io.reactivesw.shoppingcart.grpc.CustomerRequest;
import io.reactivesw.shoppingcart.grpc.CustomerSkuRequest;
import io.reactivesw.shoppingcart.grpc.DeleteReply;
import io.reactivesw.shoppingcart.grpc.SessionRequest;
import io.reactivesw.shoppingcart.grpc.SessionSkuRequest;
import io.reactivesw.shoppingcart.grpc.ShoppingCartDeleteServiceGrpc;
import io.reactivesw.shoppingcart.infrastructure.grpcservice.utils.ScGrpcUtility;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * grpc server that convert grpc request and add product to shopping cart service.
 * @author janeli
 */
@GRpcService
public class ScGrpcDeleteService
    extends ShoppingCartDeleteServiceGrpc.ShoppingCartDeleteServiceImplBase {

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
   * @param request ShoppingCartOuterClass.CustomerSkuRequest
   * @param responseObserver StreamObserver DeleteReply
   */
  @Override
  public void deleteSkuForCustomer(CustomerSkuRequest request,
                                   StreamObserver<DeleteReply> responseObserver) {
    long customerId = request.getCustomerId();
    long skuId = request.getSkuId();
    LOGGER.debug("grpc server: delete sku {} from shopping cart by customer {}", skuId, customerId);
    boolean deleted = deleteItemApp.deleteSkuByCustomerId(customerId, skuId);

    // convert shopping cart list to reply builder
    DeleteReply replyMessage = DeleteReply.newBuilder().setDeleted(deleted).build();
    ScGrpcUtility.completeResponse(responseObserver, replyMessage);
    LOGGER.debug("grpc server: delete shopping cart by customer finished, reply: {}", replyMessage);
  }

  /**
   * delete customer shopping cart and reply to grpc client.
   * @param request ShoppingCartOuterClass.CustomerRequest
   * @param responseObserver StreamObserver DeleteReply
   */
  @Override
  public void deleteForCustomer(CustomerRequest request,
                                StreamObserver<DeleteReply> responseObserver) {
    long customerId = request.getCustomerId();
    LOGGER.debug("grpc server: delete shopping cart by customer {}", customerId);
    boolean deleted = deleteItemApp.deleteByCustomerId(customerId);

    // convert shopping cart list to reply builder
    DeleteReply replyMessage = DeleteReply.newBuilder().setDeleted(deleted).build();
    ScGrpcUtility.completeResponse(responseObserver, replyMessage);
    LOGGER.debug("grpc server: delete customer shopping cart finished, reply: {}", replyMessage);
  }

  /**
   * delete sku from session shopping cart and reply to grpc client.
   * @param request ShoppingCartOuterClass.SessionSkuRequest
   * @param responseObserver StreamObserver DeleteReply
   */
  @Override
  public void deleteSkuForSession(SessionSkuRequest request,
                                  StreamObserver<DeleteReply> responseObserver) {
    String sessionId = request.getSessionId();
    long skuId = request.getSkuId();
    LOGGER.debug("grpc server: delete sku {} from shopping cart by session {}", skuId, sessionId);
    boolean deleted = deleteItemApp.deleteSkuBySessionId(sessionId, skuId);

    // convert shopping cart list to reply builder
    DeleteReply replyMessage = DeleteReply.newBuilder().setDeleted(deleted).build();
    ScGrpcUtility.completeResponse(responseObserver, replyMessage);
    LOGGER.debug("grpc server: delete shopping cart for session finished, reply: {}", replyMessage);
  }

  /**
   * delete session shopping cart and reply to grpc client.
   * @param request ShoppingCartOuterClass.SessionRequest
   * @param responseObserver StreamObserver DeleteReply
   */
  @Override
  public void deleteForSession(SessionRequest request,
                               StreamObserver<DeleteReply> responseObserver) {
    String sessionId = request.getSessionId();
    LOGGER.debug("grpc server: delete shopping cart by session {}", sessionId);
    boolean deleted = deleteItemApp.deleteBySessionId(sessionId);

    // convert shopping cart list to reply builder
    DeleteReply replyMessage = DeleteReply.newBuilder().setDeleted(deleted).build();
    ScGrpcUtility.completeResponse(responseObserver, replyMessage);
    LOGGER.debug("grpc server: delete shopping cart for session finished, reply: {}", replyMessage);
  }

}
