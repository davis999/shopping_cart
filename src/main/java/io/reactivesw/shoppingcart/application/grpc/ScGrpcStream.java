package io.reactivesw.shoppingcart.application.grpc;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartSku;
import io.reactivesw.shoppingcart.grpc.AddRequest;
import io.reactivesw.shoppingcart.grpc.GrpcShoppingCartSku;
import io.reactivesw.shoppingcart.grpc.ShoppingCartListReply;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * supprt grpc request and reply convert.
 * @author janeli
 */
public final class ScGrpcStream {

  /**
   * class logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ScGrpcStream.class);

  /**
   * constructor.
   */
  private ScGrpcStream() {

  }

  /**
   * convert shopping cart list to reply builder.
   * @param scList List ShoppingCartSku
   * @return ShoppingCartListReply
   */
  public static ShoppingCartListReply repeatShoppingCart(List<ShoppingCartSku> scList) {
    LOGGER.debug("convert to grpc repeated list. shopping cart sku list {}", scList);
    final ShoppingCartListReply.Builder replyBuilder =
        ShoppingCartListReply.newBuilder();
    final ModelMapper modelMapper = new ModelMapper();
    for (final ShoppingCartSku scItem : scList) {
      LOGGER.debug("shopping cart sku item {}", scItem);
      if (scItem.getSessionId() == null) {
        scItem.setSessionId("");
      }
      // use model mapper to convert java class to grpc message class
      final GrpcShoppingCartSku.Builder scbuilder =
          modelMapper.map(scItem, GrpcShoppingCartSku.Builder.class);
      final GrpcShoppingCartSku grpcShoppingCartSku = scbuilder.build();
      // repeated shopping cart
      replyBuilder.addShoppingCart(grpcShoppingCartSku);
    }
    return replyBuilder.build();
  }

  /**
   * convert grpc request to shopping cart domain.
   * @param grpcRequest ShoppingCartOuterClass.AddRequest
   * @return shoppingCart
   */
  public static ShoppingCart grpcRequestToShoppingCart(AddRequest grpcRequest) {
    LOGGER.debug("convert grpc request to shopping cart. request: {}", grpcRequest);
    final ModelMapper modelMapper = new ModelMapper();
    final ShoppingCart shoppingCart = modelMapper.map(grpcRequest, ShoppingCart.class);
    LOGGER.debug("convert grpc request to shopping cart. shoppingCart: {}", shoppingCart);
    return shoppingCart;
  }

  /**
   * convert grpc request to shopping cart domain.
   * @param shoppingCartSku ShoppingCartOuterClass.AddRequest
   * @return gShoppingCart
   */
  public static GrpcShoppingCartSku shoppingCartToGrpcReply(ShoppingCartSku shoppingCartSku) {
    LOGGER.debug("convert shopping cart to grpc reply. shoppingCartSku: {}", shoppingCartSku);
    final ModelMapper modelMapper = new ModelMapper();
    if (shoppingCartSku.getSessionId() == null) {
      shoppingCartSku.setSessionId("");
    }
    final GrpcShoppingCartSku.Builder scbuilder =
        modelMapper.map(shoppingCartSku, GrpcShoppingCartSku.Builder.class);
    final GrpcShoppingCartSku gShoppingCartSku = scbuilder.build();
    LOGGER.debug("convert shopping cart to grpc reply. GrpcShoppingCartSku: {}", gShoppingCartSku);
    return gShoppingCartSku;
  }

}
