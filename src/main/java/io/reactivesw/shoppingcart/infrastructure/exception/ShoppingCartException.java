package io.reactivesw.shoppingcart.infrastructure.exception;

/**
 * shopping cart exception.
 * @author janeli
 */
public class ShoppingCartException extends RuntimeException {

  /**
   * product is unavailable.
   */
  public static final String INTERNAL = "grpc client cannot shut down";

  /**
   * serial version uid.
   */
  private static final long serialVersionUID = 5913775916147840081L;

  /**
   * constructor.
   */
  public ShoppingCartException() {
    super();
  }

  /**
   * constructor with message.
   * @param message String message
   */
  public ShoppingCartException(String message) {
    super(message);
  }

  /**
   * constructor with throwable.
   * @param cause Throwable
   */
  public ShoppingCartException(Throwable cause) {
    super(cause);
  }

  /**
   * constructor with message and throwable.
   * @param message String message
   * @param cause Throwable
   */
  public ShoppingCartException(String message, Throwable cause) {
    super(message, cause);
  }

}