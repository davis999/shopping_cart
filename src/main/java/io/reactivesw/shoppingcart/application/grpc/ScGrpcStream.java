package io.reactivesw.shoppingcart.application.grpc;

import io.reactivesw.shoppingcart.domain.model.ShoppingCart;
import io.reactivesw.shoppingcart.domain.model.ShoppingCartProduct;
import io.reactivesw.shoppingcart.grpc.AddRequest;
import io.reactivesw.shoppingcart.grpc.GrpcShoppingCart;
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
   * @param scList List ShoppingCartProduct
   * @return builder
   */
  public static ShoppingCartListReply.Builder repeatShoppingCart(List<ShoppingCartProduct> scList) {
    LOGGER.debug("convert to grpc repeated list. shopping cart product list {}", scList);
    final ShoppingCartListReply.Builder replyBuilder =
        ShoppingCartListReply.newBuilder();
    final ModelMapper modelMapper = new ModelMapper();
    for (final ShoppingCartProduct scItem : scList) {
      LOGGER.debug("shopping cart product item {}", scItem);
      // use model mapper to convert java class to grpc message class
      final GrpcShoppingCart.Builder scbuilder =
          modelMapper.map(scItem, GrpcShoppingCart.Builder.class);
      final GrpcShoppingCart grpcShoppingCart = scbuilder.build();
      // repeated shopping cart
      replyBuilder.addShoppingCart(grpcShoppingCart);
    }
    return replyBuilder;
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
   * @param shoppingCart ShoppingCartOuterClass.AddRequest
   * @return gShoppingCart
   */
  public static GrpcShoppingCart shoppingCartToGrpcReply(ShoppingCart shoppingCart) {
    LOGGER.debug("convert shopping cart to grpc reply. shoppingCart: {}", shoppingCart);
    final ModelMapper modelMapper = new ModelMapper();
    final GrpcShoppingCart.Builder scbuilder =
        modelMapper.map(shoppingCart, GrpcShoppingCart.Builder.class);
    final GrpcShoppingCart gShoppingCart = scbuilder.build();
    LOGGER.debug("convert shopping cart to grpc reply. GrpcShoppingCart: {}", gShoppingCart);
    return gShoppingCart;
  }

}