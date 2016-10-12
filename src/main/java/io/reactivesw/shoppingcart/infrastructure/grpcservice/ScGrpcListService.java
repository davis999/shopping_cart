package io.reactivesw.shoppingcart.infrastructure.grpcservice;

import io.grpc.stub.StreamObserver;
import io.reactivesw.shoppingcart.application.ListItemsApp;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku;
import io.reactivesw.shoppingcart.grpc.CustomerRequest;
import io.reactivesw.shoppingcart.grpc.SessionRequest;
import io.reactivesw.shoppingcart.grpc.ShoppingCartListReply;
import io.reactivesw.shoppingcart.grpc.ShoppingCartServiceGrpc;
import io.reactivesw.shoppingcart.infrastructure.grpcservice.utils.ScGrpcStream;
import io.reactivesw.shoppingcart.infrastructure.grpcservice.utils.ScGrpcUtility;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * grpc server that convert grpc request and add product to shopping cart service.
 * @author janeli
 */
@GRpcService
public class ScGrpcListService extends ShoppingCartServiceGrpc.ShoppingCartServiceImplBase {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScGrpcListService.class);

  /**
   * list shopping cart application.
   */
  @Autowired
  private transient ListItemsApp listItemsApp;

  /**
   * list customer shopping cart and reply to grpc client.
   * @param request ShoppingCartOuterClass.CustomerRequest
   * @param responseObserver StreamObserver ShoppingCartListReply
   */
  @Override
  public void listShoppingCartForCustomer(CustomerRequest request,
                                          StreamObserver<ShoppingCartListReply> responseObserver) {
    LOGGER.debug("grpc server: list shopping cart for customer. request: {}", request);
    List<ShoppingCartSku> cartList = listItemsApp.listByCustomerId(request.getCustomerId());
    LOGGER.debug("list shopping cart for customer. shopping cart list: {}", cartList);

    // convert shopping cart list to reply builder
    ShoppingCartListReply replyMessage = ScGrpcStream.repeatShoppingCart(cartList);
    ScGrpcUtility.completeResponse(responseObserver, replyMessage);
    LOGGER.debug("grpc server: list shopping cart for customer finished, reply: {}", replyMessage);
  }

  /**
   * list session shopping cart and reply to grpc client.
   * @param request ShoppingCartOuterClass.SessionListRequest
   * @param responseObserver StreamObserver ShoppingCartListReply
   */
  @Override
  public void listShoppingCartForSession(SessionRequest request,
                                         StreamObserver<ShoppingCartListReply> responseObserver) {
    LOGGER.debug("grpc server: list shopping cart for session. request: {}", request);
    List<ShoppingCartSku> cartList = listItemsApp.listBySessionId(request.getSessionId());
    LOGGER.debug("list shopping cart for session. shopping cart list: {}", cartList);

    // convert shopping cart list to reply builder
    ShoppingCartListReply replyMessage = ScGrpcStream.repeatShoppingCart(cartList);
    ScGrpcUtility.completeResponse(responseObserver, replyMessage);
    LOGGER.debug("grpc server: list shopping cart for session finished, reply: {}", replyMessage);
  }

}
