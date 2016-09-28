// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: catalog_proto/catalog_sku_message.proto

package io.reactivesw.catalog.grpc;

/**
 * <pre>
 **
 *response when call querySkuInformationList.
 * </pre>
 *
 * Protobuf type {@code io.reactivesw.catalog.infrastructure.SkuInformationList}
 */
public  final class SkuInformationList extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:io.reactivesw.catalog.infrastructure.SkuInformationList)
    SkuInformationListOrBuilder {
  // Use SkuInformationList.newBuilder() to construct.
  private SkuInformationList(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private SkuInformationList() {
    skuInformation_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private SkuInformationList(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              skuInformation_ = new java.util.ArrayList<io.reactivesw.catalog.grpc.SkuInformation>();
              mutable_bitField0_ |= 0x00000001;
            }
            skuInformation_.add(
                input.readMessage(io.reactivesw.catalog.grpc.SkuInformation.parser(), extensionRegistry));
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
        skuInformation_ = java.util.Collections.unmodifiableList(skuInformation_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.reactivesw.catalog.grpc.CatalogSkuMessage.internal_static_io_reactivesw_catalog_infrastructure_SkuInformationList_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.reactivesw.catalog.grpc.CatalogSkuMessage.internal_static_io_reactivesw_catalog_infrastructure_SkuInformationList_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.reactivesw.catalog.grpc.SkuInformationList.class, io.reactivesw.catalog.grpc.SkuInformationList.Builder.class);
  }

  public static final int SKU_INFORMATION_FIELD_NUMBER = 1;
  private java.util.List<io.reactivesw.catalog.grpc.SkuInformation> skuInformation_;
  /**
   * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
   */
  public java.util.List<io.reactivesw.catalog.grpc.SkuInformation> getSkuInformationList() {
    return skuInformation_;
  }
  /**
   * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
   */
  public java.util.List<? extends io.reactivesw.catalog.grpc.SkuInformationOrBuilder> 
      getSkuInformationOrBuilderList() {
    return skuInformation_;
  }
  /**
   * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
   */
  public int getSkuInformationCount() {
    return skuInformation_.size();
  }
  /**
   * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
   */
  public io.reactivesw.catalog.grpc.SkuInformation getSkuInformation(int index) {
    return skuInformation_.get(index);
  }
  /**
   * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
   */
  public io.reactivesw.catalog.grpc.SkuInformationOrBuilder getSkuInformationOrBuilder(
      int index) {
    return skuInformation_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < skuInformation_.size(); i++) {
      output.writeMessage(1, skuInformation_.get(i));
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < skuInformation_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, skuInformation_.get(i));
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof io.reactivesw.catalog.grpc.SkuInformationList)) {
      return super.equals(obj);
    }
    io.reactivesw.catalog.grpc.SkuInformationList other = (io.reactivesw.catalog.grpc.SkuInformationList) obj;

    boolean result = true;
    result = result && getSkuInformationList()
        .equals(other.getSkuInformationList());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    if (getSkuInformationCount() > 0) {
      hash = (37 * hash) + SKU_INFORMATION_FIELD_NUMBER;
      hash = (53 * hash) + getSkuInformationList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.reactivesw.catalog.grpc.SkuInformationList parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.reactivesw.catalog.grpc.SkuInformationList parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.reactivesw.catalog.grpc.SkuInformationList parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.reactivesw.catalog.grpc.SkuInformationList parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.reactivesw.catalog.grpc.SkuInformationList parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.reactivesw.catalog.grpc.SkuInformationList parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.reactivesw.catalog.grpc.SkuInformationList parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.reactivesw.catalog.grpc.SkuInformationList parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.reactivesw.catalog.grpc.SkuInformationList parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.reactivesw.catalog.grpc.SkuInformationList parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.reactivesw.catalog.grpc.SkuInformationList prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   **
   *response when call querySkuInformationList.
   * </pre>
   *
   * Protobuf type {@code io.reactivesw.catalog.infrastructure.SkuInformationList}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:io.reactivesw.catalog.infrastructure.SkuInformationList)
      io.reactivesw.catalog.grpc.SkuInformationListOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.reactivesw.catalog.grpc.CatalogSkuMessage.internal_static_io_reactivesw_catalog_infrastructure_SkuInformationList_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.reactivesw.catalog.grpc.CatalogSkuMessage.internal_static_io_reactivesw_catalog_infrastructure_SkuInformationList_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.reactivesw.catalog.grpc.SkuInformationList.class, io.reactivesw.catalog.grpc.SkuInformationList.Builder.class);
    }

    // Construct using io.reactivesw.catalog.grpc.SkuInformationList.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getSkuInformationFieldBuilder();
      }
    }
    public Builder clear() {
      super.clear();
      if (skuInformationBuilder_ == null) {
        skuInformation_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        skuInformationBuilder_.clear();
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.reactivesw.catalog.grpc.CatalogSkuMessage.internal_static_io_reactivesw_catalog_infrastructure_SkuInformationList_descriptor;
    }

    public io.reactivesw.catalog.grpc.SkuInformationList getDefaultInstanceForType() {
      return io.reactivesw.catalog.grpc.SkuInformationList.getDefaultInstance();
    }

    public io.reactivesw.catalog.grpc.SkuInformationList build() {
      io.reactivesw.catalog.grpc.SkuInformationList result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public io.reactivesw.catalog.grpc.SkuInformationList buildPartial() {
      io.reactivesw.catalog.grpc.SkuInformationList result = new io.reactivesw.catalog.grpc.SkuInformationList(this);
      int from_bitField0_ = bitField0_;
      if (skuInformationBuilder_ == null) {
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          skuInformation_ = java.util.Collections.unmodifiableList(skuInformation_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.skuInformation_ = skuInformation_;
      } else {
        result.skuInformation_ = skuInformationBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.reactivesw.catalog.grpc.SkuInformationList) {
        return mergeFrom((io.reactivesw.catalog.grpc.SkuInformationList)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.reactivesw.catalog.grpc.SkuInformationList other) {
      if (other == io.reactivesw.catalog.grpc.SkuInformationList.getDefaultInstance()) return this;
      if (skuInformationBuilder_ == null) {
        if (!other.skuInformation_.isEmpty()) {
          if (skuInformation_.isEmpty()) {
            skuInformation_ = other.skuInformation_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureSkuInformationIsMutable();
            skuInformation_.addAll(other.skuInformation_);
          }
          onChanged();
        }
      } else {
        if (!other.skuInformation_.isEmpty()) {
          if (skuInformationBuilder_.isEmpty()) {
            skuInformationBuilder_.dispose();
            skuInformationBuilder_ = null;
            skuInformation_ = other.skuInformation_;
            bitField0_ = (bitField0_ & ~0x00000001);
            skuInformationBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getSkuInformationFieldBuilder() : null;
          } else {
            skuInformationBuilder_.addAllMessages(other.skuInformation_);
          }
        }
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      io.reactivesw.catalog.grpc.SkuInformationList parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (io.reactivesw.catalog.grpc.SkuInformationList) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<io.reactivesw.catalog.grpc.SkuInformation> skuInformation_ =
      java.util.Collections.emptyList();
    private void ensureSkuInformationIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        skuInformation_ = new java.util.ArrayList<io.reactivesw.catalog.grpc.SkuInformation>(skuInformation_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        io.reactivesw.catalog.grpc.SkuInformation, io.reactivesw.catalog.grpc.SkuInformation.Builder, io.reactivesw.catalog.grpc.SkuInformationOrBuilder> skuInformationBuilder_;

    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public java.util.List<io.reactivesw.catalog.grpc.SkuInformation> getSkuInformationList() {
      if (skuInformationBuilder_ == null) {
        return java.util.Collections.unmodifiableList(skuInformation_);
      } else {
        return skuInformationBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public int getSkuInformationCount() {
      if (skuInformationBuilder_ == null) {
        return skuInformation_.size();
      } else {
        return skuInformationBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public io.reactivesw.catalog.grpc.SkuInformation getSkuInformation(int index) {
      if (skuInformationBuilder_ == null) {
        return skuInformation_.get(index);
      } else {
        return skuInformationBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public Builder setSkuInformation(
        int index, io.reactivesw.catalog.grpc.SkuInformation value) {
      if (skuInformationBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureSkuInformationIsMutable();
        skuInformation_.set(index, value);
        onChanged();
      } else {
        skuInformationBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public Builder setSkuInformation(
        int index, io.reactivesw.catalog.grpc.SkuInformation.Builder builderForValue) {
      if (skuInformationBuilder_ == null) {
        ensureSkuInformationIsMutable();
        skuInformation_.set(index, builderForValue.build());
        onChanged();
      } else {
        skuInformationBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public Builder addSkuInformation(io.reactivesw.catalog.grpc.SkuInformation value) {
      if (skuInformationBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureSkuInformationIsMutable();
        skuInformation_.add(value);
        onChanged();
      } else {
        skuInformationBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public Builder addSkuInformation(
        int index, io.reactivesw.catalog.grpc.SkuInformation value) {
      if (skuInformationBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureSkuInformationIsMutable();
        skuInformation_.add(index, value);
        onChanged();
      } else {
        skuInformationBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public Builder addSkuInformation(
        io.reactivesw.catalog.grpc.SkuInformation.Builder builderForValue) {
      if (skuInformationBuilder_ == null) {
        ensureSkuInformationIsMutable();
        skuInformation_.add(builderForValue.build());
        onChanged();
      } else {
        skuInformationBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public Builder addSkuInformation(
        int index, io.reactivesw.catalog.grpc.SkuInformation.Builder builderForValue) {
      if (skuInformationBuilder_ == null) {
        ensureSkuInformationIsMutable();
        skuInformation_.add(index, builderForValue.build());
        onChanged();
      } else {
        skuInformationBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public Builder addAllSkuInformation(
        java.lang.Iterable<? extends io.reactivesw.catalog.grpc.SkuInformation> values) {
      if (skuInformationBuilder_ == null) {
        ensureSkuInformationIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, skuInformation_);
        onChanged();
      } else {
        skuInformationBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public Builder clearSkuInformation() {
      if (skuInformationBuilder_ == null) {
        skuInformation_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        skuInformationBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public Builder removeSkuInformation(int index) {
      if (skuInformationBuilder_ == null) {
        ensureSkuInformationIsMutable();
        skuInformation_.remove(index);
        onChanged();
      } else {
        skuInformationBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public io.reactivesw.catalog.grpc.SkuInformation.Builder getSkuInformationBuilder(
        int index) {
      return getSkuInformationFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public io.reactivesw.catalog.grpc.SkuInformationOrBuilder getSkuInformationOrBuilder(
        int index) {
      if (skuInformationBuilder_ == null) {
        return skuInformation_.get(index);  } else {
        return skuInformationBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public java.util.List<? extends io.reactivesw.catalog.grpc.SkuInformationOrBuilder> 
         getSkuInformationOrBuilderList() {
      if (skuInformationBuilder_ != null) {
        return skuInformationBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(skuInformation_);
      }
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public io.reactivesw.catalog.grpc.SkuInformation.Builder addSkuInformationBuilder() {
      return getSkuInformationFieldBuilder().addBuilder(
          io.reactivesw.catalog.grpc.SkuInformation.getDefaultInstance());
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public io.reactivesw.catalog.grpc.SkuInformation.Builder addSkuInformationBuilder(
        int index) {
      return getSkuInformationFieldBuilder().addBuilder(
          index, io.reactivesw.catalog.grpc.SkuInformation.getDefaultInstance());
    }
    /**
     * <code>repeated .io.reactivesw.catalog.infrastructure.SkuInformation sku_information = 1;</code>
     */
    public java.util.List<io.reactivesw.catalog.grpc.SkuInformation.Builder> 
         getSkuInformationBuilderList() {
      return getSkuInformationFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        io.reactivesw.catalog.grpc.SkuInformation, io.reactivesw.catalog.grpc.SkuInformation.Builder, io.reactivesw.catalog.grpc.SkuInformationOrBuilder> 
        getSkuInformationFieldBuilder() {
      if (skuInformationBuilder_ == null) {
        skuInformationBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            io.reactivesw.catalog.grpc.SkuInformation, io.reactivesw.catalog.grpc.SkuInformation.Builder, io.reactivesw.catalog.grpc.SkuInformationOrBuilder>(
                skuInformation_,
                ((bitField0_ & 0x00000001) == 0x00000001),
                getParentForChildren(),
                isClean());
        skuInformation_ = null;
      }
      return skuInformationBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:io.reactivesw.catalog.infrastructure.SkuInformationList)
  }

  // @@protoc_insertion_point(class_scope:io.reactivesw.catalog.infrastructure.SkuInformationList)
  private static final io.reactivesw.catalog.grpc.SkuInformationList DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.reactivesw.catalog.grpc.SkuInformationList();
  }

  public static io.reactivesw.catalog.grpc.SkuInformationList getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SkuInformationList>
      PARSER = new com.google.protobuf.AbstractParser<SkuInformationList>() {
    public SkuInformationList parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new SkuInformationList(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<SkuInformationList> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<SkuInformationList> getParserForType() {
    return PARSER;
  }

  public io.reactivesw.catalog.grpc.SkuInformationList getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

