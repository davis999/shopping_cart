package io.reactivesw.shoppingcart.infrastructure.exception;

/**
 * shopping cart parameter exception.
 * @author janeli
 */
public class ShoppingCartParamException extends ShoppingCartException {

  /**
   * customer id required.
   */
  public static final String CUSTOMER_ID_REQUIRED = "customer_id is required";
  /**
   * session id required.
   */
  public static final String SESSION_ID_REQUIRED = "session_id is required";
  /**
   * customer id or session id required.
   */
  public static final String CUSTOMER_REQUIRED = "one of customer_id and session_id is required";
  /**
   * quantity required.
   */
  public static final String QUANTITY_REQUIRED = "quantity is required";
  /**
   * quantity invalid.
   */
  public static final String QUANTITY_INVALID = "quantity is invalid";
  /**
   * serial version uid.
   */
  private static final long serialVersionUID = -2693939305816309194L;

  /**
   * constructor.
   */
  public ShoppingCartParamException() {
    super();
  }

  /**
   * constructor with message.
   * @param message String message
   */
  public ShoppingCartParamException(String message) {
    super(message);
  }

  /**
   * constructor with throwable.
   * @param cause Throwable
   */
  public ShoppingCartParamException(Throwable cause) {
    super(cause);
  }
  
  /**
   * constructor with message and throwable.
   * @param message String message
   * @param cause Throwable
   */
  public ShoppingCartParamException(String message, Throwable cause) {
    super(message, cause);
  }
}