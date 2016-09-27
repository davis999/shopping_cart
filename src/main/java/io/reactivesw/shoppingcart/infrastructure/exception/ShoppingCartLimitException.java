package io.reactivesw.shoppingcart.infrastructure.exception;

/**
 * shopping cart limit exception.
 * @author janeli
 */
public class ShoppingCartLimitException extends ShoppingCartException {

  /**
   * shopping cart resource exhausted.
   */
  public static final String TOTAL_LIMIT =
      "total quantity of the shopping cart cannot be larger than the limit value";
  /**
   * serial version uid.
   */
  private static final long serialVersionUID = -2268484602450751066L;

  /**
   * constructor.
   */
  public ShoppingCartLimitException() {
    super();
  }

  /**
   * constructor with message.
   * @param message String message
   */
  public ShoppingCartLimitException(String message) {
    super(message);
  }

  /**
   * constructor with throwable.
   * @param cause Throwable
   */
  public ShoppingCartLimitException(Throwable cause) {
    super(cause);
  }

  /**
   * constructor with message and throwable.
   * @param message String message
   * @param cause Throwable
   */
  public ShoppingCartLimitException(String message, Throwable cause) {
    super(message, cause);
  }

}