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
 * sku rpc service.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: catalog_proto/catalog_sku_service.proto")
public class SkuServiceGrpc {

  private SkuServiceGrpc() {}

  public static final String SERVICE_NAME = "io.reactivesw.catalog.infrastructure.SkuService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.protobuf.Int64Value,
      com.google.protobuf.Int32Value> METHOD_QUERY_SKU_INVENTORY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "io.reactivesw.catalog.infrastructure.SkuService", "querySkuInventory"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.protobuf.Int64Value.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.protobuf.Int32Value.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.protobuf.Int64Value,
      io.reactivesw.catalog.grpc.SkuInformation> METHOD_QUERY_SKU_SIMPLE_INFORMATION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "io.reactivesw.catalog.infrastructure.SkuService", "querySkuSimpleInformation"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.protobuf.Int64Value.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.catalog.grpc.SkuInformation.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.reactivesw.catalog.grpc.SkuIdList,
      io.reactivesw.catalog.grpc.SkuInformationList> METHOD_QUERY_SKU_INFORMATION_LIST =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "io.reactivesw.catalog.infrastructure.SkuService", "querySkuInformationList"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.catalog.grpc.SkuIdList.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.reactivesw.catalog.grpc.SkuInformationList.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SkuServiceStub newStub(io.grpc.Channel channel) {
    return new SkuServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SkuServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SkuServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static SkuServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SkuServiceFutureStub(channel);
  }

  /**
   * <pre>
   **
   * sku rpc service.
   * </pre>
   */
  public static abstract class SkuServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *query sku inventory by sku id.
     * </pre>
     */
    public void querySkuInventory(com.google.protobuf.Int64Value request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Int32Value> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_QUERY_SKU_INVENTORY, responseObserver);
    }

    /**
     * <pre>
     *query sku simple information.
     * </pre>
     */
    public void querySkuSimpleInformation(com.google.protobuf.Int64Value request,
        io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.SkuInformation> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_QUERY_SKU_SIMPLE_INFORMATION, responseObserver);
    }

    /**
     * <pre>
     *query list sku information.
     * </pre>
     */
    public void querySkuInformationList(io.reactivesw.catalog.grpc.SkuIdList request,
        io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.SkuInformationList> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_QUERY_SKU_INFORMATION_LIST, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_QUERY_SKU_INVENTORY,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Int64Value,
                com.google.protobuf.Int32Value>(
                  this, METHODID_QUERY_SKU_INVENTORY)))
          .addMethod(
            METHOD_QUERY_SKU_SIMPLE_INFORMATION,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Int64Value,
                io.reactivesw.catalog.grpc.SkuInformation>(
                  this, METHODID_QUERY_SKU_SIMPLE_INFORMATION)))
          .addMethod(
            METHOD_QUERY_SKU_INFORMATION_LIST,
            asyncUnaryCall(
              new MethodHandlers<
                io.reactivesw.catalog.grpc.SkuIdList,
                io.reactivesw.catalog.grpc.SkuInformationList>(
                  this, METHODID_QUERY_SKU_INFORMATION_LIST)))
          .build();
    }
  }

  /**
   * <pre>
   **
   * sku rpc service.
   * </pre>
   */
  public static final class SkuServiceStub extends io.grpc.stub.AbstractStub<SkuServiceStub> {
    private SkuServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SkuServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkuServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SkuServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *query sku inventory by sku id.
     * </pre>
     */
    public void querySkuInventory(com.google.protobuf.Int64Value request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Int32Value> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_QUERY_SKU_INVENTORY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *query sku simple information.
     * </pre>
     */
    public void querySkuSimpleInformation(com.google.protobuf.Int64Value request,
        io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.SkuInformation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_QUERY_SKU_SIMPLE_INFORMATION, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *query list sku information.
     * </pre>
     */
    public void querySkuInformationList(io.reactivesw.catalog.grpc.SkuIdList request,
        io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.SkuInformationList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_QUERY_SKU_INFORMATION_LIST, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   **
   * sku rpc service.
   * </pre>
   */
  public static final class SkuServiceBlockingStub extends io.grpc.stub.AbstractStub<SkuServiceBlockingStub> {
    private SkuServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SkuServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkuServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SkuServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *query sku inventory by sku id.
     * </pre>
     */
    public com.google.protobuf.Int32Value querySkuInventory(com.google.protobuf.Int64Value request) {
      return blockingUnaryCall(
          getChannel(), METHOD_QUERY_SKU_INVENTORY, getCallOptions(), request);
    }

    /**
     * <pre>
     *query sku simple information.
     * </pre>
     */
    public io.reactivesw.catalog.grpc.SkuInformation querySkuSimpleInformation(com.google.protobuf.Int64Value request) {
      return blockingUnaryCall(
          getChannel(), METHOD_QUERY_SKU_SIMPLE_INFORMATION, getCallOptions(), request);
    }

    /**
     * <pre>
     *query list sku information.
     * </pre>
     */
    public io.reactivesw.catalog.grpc.SkuInformationList querySkuInformationList(io.reactivesw.catalog.grpc.SkuIdList request) {
      return blockingUnaryCall(
          getChannel(), METHOD_QUERY_SKU_INFORMATION_LIST, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   **
   * sku rpc service.
   * </pre>
   */
  public static final class SkuServiceFutureStub extends io.grpc.stub.AbstractStub<SkuServiceFutureStub> {
    private SkuServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SkuServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkuServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SkuServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *query sku inventory by sku id.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Int32Value> querySkuInventory(
        com.google.protobuf.Int64Value request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_QUERY_SKU_INVENTORY, getCallOptions()), request);
    }

    /**
     * <pre>
     *query sku simple information.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.reactivesw.catalog.grpc.SkuInformation> querySkuSimpleInformation(
        com.google.protobuf.Int64Value request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_QUERY_SKU_SIMPLE_INFORMATION, getCallOptions()), request);
    }

    /**
     * <pre>
     *query list sku information.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.reactivesw.catalog.grpc.SkuInformationList> querySkuInformationList(
        io.reactivesw.catalog.grpc.SkuIdList request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_QUERY_SKU_INFORMATION_LIST, getCallOptions()), request);
    }
  }

  private static final int METHODID_QUERY_SKU_INVENTORY = 0;
  private static final int METHODID_QUERY_SKU_SIMPLE_INFORMATION = 1;
  private static final int METHODID_QUERY_SKU_INFORMATION_LIST = 2;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SkuServiceImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(SkuServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUERY_SKU_INVENTORY:
          serviceImpl.querySkuInventory((com.google.protobuf.Int64Value) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Int32Value>) responseObserver);
          break;
        case METHODID_QUERY_SKU_SIMPLE_INFORMATION:
          serviceImpl.querySkuSimpleInformation((com.google.protobuf.Int64Value) request,
              (io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.SkuInformation>) responseObserver);
          break;
        case METHODID_QUERY_SKU_INFORMATION_LIST:
          serviceImpl.querySkuInformationList((io.reactivesw.catalog.grpc.SkuIdList) request,
              (io.grpc.stub.StreamObserver<io.reactivesw.catalog.grpc.SkuInformationList>) responseObserver);
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
        METHOD_QUERY_SKU_INVENTORY,
        METHOD_QUERY_SKU_SIMPLE_INFORMATION,
        METHOD_QUERY_SKU_INFORMATION_LIST);
  }

}
