# Shopping Cart Design
This service provides these features for use cases.
* Infrastructure
* Adding a Product to a Shopping Cart
* Displaying Shopping Cart Content
* Editing Shpping Cart
* Clearing Shopping Cart 
* Abandoning Shopping Cart

## Infrastructure

### Database Model

#### Shopping Cart `shopping_cart`
Field Name | Not Null | Data Type | Description | Related
---------- | -------- | --------- | ----------- | -------
id | Yes | Long | PK | auto generated |
customer_id | No | Long | FK | Customer
session_id | No | String |  |
sku_id | Yes | Long | FK | SKU
quantity | Yes | Int | |
modified_date | Yes | Date | |

#### Shopping Cart Config `shopping_cart_config`
Field Name | Not Null | Data Type | Description | Related
---------- | -------- | --------- | ----------- | -------
id | Yes | Long | PK | auto generated |
key | Yes | String | configuration key | 
value | No | String | configuration value |
modified_date | Yes | Date | |

### Exception
* Grpc exception: StatusRuntimeException
* Customerized exception:
 - ShoppingCartParamException
 - ShoppingCartLimitException
 - ShoppingCartInventoryException
* Java exception

## 1. Adding a Product to a Shopping Cart
This module is called when a customer clicks "add to cart" button in the product page.  
Client(e.g. web server) should supplie `sku_id`, `quantity`, `customer_id`, `session_id`.  
If the page request client without quantity setting, client should set the `quantity` to be one.

### 1.1. GRPC API

* service: addToShoppingCart (AddRequest) returns (AddReply)
* param: AddRequest(sku_id(Long), quantity(int), customer_id(Long), session_id(String))
* return: AddReply(shoppingCart(GrpcShoppingCart))
* exception: StatusRuntimeException
 - INVALID_ARGUMENT
 - OUT_OF_RANGE
 - RESOURCE_EXHAUSTED
 - INTERNAL

### 1.2. Domain Service

#### 1.2.1. Workflow
* valiadte required parameters `void validateRequestParams(ShoppingCart)`
 - validation success: go ahead
 - validation failed: throw exception(ShoppingCartParamException)
* merge existed quantity `ShoppingCart mergeExistedQuantity(ShoppingCart)`
 - If product is new in cart, remain the quantity.
 - If product has existed in cart, merge the quantity.
* check quantity limit value `void checkQuantityLimit(ShoppingCart)`
 - check success: go ahead
 - check failed: throw exception(ShoppingCartLimitException)
* check inventory `void checkInventory(ShoppingCart)`
 - call product grpc service to get inventory for this product.
 - check if the inventory is enough
  * check success: go ahead
  * check failed: throw exception(ShoppingCartInventoryException)
* save shopping cart to database `ShoppingCart saveShoppingCart(ShoppingCart)`

#### 1.2.2. Exception
* ShoppingCartParamException
 - sku_id is required
 - one of customer_id and session_id is required
 - quantity is required
* ShoppingCartLimitException
 - total quantity of the shopping cart cannot be larger than the limit value
* ShoppingCartInventoryException
 - product is unavailable
 - inventory is 0
 - inventory is less than the quantity

### 1.3. Error Handle

Grpc Status | Grpc Message | Customerized Exception | Customerized Message
----------- | ------------ | ---------------------- | --------------------
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | sku_id is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | one of customer_id and session_id is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | quantity is required
OUT_OF_RANGE | quantity is out of range | ShoppingCartLimitException | total quantity of the shopping cart cannot be larger than the limit value
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | product is unavailable
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | inventory is 0
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | inventory is less than the quantity
INTERNAL | add to shopping cart failed | Exception | 

## 2. Displaying Shopping Cart Content

## 3. Editing Shpping Cart

## 4. Clearing Shopping Cart 

## 5. Abandoning Shopping Cart