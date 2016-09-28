package io.reactivesw.shoppingcart.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * The add to cart service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: shopping_cart_proto/shopping_cart_service.proto")
public class ShoppingCartGrpc {

  private ShoppingCartGrpc() {}

  public static final String SERVICE_NAME = "ShoppingCart";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.reactivesw.shoppingcart.grpc.AddRequest,
      io.reactivesw.shoppingcart.grpc.AddReply> METHOD_ADD_TO_SHOPPING_CART =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "ShoppingCart", "addToShoppingCart"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.shoppingcart.grpc.AddRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.shoppingcart.grpc.AddReply.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.reactivesw.shoppingcart.grpc.CustomerShoppingCartListRequest,
      io.reactivesw.shoppingcart.grpc.ShoppingCartListReply> METHOD_LIST_SHOPPING_CART_FOR_CUSTOMER =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "ShoppingCart", "listShoppingCartForCustomer"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.shoppingcart.grpc.CustomerShoppingCartListRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.shoppingcart.grpc.ShoppingCartListReply.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.reactivesw.shoppingcart.grpc.SessionShoppingCartListRequest,
      io.reactivesw.shoppingcart.grpc.ShoppingCartListReply> METHOD_LIST_SHOPPING_CART_FOR_SESSION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "ShoppingCart", "listShoppingCartForSession"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.shoppingcart.grpc.SessionShoppingCartListRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.shoppingcart.grpc.ShoppingCartListReply.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ShoppingCartStub newStub(io.grpc.Channel channel) {
    return new ShoppingCartStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ShoppingCartBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ShoppingCartBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static ShoppingCartFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ShoppingCartFutureStub(channel);
  }

  /**
   * <pre>
   * The add to cart service definition.
   * </pre>
   */
  public static abstract class ShoppingCartImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a product to add product to shopping cart service
     * </pre>
     */
    public void addToShoppingCart(io.reactivesw.shoppingcart.grpc.AddRequest request,
        io.grpc.stub.StreamObserver<io.reactivesw.shoppingcart.grpc.AddReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_TO_SHOPPING_CART, responseObserver);
    }

    /**
     * <pre>
     * Lists the shopping cart for customer service
     * </pre>
     */
    public void listShoppingCartForCustomer(io.reactivesw.shoppingcart.grpc.CustomerShoppingCartListRequest request,
        io.grpc.stub.StreamObserver<io.reactivesw.shoppingcart.grpc.ShoppingCartListReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST_SHOPPING_CART_FOR_CUSTOMER, responseObserver);
    }

    /**
     * <pre>
     * Lists the shopping cart for session service
     * </pre>
     */
    public void listShoppingCartForSession(io.reactivesw.shoppingcart.grpc.SessionShoppingCartListRequest request,
        io.grpc.stub.StreamObserver<io.reactivesw.shoppingcart.grpc.ShoppingCartListReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST_SHOPPING_CART_FOR_SESSION, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_ADD_TO_SHOPPING_CART,
            asyncUnaryCall(
              new MethodHandlers<
                io.reactivesw.shoppingcart.grpc.AddRequest,
                io.reactivesw.shoppingcart.grpc.AddReply>(
                  this, METHODID_ADD_TO_SHOPPING_CART)))
          .addMethod(
            METHOD_LIST_SHOPPING_CART_FOR_CUSTOMER,
            asyncUnaryCall(
              new MethodHandlers<
                io.reactivesw.shoppingcart.grpc.CustomerShoppingCartListRequest,
                io.reactivesw.shoppingcart.grpc.ShoppingCartListReply>(
                  this, METHODID_LIST_SHOPPING_CART_FOR_CUSTOMER)))
          .addMethod(
            METHOD_LIST_SHOPPING_CART_FOR_SESSION,
            asyncUnaryCall(
              new MethodHandlers<
                io.reactivesw.shoppingcart.grpc.SessionShoppingCartListRequest,
                io.reactivesw.shoppingcart.grpc.ShoppingCartListReply>(
                  this, METHODID_LIST_SHOPPING_CART_FOR_SESSION)))
          .build();
    }
  }

  /**
   * <pre>
   * The add to cart service definition.
   * </pre>
   */
  public static final class ShoppingCartStub extends io.grpc.stub.AbstractStub<ShoppingCartStub> {
    private ShoppingCartStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ShoppingCartStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShoppingCartStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ShoppingCartStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a product to add product to shopping cart service
     * </pre>
     */
    public void addToShoppingCart(io.reactivesw.shoppingcart.grpc.AddRequest request,
        io.grpc.stub.StreamObserver<io.reactivesw.shoppingcart.grpc.AddReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_TO_SHOPPING_CART, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Lists the shopping cart for customer service
     * </pre>
     */
    public void listShoppingCartForCustomer(io.reactivesw.shoppingcart.grpc.CustomerShoppingCartListRequest request,
        io.grpc.stub.StreamObserver<io.reactivesw.shoppingcart.grpc.ShoppingCartListReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LIST_SHOPPING_CART_FOR_CUSTOMER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Lists the shopping cart for session service
     * </pre>
     */
    public void listShoppingCartForSession(io.reactivesw.shoppingcart.grpc.SessionShoppingCartListRequest request,
        io.grpc.stub.StreamObserver<io.reactivesw.shoppingcart.grpc.ShoppingCartListReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LIST_SHOPPING_CART_FOR_SESSION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The add to cart service definition.
   * </pre>
   */
  public static final class ShoppingCartBlockingStub extends io.grpc.stub.AbstractStub<ShoppingCartBlockingStub> {
    private ShoppingCartBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ShoppingCartBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShoppingCartBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ShoppingCartBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a product to add product to shopping cart service
     * </pre>
     */
    public io.reactivesw.shoppingcart.grpc.AddReply addToShoppingCart(io.reactivesw.shoppingcart.grpc.AddRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_TO_SHOPPING_CART, getCallOptions(), request);
    }

    /**
     * <pre>
     * Lists the shopping cart for customer service
     * </pre>
     */
    public io.reactivesw.shoppingcart.grpc.ShoppingCartListReply listShoppingCartForCustomer(io.reactivesw.shoppingcart.grpc.CustomerShoppingCartListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LIST_SHOPPING_CART_FOR_CUSTOMER, getCallOptions(), request);
    }

    /**
     * <pre>
     * Lists the shopping cart for session service
     * </pre>
     */
    public io.reactivesw.shoppingcart.grpc.ShoppingCartListReply listShoppingCartForSession(io.reactivesw.shoppingcart.grpc.SessionShoppingCartListRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LIST_SHOPPING_CART_FOR_SESSION, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The add to cart service definition.
   * </pre>
   */
  public static final class ShoppingCartFutureStub extends io.grpc.stub.AbstractStub<ShoppingCartFutureStub> {
    private ShoppingCartFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ShoppingCartFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShoppingCartFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ShoppingCartFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a product to add product to shopping cart service
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.reactivesw.shoppingcart.grpc.AddReply> addToShoppingCart(
        io.reactivesw.shoppingcart.grpc.AddRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_TO_SHOPPING_CART, getCallOptions()), request);
    }

    /**
     * <pre>
     * Lists the shopping cart for customer service
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.reactivesw.shoppingcart.grpc.ShoppingCartListReply> listShoppingCartForCustomer(
        io.reactivesw.shoppingcart.grpc.CustomerShoppingCartListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LIST_SHOPPING_CART_FOR_CUSTOMER, getCallOptions()), request);
    }

    /**
     * <pre>
     * Lists the shopping cart for session service
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.reactivesw.shoppingcart.grpc.ShoppingCartListReply> listShoppingCartForSession(
        io.reactivesw.shoppingcart.grpc.SessionShoppingCartListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LIST_SHOPPING_CART_FOR_SESSION, getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_TO_SHOPPING_CART = 0;
  private static final int METHODID_LIST_SHOPPING_CART_FOR_CUSTOMER = 1;
  private static final int METHODID_LIST_SHOPPING_CART_FOR_SESSION = 2;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ShoppingCartImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(ShoppingCartImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_TO_SHOPPING_CART:
          serviceImpl.addToShoppingCart((io.reactivesw.shoppingcart.grpc.AddRequest) request,
              (io.grpc.stub.StreamObserver<io.reactivesw.shoppingcart.grpc.AddReply>) responseObserver);
          break;
        case METHODID_LIST_SHOPPING_CART_FOR_CUSTOMER:
          serviceImpl.listShoppingCartForCustomer((io.reactivesw.shoppingcart.grpc.CustomerShoppingCartListRequest) request,
              (io.grpc.stub.StreamObserver<io.reactivesw.shoppingcart.grpc.ShoppingCartListReply>) responseObserver);
          break;
        case METHODID_LIST_SHOPPING_CART_FOR_SESSION:
          serviceImpl.listShoppingCartForSession((io.reactivesw.shoppingcart.grpc.SessionShoppingCartListRequest) request,
              (io.grpc.stub.StreamObserver<io.reactivesw.shoppingcart.grpc.ShoppingCartListReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_ADD_TO_SHOPPING_CART,
        METHOD_LIST_SHOPPING_CART_FOR_CUSTOMER,
        METHOD_LIST_SHOPPING_CART_FOR_SESSION);
  }

}
