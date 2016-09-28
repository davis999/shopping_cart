package io.reactivesw.catalog.grpc;

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
 **
 *  product rpc service.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: catalog_proto/catalog_service.proto")
public class ProductServiceGrpc {

  private ProductServiceGrpc() {}

  public static final String SERVICE_NAME = "io.reactivesw.catalog.infrastructure.ProductService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.protobuf.Int64Value,
      io.reactivesw.catalog.grpc.ProductBriefList> METHOD_GET_PRODUCTS_BY_CATEGORY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "io.reactivesw.catalog.infrastructure.ProductService", "getProductsByCategory"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.protobuf.Int64Value.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.catalog.grpc.ProductBriefList.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.protobuf.Int64Value,
      io.reactivesw.catalog.grpc.GrpcProduct> METHOD_GET_PRODUCT_DETIAL =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "io.reactivesw.catalog.infrastructure.ProductService", "getProductDetial"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.protobuf.Int64Value.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.catalog.grpc.GrpcProduct.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductServiceStub newStub(io.grpc.Channel channel) {
    return new ProductServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ProductServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static ProductServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ProductServiceFutureStub(channel);
  }

  /**
   * <pre>
   **
   *  product rpc service.
   * </pre>
   */
  public static abstract class ProductServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *query all products by id of category
     * </pre>
     */
    public void getProductsByCategory(com.google.protobuf.Int64Value request,
        io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.ProductBriefList> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PRODUCTS_BY_CATEGORY, responseObserver);
    }

    /**
     * <pre>
     *query product's detail by id of product
     * </pre>
     */
    public void getProductDetial(com.google.protobuf.Int64Value request,
        io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.GrpcProduct> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_PRODUCT_DETIAL, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_PRODUCTS_BY_CATEGORY,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Int64Value,
                io.reactivesw.catalog.grpc.ProductBriefList>(
                  this, METHODID_GET_PRODUCTS_BY_CATEGORY)))
          .addMethod(
            METHOD_GET_PRODUCT_DETIAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Int64Value,
                io.reactivesw.catalog.grpc.GrpcProduct>(
                  this, METHODID_GET_PRODUCT_DETIAL)))
          .build();
    }
  }

  /**
   * <pre>
   **
   *  product rpc service.
   * </pre>
   */
  public static final class ProductServiceStub extends io.grpc.stub.AbstractStub<ProductServiceStub> {
    private ProductServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProductServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProductServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *query all products by id of category
     * </pre>
     */
    public void getProductsByCategory(com.google.protobuf.Int64Value request,
        io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.ProductBriefList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCTS_BY_CATEGORY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *query product's detail by id of product
     * </pre>
     */
    public void getProductDetial(com.google.protobuf.Int64Value request,
        io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.GrpcProduct> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_DETIAL, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   **
   *  product rpc service.
   * </pre>
   */
  public static final class ProductServiceBlockingStub extends io.grpc.stub.AbstractStub<ProductServiceBlockingStub> {
    private ProductServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProductServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProductServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *query all products by id of category
     * </pre>
     */
    public io.reactivesw.catalog.grpc.ProductBriefList getProductsByCategory(com.google.protobuf.Int64Value request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PRODUCTS_BY_CATEGORY, getCallOptions(), request);
    }

    /**
     * <pre>
     *query product's detail by id of product
     * </pre>
     */
    public io.reactivesw.catalog.grpc.GrpcProduct getProductDetial(com.google.protobuf.Int64Value request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_PRODUCT_DETIAL, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   **
   *  product rpc service.
   * </pre>
   */
  public static final class ProductServiceFutureStub extends io.grpc.stub.AbstractStub<ProductServiceFutureStub> {
    private ProductServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ProductServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ProductServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *query all products by id of category
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.reactivesw.catalog.grpc.ProductBriefList> getProductsByCategory(
        com.google.protobuf.Int64Value request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCTS_BY_CATEGORY, getCallOptions()), request);
    }

    /**
     * <pre>
     *query product's detail by id of product
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.reactivesw.catalog.grpc.GrpcProduct> getProductDetial(
        com.google.protobuf.Int64Value request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_PRODUCT_DETIAL, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCTS_BY_CATEGORY = 0;
  private static final int METHODID_GET_PRODUCT_DETIAL = 1;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductServiceImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(ProductServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCTS_BY_CATEGORY:
          serviceImpl.getProductsByCategory((com.google.protobuf.Int64Value) request,
              (io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.ProductBriefList>) responseObserver);
          break;
        case METHODID_GET_PRODUCT_DETIAL:
          serviceImpl.getProductDetial((com.google.protobuf.Int64Value) request,
              (io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.GrpcProduct>) responseObserver);
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
        METHOD_GET_PRODUCTS_BY_CATEGORY,
        METHOD_GET_PRODUCT_DETIAL);
  }

}
