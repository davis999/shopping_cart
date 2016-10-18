# Shopping Cart Design
This service provides these features for use cases.
* Infrastructure
* Adding a Product to a Shopping Cart
* Displaying Shopping Cart Content
* Editing Shpping Cart
* Clearing Shopping Cart 
* Abandoning Shopping Cart

## Infrastructure

### Database Model `Entity`

#### Shopping Cart `shopping_cart`
Field Name | Not Null | Data Type | Description | Related
---------- | -------- | --------- | ----------- | -------
id | Yes | Long | PK | auto generated |
customer_id | No | Long | FK | Customer
session_id | No | String |  |
sku_id | Yes | Long | FK | SKU
quantity | Yes | Int | |
created_time | Yes | Date | |
modified_time | Yes | Date | |

#### Shopping Cart Config `shopping_cart_config`
Field Name | Not Null | Data Type | Description | Related
---------- | -------- | --------- | ----------- | -------
id | Yes | Long | PK | auto generated |
key | Yes | String | configuration key | 
value | No | String | configuration value |
created_time | Yes | Date | |
modified_time | Yes | Date | |

### Exception
* Grpc exception: StatusRuntimeException
* Customerized exception:
 - ShoppingCartParamException
 - ShoppingCartLimitException
 - ShoppingCartInventoryException

## 1. Adding a Product to a Shopping Cart
This module is called when a customer clicks "add to cart" button in the product page.  
Client(e.g. web server) should supplie `sku_id`, `quantity`, `customer_id`, `session_id`.  
If the page send request to client without quantity setting, client should set the `quantity` to be one.

### 1.1. GRPC API

* service: addToShoppingCart (ShoppingCartRequest) returns (ShoppingCartReply)
* param: ShoppingCartRequest(sku_id(Long), quantity(int), customer_id(Long), session_id(String))
* return: ShoppingCartReply(shoppingCart(GrpcShoppingCart))
* exception: StatusRuntimeException
 - INVALID_ARGUMENT
 - OUT_OF_RANGE
 - RESOURCE_EXHAUSTED
 - INTERNAL

### 1.2. Domain Service

#### 1.2.1. Workflow
* valiadte required parameters `void validateRequestParams(ShoppingCart)`
 - one of customer_id and session_id is required (both are not null, use customer id)
 - quantity is required (quantity = 0)
 - quantity is invalid (quantity < 0)
 - validation success: go ahead
 - validation failed: throw exception(ShoppingCartParamException)
* merge existed quantity `ShoppingCart mergeExistedQuantity(ShoppingCart)`
 - If product is new in cart, remain the quantity
 - If product has existed in cart, merge the quantity
* check quantity limit value `void checkQuantityLimit(ShoppingCart)`
 - default limit: 42, unlimited: 0
 - total products quantity of the shopping cart cannot be larger than the limit
 - check success: go ahead
 - check failed: throw exception(ShoppingCartLimitException)
* check inventory `void checkInventory(ShoppingCart)`
 - call product grpc service to get inventory for this product
 - check if the inventory is enough (inventory != 0 and inventory >= quantity)
  * check success: go ahead
  * check failed: throw exception(ShoppingCartInventoryException)
* save shopping cart to database `ShoppingCart saveShoppingCart(ShoppingCart)`

#### 1.2.2. Exception
* ShoppingCartParamException
 - one of customer_id and session_id is required
 - quantity is required
 - quantity is invalid
* ShoppingCartLimitException
 - total quantity of the shopping cart cannot be larger than the limit value
* ShoppingCartInventoryException
 - product is unavailable
 - inventory is 0
 - inventory is less than the quantity

### 1.3. Error Handle

Grpc Status | Grpc Message | Customerized Exception | Customerized Message
----------- | ------------ | ---------------------- | --------------------
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | one of customer_id and session_id is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | quantity is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | quantity is invalid (quantity < 0)
OUT_OF_RANGE | quantity is out of range | ShoppingCartLimitException | total quantity of the shopping cart cannot be larger than the limit value
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | product is unavailable (from product grpc exception)
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | inventory is 0
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | inventory is less than the quantity
INTERNAL | add to shopping cart failed | Exception | 

## 2. Displaying Shopping Cart Content

### 2.1. Grpc API

#### 2.1.1. Request by customer id
* service: listShoppingCartForCustomer (CustomerRequest) returns (ShoppingCartListReply)
* param: CustomerRequest(customer_id(Long))
* return: ShoppingCartListReply(List<GrpcShoppingCart>)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

#### 2.1.2. Request by session id
* service: listShoppingCartForSession (SessionRequest) returns (ShoppingCartListReply)
* param: SessionRequest(session_id(String))
* return: ShoppingCartListReply(List<GrpcShoppingCart>)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

### 2.2. Domain Service

#### 2.2.1. Workflow
* get shopping cart list
 - get shopping cart list by customer id `List<ShoppingCart> listShoppingCartByCustomerId(long customerId)`
 - get shopping cart list by session id `List<ShoppingCart> listShoppingCartBySessionId(String sessionId)`
* call sku grpc service to get product information `List getShoppingCartSkuInfoList(List<long> skuIdList)`
 - list shopping cart sku id
 - call sku grpc service
 - list shopping cart product information

#### 2.2.2. Exception
* ShoppingCartParamException
 - customer_id is required: `listShoppingCartByCustomerId`
 - session_id is required: `listShoppingCartBySessionId`

### 2.3 Error Handle

Grpc Status | Grpc Message | Customerized Exception | Customerized Message
----------- | ------------ | ---------------------- | --------------------
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | customer_id is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | session_id is required

## 3. Editing Shpping Cart
This module is called when a customer clicks "+" or "-" icon in the shopping cart page.  
Client(e.g. web server) should supplie `sku_id`, `quantity`, `customer_id`, `session_id`.  
If the page send request to client without quantity setting, client should set the `quantity` to be one.

### 3.1. GRPC API

* service: editShoppingCart (ShoppingCartRequest) returns (ShoppingCartReply)
* param: ShoppingCartRequest(sku_id(Long), quantity(int), customer_id(Long), session_id(String))
* return: ShoppingCartReply(shoppingCart(GrpcShoppingCart))
* exception: StatusRuntimeException
 - INVALID_ARGUMENT
 - OUT_OF_RANGE
 - RESOURCE_EXHAUSTED
 - INTERNAL

### 3.2. Domain Service

#### 3.2.1. Workflow
* valiadte required parameters `void validateRequestParams(ShoppingCart)`
 - one of customer_id and session_id is required (both are not null, use customer id)
 - quantity is required (quantity = 0)
 - quantity is invalid (quantity < 0)
 - validation success: go ahead
 - validation failed: throw exception(ShoppingCartParamException)
* check quantity limit value `void checkQuantityLimit(ShoppingCart)`
 - default limit: 42, unlimited: 0
 - total products quantity of the shopping cart cannot be larger than the limit
 - check success: go ahead
 - check failed: throw exception(ShoppingCartLimitException)
* check inventory `void checkInventory(ShoppingCart)`
 - call product grpc service to get inventory for this product
 - check if the inventory is enough (inventory != 0 and inventory >= quantity)
  * check success: go ahead
  * check failed: throw exception(ShoppingCartInventoryException)
* save shopping cart to database `ShoppingCart saveShoppingCart(ShoppingCart)`

#### 3.2.2. Exception
* ShoppingCartParamException
 - one of customer_id and session_id is required
 - quantity is required
 - quantity is invalid
* ShoppingCartLimitException
 - total quantity of the shopping cart cannot be larger than the limit value
* ShoppingCartInventoryException
 - product is unavailable
 - inventory is 0
 - inventory is less than the quantity

### 3.3. Error Handle

Grpc Status | Grpc Message | Customerized Exception | Customerized Message
----------- | ------------ | ---------------------- | --------------------
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | one of customer_id and session_id is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | quantity is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | quantity is invalid (quantity < 0)
OUT_OF_RANGE | quantity is out of range | ShoppingCartLimitException | total quantity of the shopping cart cannot be larger than the limit value
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | product is unavailable (from product grpc exception)
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | inventory is 0
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | inventory is less than the quantity
INTERNAL | add to shopping cart failed | Exception | 

## 4. Delete Sku from Shopping Cart 
When customer click the "x" icon, the sku in the shopping cart will be deleted.

### 4.1. Delete Sku Grpc API

#### 4.1.1. Request by customer id
* service: deleteSkuForCustomer (CustomerRequest) returns (DeleteReply)
* param: CustomerRequest(customer_id(Long))
* return: DeleteReply(boolean)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

#### 4.1.2. Request by session id
* service: deleteSkuForSession (SessionRequest) returns (DeleteReply)
* param: SessionRequest(session_id(String))
* return: DeleteReply(boolean)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

### 4.2. Domain Service

#### 4.2.1. Workflow
* delete sku from shopping cart
 - delete sku from shopping cart by customer id `boolean deleteSkuByCustomerId(long customerId)`
 - delete sku from shopping cart by session id `boolean deleteSkuBySessionId(String sessionId)`

#### 4.2.2. Exception
* ShoppingCartParamException
 - customer_id is required: `deleteSkuByCustomerId`
 - session_id is required: `deleteSkuBySessionId`

### 4.3 Error Handle

Grpc Status | Grpc Message | Customerized Exception | Customerized Message
----------- | ------------ | ---------------------- | --------------------
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | customer_id is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | session_id is required

## 5. Clearing Shopping Cart
When customer submit the order, shopping cart will be cleaned.

### 5.1. Grpc API

#### 5.1.1. Request service
* service: deleteForCustomer (CustomerRequest) returns (DeleteReply)
* param: CustomerRequest(customer_id(Long))
* return: DeleteReply(boolean)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

### 5.2. Domain Service

#### 5.2.1. Workflow
* clear shopping cart
 - clear shopping cart by customer id `boolean deleteByCustomerId(long customerId)`

#### 5.2.2. Exception
* ShoppingCartParamException
 - customer_id is required: `deleteByCustomerId`

### 5.3 Error Handle

Grpc Status | Grpc Message | Customerized Exception | Customerized Message
----------- | ------------ | ---------------------- | --------------------
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | customer_id is required

## 6. Abandoning Shopping Cart
When the session of an anonymous customer is expired, the shopping cart will be abandoned.

### 6.1. Grpc API

#### 6.1.1. Request service
* service: deleteForSession (SessionRequest) returns (DeleteReply)
* param: SessionRequest(session_id(String))
* return: DeleteReply(boolean)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

### 6.2. Domain Service

#### 6.2.1. Workflow
* clear shopping cart
 - clear shopping cart by customer id `boolean deleteBySessionId(String sessionId)`

#### 6.2.2. Exception
* ShoppingCartParamException
 - session_id is required: `deleteBySessionId`

### 6.3 Error Handle

Grpc Status | Grpc Message | Customerized Exception | Customerized Message
----------- | ------------ | ---------------------- | --------------------
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | session_id is required
