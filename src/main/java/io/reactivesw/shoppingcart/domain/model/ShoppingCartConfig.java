package io.reactivesw.shoppingcart.domain.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * shopping cart config domain.
 * @author janeli
 */
@Entity
public class ShoppingCartConfig {

  /**
   * config id auto generated.
   */
  @Id
  @GeneratedValue
  @Column(name = "id")
  private long configId;

  /**
   * config key.
   */
  @Column(name = "config_key")
  private String configKey;

  /**
   * config value.
   */
  @Column(name = "config_value")
  private String configValue;

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
   * get config id.
   * @return long
   */
  public long getConfigId() {
    return configId;
  }

  /**
   * set config id.
   * @param configId long
   */
  public void setConfigId(long configId) {
    this.configId = configId;
  }

  /**
   * get config key.
   * @return String
   */
  public String getConfigKey() {
    return configKey;
  }

  /**
   * set config key.
   * @param configKey String
   */
  public void setConfigKey(String configKey) {
    this.configKey = configKey;
  }

  /**
   * get config value.
   * @return String
   */
  public String getConfigValue() {
    return configValue;
  }

  /**
   * set config value.
   * @param configValue String
   */
  public void setConfigValue(String configValue) {
    this.configValue = configValue;
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
    return Objects.hash(configId, configKey, configValue, createdTime, modifiedTime);
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
    } else if (obj instanceof ShoppingCartConfig) {
      final ShoppingCartConfig other = (ShoppingCartConfig) obj;
      equalsResult =
          Objects.equals(configId, other.configId)
              && Objects.equals(configKey, other.configKey)
              && Objects.equals(configValue, other.configValue)
              && Objects.equals(createdTime, other.createdTime)
              && Objects.equals(modifiedTime, other.modifiedTime);
    }
    return equalsResult;
  }

}
