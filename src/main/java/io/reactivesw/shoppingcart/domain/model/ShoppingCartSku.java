package io.reactivesw.shoppingcart.domain.model;

import java.util.Date;
import java.util.Objects;

/**
 * shopping cart product just for class mapper.
 * @author janeli
 */
public class ShoppingCartSku {

  /**
   * shopping cart id.
   */
  private long shoppingCartId;

  /**
   * customer id of the login customer.
   */
  private long customerId;

  /**
   * session id of the anonymous customer.
   */
  private String sessionId;

  /**
   * product sku id.
   */
  private long skuId;

  /**
   * product sku number.
   */
  private String skuNumber;

  /**
   * product sku name.
   */
  private String skuName;

  /**
   * product media url.
   */
  private String mediaUrl;

  /**
   * product price.
   */
  private String price;

  /**
   * sku quantity.
   */
  private int quantity;

  /**
   * created time.
   */
  private Date createdTime;

  /**
   * modified time.
   */
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
   * get sku number.
   * @return String
   */
  public String getSkuNumber() {
    return skuNumber;
  }

  /**
   * set sku number.
   * @param skuNumber String
   */
  public void setSkuNumber(String skuNumber) {
    this.skuNumber = skuNumber;
  }

  /**
   * get sku name.
   * @return String
   */
  public String getSkuName() {
    return skuName;
  }

  /**
   * set sku name.
   * @param skuName String
   */
  public void setSkuName(String skuName) {
    this.skuName = skuName;
  }

  /**
   * get media url.
   * @return String
   */
  public String getMediaUrl() {
    return mediaUrl;
  }

  /**
   * set media url.
   * @param mediaUrl String
   */
  public void setMediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  /**
   * get sku price.
   * @return String
   */
  public String getPrice() {
    return price;
  }

  /**
   * set sku price.
   * @param price String
   */
  public void setPrice(String price) {
    this.price = price;
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
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects
        .hash(shoppingCartId, customerId, sessionId, skuId, skuNumber, skuName, mediaUrl, price,
            quantity, createdTime, modifiedTime);
  }

  /**
   * domain equals.
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean equalsResult = false;
    if (this == obj) {
      equalsResult = true;
    } else if (obj instanceof ShoppingCartSku) {
      final ShoppingCartSku other = (ShoppingCartSku) obj;
      equalsResult =
          Objects.equals(shoppingCartId, other.shoppingCartId)
              && Objects.equals(customerId, other.customerId)
              && Objects.equals(sessionId, other.sessionId)
              && Objects.equals(skuId, other.skuId)
              && Objects.equals(skuNumber, other.skuNumber)
              && Objects.equals(skuName, other.skuName)
              && Objects.equals(mediaUrl, other.mediaUrl)
              && Objects.equals(price, other.price)
              && Objects.equals(quantity, other.quantity)
              && Objects.equals(createdTime, other.createdTime)
              && Objects.equals(modifiedTime, other.modifiedTime);
    }
    return equalsResult;
  }

}
