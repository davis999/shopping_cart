package io.reactivesw.shopping.cart.common;

/**
 * constants declare for shopping cart service.
 * 
 * @author janeli
 *
 */
public final class ConstantsUtility {

  /**
   * blank string value.
   */
  public static final String BLANK_VALUE = "";

  /**
   * default sku qty.
   */
  public static final int DEFAULT_QTY = 1;

  /**
   * zero value qty.
   */
  public static final int ZERO_QTY = 0;

  /**
   * not found value.
   */
  public static final long ERROR_COUNT = -1L;

  /**
   * grpc request parameters are invalid.
   */
  public static final String GRPC_INVALID_ARGUMENT_MSG = "grpc request parameters are invalid";

  /**
   * resource is exhausted.
   */
  public static final String GRPC_RESOURCE_EXHAUSTED_MSG = "resource is exhausted";

  /**
   * add product to shopping cart failed.
   */
  public static final String GRPC_INTERNAL_MSG = "add product to shopping cart failed";

  /**
   * check if string is empty.
   * 
   * @param str String
   * @return boolean
   */
  public static boolean stringEmpty(String str) {
    return str == null || str.equals(ConstantsUtility.BLANK_VALUE);
  }

}
