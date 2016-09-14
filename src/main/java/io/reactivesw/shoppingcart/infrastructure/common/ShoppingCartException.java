package io.reactivesw.shoppingcart.infrastructure.common;

/**
 * shopping cart exception.
 * 
 * @author janeli
 *
 */
public class ShoppingCartException  extends RuntimeException {

  /**
   * serial version uid.
   */
  private static final long serialVersionUID = -2268484602450751066L;
  
  /**
   * shopping cart resource exhausted.
   */
  public static final String RESOURCE_EXHAUSTED = "RESOURCE_EXHAUSTED";
  
  /**
   * shopping cart internal.
   */
  public static final String INTERNAL = "INTERNAL";

  /**
   * constructor.
   * 
   */
  public ShoppingCartException() {
      super();
  }

  /**
   * constructor with message.
   * 
   * @param message String message
   */
  public ShoppingCartException(String message) {
    super(message);
  }

  /**
   * constructor with throwable.
   * 
   * @param cause Throwable
   */
  public ShoppingCartException(Throwable cause) {
    super(cause);
  }


  /**
   * constructor with message and throwable.
   * 
   * @param message String message
   * @param cause Throwable
   */
  public ShoppingCartException(String message, Throwable cause) {
    super(message, cause);
  }
  
}