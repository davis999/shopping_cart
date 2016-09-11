package io.reactivesw.shoppingcart.domain.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * shopping cart domain.
 * 
 * @author janeli
 *
 */
@Entity
public class ShoppingCart {

  /**
   * shopping cart id auto generated.
   */
  @Id
  @GeneratedValue
  @Column(name = "shopping_cart_id")
  private Long shoppingCartId;

  /**
   * customer id of the login customer.
   */
  @Column(name = "customer_id")
  private String customerId;

  /**
   * session id of the anonymous customer.
   */
  @Column(name = "session_id")
  private String sessionId;

  /**
   * product sku number.
   */
  @Column(name = "sku")
  private String sku;

  /**
   * sku qty.
   */
  @Column(name = "qty")
  private int qty;

  /**
   * modified date.
   */
  @Column(name = "modified_date")
  private Date modifiedDate;

  /**
   * get ther shopping cart id.
   * 
   * @return shoppingCartId the shoppingCartId
   */
  public Long getShoppingCartId() {
    return shoppingCartId;
  }

  /**
   * set the shopping cart id.
   * 
   * @param shoppingCartId the shoppingCartId to set
   */
  public void setShoppingCartId(Long shoppingCartId) {
    this.shoppingCartId = shoppingCartId;
  }

  /**
   * get the customer id of the shopping cart.
   * 
   * @return shoppingCartId the customerId
   */
  public String getCustomerId() {
    return customerId;
  }

  /**
   * set the customer id of the shopping cart.
   * 
   * @param customerId the customerId to set
   */
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  /**
   * get the session id of the shopping cart.
   * 
   * @return sessionId the sessionId
   */
  public String getSessionId() {
    return sessionId;
  }

  /**
   * set the session id of the shopping cart.
   * 
   * @param sessionId the sessionId to set
   */
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  /**
   * get the sku of the shopping cart.
   * 
   * @return sku the sku
   */
  public String getSku() {
    return sku;
  }

  /**
   * set the sku of the shopping cart.
   * 
   * @param sku the sku to set
   */
  public void setSku(String sku) {
    this.sku = sku;
  }

  /**
   * get the qty of the shopping cart.
   * 
   * @return qty the qty
   */
  public int getQty() {
    return qty;
  }

  /**
   * set the qty of the shopping cart.
   * 
   * @param qty the qty to set
   */
  public void setQty(int qty) {
    this.qty = qty;
  }

  /**
   * get the modified date of the shopping cart.
   * 
   * @return modifiedDate the modifiedDate
   */
  public Date getModifiedDate() {
    return (Date) modifiedDate.clone();
  }

  /**
   * set the modified date of the shopping cart.
   * 
   * @param modifiedDate the modifiedDate to set
   */
  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = (Date) modifiedDate.clone();
  }

  /**
   * generate hash code.
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(customerId, sessionId, sku, qty);
  }

  /**
   * domain equals.
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean equalsResult = false;
    if (this == obj) {
      equalsResult = true;
    } else if (obj instanceof ShoppingCart) {
      final ShoppingCart other = (ShoppingCart) obj;
      equalsResult =
          Objects.equals(customerId, other.customerId)
              && Objects.equals(sessionId, other.sessionId) && Objects.equals(qty, other.qty)
              && Objects.equals(sku, other.sku);
    }
    return equalsResult;
  }

}
