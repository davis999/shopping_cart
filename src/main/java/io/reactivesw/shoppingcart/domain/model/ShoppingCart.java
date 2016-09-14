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
  @Column(name = "id")
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
  @Column(name = "sku_id")
  private String skuId;

  /**
   * sku quantity.
   */
  @Column(name = "quantity")
  private int quantity;

  /**
   * modified date.
   */
  @Column(name = "modified_date")
  private Date modifiedDate;

  /**
   * get the shopping cart id.
   * 
   * @return id the shopping cart id
   */
  public Long getShoppingCartId() {
    return shoppingCartId;
  }

  /**
   * set the shopping cart id.
   * 
   * @param shoppingCartId the shopping cart id to set
   */
  public void setShoppingCartId(Long shoppingCartId) {
    this.shoppingCartId = shoppingCartId;
  }

  /**
   * get the customer id of the shopping cart.
   * 
   * @return customerId the customerId
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
   * get the sku id of the shopping cart.
   * 
   * @return skuId the sku id
   */
  public String getSkuId() {
    return skuId;
  }

  /**
   * set the sku id of the shopping cart.
   * 
   * @param skuId the sku id to set
   */
  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  /**
   * get the quantity of the shopping cart.
   * 
   * @return quantity the quantity
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * set the quantity of the shopping cart.
   * 
   * @param quantity the quantity to set
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * get the modified date of the shopping cart.
   * 
   * @return modifiedDate the modifiedDate
   */
  public Date getModifiedDate() {
    return new Date(modifiedDate.getTime());
  }

  /**
   * set the modified date of the shopping cart.
   * 
   * @param modifiedDate the modifiedDate to set
   */
  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = new Date(modifiedDate.getTime());
  }

  /**
   * generate hash code.
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(customerId, sessionId, skuId, quantity);
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
              && Objects.equals(sessionId, other.sessionId)
              && Objects.equals(quantity, other.quantity) && Objects.equals(skuId, other.skuId);
    }
    return equalsResult;
  }

}
