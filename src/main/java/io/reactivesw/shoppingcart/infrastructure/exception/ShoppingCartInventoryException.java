package io.reactivesw.shoppingcart.infrastructure.exception;

/**
 * shopping cart inventory exception.
 * @author janeli
 */
public class ShoppingCartInventoryException extends ShoppingCartException {

  /**
   * product is unavailable.
   */
  public static final String PRODUCT_UNAVAILABLE = "product is unavailable";
  /**
   * inventory is 0.
   */
  public static final String ZERO_INVENTORY = "inventory is 0";
  /**
   * inventory is less than quantity.
   */
  public static final String LESS_INVENTORY = "inventory is less than the quantity";
  /**
   * serial version uid.
   */
  private static final long serialVersionUID = 2212319431510518735L;

  /**
   * constructor.
   */
  public ShoppingCartInventoryException() {
    super();
  }

  /**
   * constructor with message.
   * @param message String message
   */
  public ShoppingCartInventoryException(String message) {
    super(message);
  }

  /**
   * constructor with throwable.
   * @param cause Throwable
   */
  public ShoppingCartInventoryException(Throwable cause) {
    super(cause);
  }

  /**
   * constructor with message and throwable.
   * @param message String message
   * @param cause Throwable
   */
  public ShoppingCartInventoryException(String message, Throwable cause) {
    super(message, cause);
  }

}