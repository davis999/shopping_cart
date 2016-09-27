package io.reactivesw.shoppingcart.domain.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * shopping cart domain.
 * @author janeli
 */
@Entity
public class ShoppingCart {

  /**
   * shopping cart id auto generated.
   */
  @Id
  @GeneratedValue
  @Column(name = "id")
  private long shoppingCartId;

  /**
   * customer id of the login customer.
   */
  @Column(name = "customer_id")
  private long customerId;

  /**
   * session id of the anonymous customer.
   */
  @Column(name = "session_id")
  private String sessionId;

  /**
   * product sku number.
   */
  @Column(name = "sku_id")
  private long skuId;

  /**
   * sku quantity.
   */
  @Column(name = "quantity")
  private int quantity;

  /**
   * created time.
   */
  @Column(name = "created_time")
  private Date createdTime;

  /**
   * modified time.
   */
  @Column(name = "modified_time")
  private Date modifiedTime;

  /**
   * get the shopping cart id.
   * @return id the shopping cart id
   */
  public long getShoppingCartId() {
    return shoppingCartId;
  }

  /**
   * set the shopping cart id.
   * @param shoppingCartId the shopping cart id to set
   */
  public void setShoppingCartId(long shoppingCartId) {
    this.shoppingCartId = shoppingCartId;
  }

  /**
   * get the customer id of the shopping cart.
   * @return customerId the customerId
   */
  public long getCustomerId() {
    return customerId;
  }

  /**
   * set the customer id of the shopping cart.
   * @param customerId the customerId to set
   */
  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

  /**
   * get the session id of the shopping cart.
   * @return sessionId the sessionId
   */
  public String getSessionId() {
    return sessionId;
  }

  /**
   * set the session id of the shopping cart.
   * @param sessionId the sessionId to set
   */
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  /**
   * get the sku id of the shopping cart.
   * @return skuId the sku id
   */
  public long getSkuId() {
    return skuId;
  }

  /**
   * set the sku id of the shopping cart.
   * @param skuId the sku id to set
   */
  public void setSkuId(long skuId) {
    this.skuId = skuId;
  }

  /**
   * get the quantity of the shopping cart.
   * @return quantity the quantity
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * set the quantity of the shopping cart.
   * @param quantity the quantity to set
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * get the created time of the shopping cart.
   * @return createdTime the createdTime
   */
  public Date getCreatedTime() {
    return new Date(createdTime.getTime());
  }

  /**
   * set the created time of the shopping cart.
   * @param createdTime the createdTime to set
   */
  public void setCreatedTime(Date createdTime) {
    this.createdTime = new Date(createdTime.getTime());
  }

  /**
   * get the modified time of the shopping cart.
   * @return modifiedTime the modifiedTime
   */
  public Date getModifiedTime() {
    return new Date(modifiedTime.getTime());
  }

  /**
   * set the modified time of the shopping cart.
   * @param modifiedTime the modifiedTime to set
   */
  public void setModifiedTime(Date modifiedTime) {
    this.modifiedTime = new Date(modifiedTime.getTime());
  }

  /**
   * generate hash code.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(shoppingCartId, customerId, sessionId, skuId, quantity, createdTime,
        modifiedTime);
  }

  /**
   * domain equals.
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
          Objects.equals(shoppingCartId, other.shoppingCartId)
              && Objects.equals(customerId, other.customerId)
              && Objects.equals(sessionId, other.sessionId)
              && Objects.equals(createdTime, other.createdTime)
              && Objects.equals(modifiedTime, other.modifiedTime)
              && Objects.equals(quantity, other.quantity) && Objects.equals(skuId, other.skuId);
    }
    return equalsResult;
  }

}
