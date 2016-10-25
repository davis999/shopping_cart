# Shopping Cart Design
This document describes the domain services, application services and grpc services provided by the shopping cart service.

## 1. Overview
* Infrastructure
* Adding a Product to a Shopping Cart
* Displaying Shopping Cart Content
* Editing Shpping Cart
* Clearing Shopping Cart 
* Abandoning Shopping Cart

## 2. Infrastructure

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

## 3. Service API
The domain services are organized for application services exposed via gRPC interfaces.
All application exceptions are converted to gGrpc `StatusRuntimeException` and a corresponding status code.

**gRPC Exception mappings**

Grpc Status | Grpc Message | Customerized Exception | Customerized Message
----------- | ------------ | ---------------------- | --------------------
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | one of customer_id and session_id is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | customer_id is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | session_id is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | quantity is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | quantity is invalid (quantity < 0)
OUT_OF_RANGE | quantity is out of range | ShoppingCartLimitException | total quantity of the shopping cart cannot be larger than the limit value
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | product is unavailable (from product grpc exception)
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | inventory is 0
RESOURCE_EXHAUSTED | inventory is exhausted | ShoppingCartInventoryException | inventory is less than the quantity
INTERNAL | add to shopping cart failed | Exception |

## 3.1. Adding a Product to a Shopping Cart
This module is called when a customer clicks "add to cart" button in the product page.  
Client(e.g. web server) should supplie `sku_id`, `quantity`, `customer_id`, `session_id`.  
If the page send request to client without quantity setting, client should set the `quantity` to be one.

### 3.1.1. `addToShoppingCart`[Class: `AddItemApp`]

**Description**: add a sku to shopping cart

**parameters**: shoppingCart(ShoppingCart)

**return**: scSku(ShoppingCartSku)

**exception**:
* ShoppingCartParamException
 - both customer_id and session_id are empty: one of customer_id and session_id is required
 - quantity = 0: quantity is required
 - quantity < 0: quantity is invalid
* ShoppingCartLimitException(There is a max value for the total number of item in a shopping cart. It is a configurable value.)
 - Quantity > max: total quantity of the shopping cart cannot be larger than the limit value
* ShoppingCartInventoryException
 - Product not available: product is unavailable
 - Inventory = 0: inventory is 0
 - Inventory < quantity: inventory is less than the quantity

**process**:
* valiadte required parameters `void validateRequestParams(ShoppingCart)`[Class: `ValidateParamsApp`]
 - check customer_id and session_id (both are not null, use customer id) `void validateCustomer(long customerId, String sessionId)`
 - check quantity `void validateQuantity(int quantity)`
* merge existed quantity `ShoppingCart mergeExistedQuantity(ShoppingCart)`[Class: `AddItemApp`]
 - If product is new in cart, remain the quantity
 - If product has existed in cart, merge the quantity
* check quantity limit value `void checkQuantityLimit(ShoppingCart)`[Class: `CheckConfigLimitApp`]
 - default limit: 42, unlimited: 0
 - check total quantity limit
* check inventory `void checkInventory(ShoppingCart)`[Class: `CheckInventoryApp`]
 - get inventory (call sku grpc service to get inventory for this sku) `int getInventoryBySkuId(long skuId)`
 - check inventory
* save shopping cart to database `ShoppingCart save(ShoppingCart shoppingCart)`[Class: `ShoppingCartService`]
* organize information of the shopping cart sku item `ShoppingCartSku getShoppingCartSkuInfo(ShoppingCart item)`[Class: `GetSkuInfoSingleApp`]
 - get sku info of the sku (call sku grpc service to get sku info) `ShoppingCartSku getSkuInfo(long skuId)`[Class: `GetSkuInfoSingleApp`]
 - organize shopping cart sku info `ShoppingCartSku organizeShoppingCartSku(ShoppingCart sc, ShoppingCartSku scProduct)`[Class: `GetSkuInfoSingleApp`]

**GRPC API**:

* service: addToShoppingCart (ShoppingCartRequest) returns (ShoppingCartReply)
* param: ShoppingCartRequest(sku_id(Long), quantity(int), customer_id(Long), session_id(String))
* return: ShoppingCartReply(shoppingCart(GrpcShoppingCart))
* exception: StatusRuntimeException
 - INVALID_ARGUMENT
 - OUT_OF_RANGE
 - RESOURCE_EXHAUSTED
 - INTERNAL

## 3.2. Displaying Shopping Cart Content

### 3.2.1. `listByCustomerId`[Class: `ListItemsApp`]

**Description**: display shopping cart list for logged-in customer

**Parameters**: customerId(long)

**Return**: (List<ShoppingCartSku>)

**Exception**:
* ShoppingCartParamException
 - customer_id is required

**Process**:
* get shopping cart list by customer id `List<ShoppingCart> listShoppingCartByCustomerId(long customerId)`[Class: `shoppingCartService`]
* call sku grpc service to get product information `List<ShoppingCartSku> getShoppingCartSkuInfoList(List<ShoppingCart> itemList)`[Class: `GetSkuInfoListApp`]
 - list shopping cart sku id `List<Long> getSkuIdList(List<ShoppingCart> itemList)`[Class: `GetSkuInfoListApp`]
 - call sku grpc service `List<ShoppingCartSku> getSkuInfoList(List<Long> skuIdList)`[Class: `GetSkuInfoListApp`]
 - organize shopping cart sku info `List<ShoppingCartSku> organizeShoppingCartSkuList(List<ShoppingCart> shoppingCartList, List<ShoppingCartSku> skuInfoList)`[Class: `GetSkuInfoListApp`]

**Grpc API**:
* service: listShoppingCartForCustomer (CustomerRequest) returns (ShoppingCartListReply)
* param: CustomerRequest(customer_id(Long))
* return: ShoppingCartListReply(List<GrpcShoppingCart>)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

### 3.2.2. `listBySessionId`[Class: `ListItemsApp`]

**Description**: display shopping cart list for anonymous customer

**Parameters**: sessionId(String)

**Return**: (List<ShoppingCartSku>)

**Exception**:
* ShoppingCartParamException
 - session_id is required

**Process**:
* get shopping cart list by session id `List<ShoppingCart> listShoppingCartBySessionId(String sessionId)`[Class: `shoppingCartService`]
* call sku grpc service to get product information `List<ShoppingCartSku> getShoppingCartSkuInfoList(List<ShoppingCart> itemList)`[Class: `GetSkuInfoListApp`]
 - list shopping cart sku id `List<Long> getSkuIdList(List<ShoppingCart> itemList)`[Class: `GetSkuInfoListApp`]
 - call sku grpc service `List<ShoppingCartSku> getSkuInfoList(List<Long> skuIdList)`[Class: `GetSkuInfoListApp`]
 - organize shopping cart sku info `List<ShoppingCartSku> organizeShoppingCartSkuList(List<ShoppingCart> shoppingCartList, List<ShoppingCartSku> skuInfoList)`[Class: `GetSkuInfoListApp`]

**Grpc API**:
* service: listShoppingCartForSession (SessionRequest) returns (ShoppingCartListReply)
* param: SessionRequest(session_id(String))
* return: ShoppingCartListReply(List<GrpcShoppingCart>)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

## 3.3. Editing Shpping Cart
This module is called when a customer clicks "+" or "-" icon in the shopping cart page.  
Client(e.g. web server) should supplie `sku_id`, `quantity`, `customer_id`, `session_id`.  
If the page send request to client without quantity setting, client should set the `quantity` to be one.

### 3.3.1. `editShoppingCart`[Class: `EditItemApp`]

**Description**: add a sku to shopping cart

**parameters**: shoppingCart(ShoppingCart)

**return**: scSku(ShoppingCartSku)

**exception**:
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

**Process**:
* valiadte required parameters `void validateRequestParams(ShoppingCart)`[Class: `ValidateParamsApp`]
 - check customer_id and session_id (both are not null, use customer id) `void validateCustomer(long customerId, String sessionId)`
 - check quantity `void validateQuantity(int quantity)`
* check quantity limit value `void checkQuantityLimit(ShoppingCart)`[Class: `CheckConfigLimitApp`]
 - default limit: 42, unlimited: 0
 - check total quantity limit
* check inventory `void checkInventory(ShoppingCart)`[Class: `CheckInventoryApp`]
 - get inventory (call sku grpc service to get inventory for this sku) `int getInventoryBySkuId(long skuId)`
 - check inventory
* get existed shopping cart record info `ShoppingCart existedShoppingCart(ShoppingCart shoppingCart)`[Class: `EditItemApp`]
* save shopping cart to database `ShoppingCart save(ShoppingCart shoppingCart)`[Class: `ShoppingCartService`]
* organize information of the shopping cart sku item `ShoppingCartSku getShoppingCartSkuInfo(ShoppingCart item)`[Class: `GetSkuInfoSingleApp`]
 - get sku info of the sku (call sku grpc service to get sku info) `ShoppingCartSku getSkuInfo(long skuId)`[Class: `GetSkuInfoSingleApp`]
 - organize shopping cart sku info `ShoppingCartSku organizeShoppingCartSku(ShoppingCart sc, ShoppingCartSku scProduct)`[Class: `GetSkuInfoSingleApp`]

**Grpc API**:
* service: editShoppingCart (ShoppingCartRequest) returns (ShoppingCartReply)
* param: ShoppingCartRequest(sku_id(Long), quantity(int), customer_id(Long), session_id(String))
* return: ShoppingCartReply(shoppingCart(GrpcShoppingCart))
* exception: StatusRuntimeException
 - INVALID_ARGUMENT
 - OUT_OF_RANGE
 - RESOURCE_EXHAUSTED
 - INTERNAL

## 3.4. Delete Sku from Shopping Cart 
When customer click the "x" icon, the sku in the shopping cart will be deleted.

### 3.4.1. `deleteSkuByCustomerId`

**Description**: delete sku from shopping cart by customer id

**parameters**: customerId(long)

**return**: deleted(boolean)

**exception**:
* ShoppingCartParamException
 - customer_id is required

**Process**:
* delete sku from shopping cart by customer id `Long deleteByCustomerIdAndSkuId(long customerId, long skuId)`

**Grpc API**:
* service: deleteSkuForCustomer (CustomerRequest) returns (DeleteReply)
* param: CustomerRequest(customer_id(Long))
* return: DeleteReply(boolean)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

### 3.4.2. `deleteSkuBySessionId`

**Description**: delete sku from shopping cart by session id

**parameters**: sessionId(String)

**return**: deleted(boolean)

**exception**:
* ShoppingCartParamException
 - customer_session is required

**Process**:
* delete sku from shopping cart by session id `Long deleteBySessionIdAndSkuId(String sessionId, long skuId)`

**Grpc API**:
* service: deleteSkuForSession (SessionRequest) returns (DeleteReply)
* param: SessionRequest(session_id(String))
* return: DeleteReply(boolean)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

## 3.5. Clearing Shopping Cart
When customer submit the order or customer click the "clean shopping cart" button, shopping cart will be cleaned.

### 3.5.1. `deleteByCustomerId`

**Description**: delete shopping cart by customer id

**parameters**: customerId(long)

**return**: deleted(boolean)

**exception**:
* ShoppingCartParamException
 - customer_id is required

**Process**:
* clear shopping cart by customer id `boolean deleteByCustomerId(long customerId)`

**Grpc API**:
* service: deleteForCustomer (CustomerRequest) returns (DeleteReply)
* param: CustomerRequest(customer_id(Long))
* return: DeleteReply(boolean)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

### 3.5.2. `deleteBySessionId`

**Description**: delete shopping cart by session id

**parameters**: sessionId(String)

**return**: deleted(boolean)

**exception**:
* ShoppingCartParamException
 - session_id is required

**Process**:
* clear shopping cart by session id `boolean deleteBySessionId(String sessionId)`

**Grpc API**:
* service: deleteForSession (SessionRequest) returns (DeleteReply)
* param: SessionRequest(session_id(Long))
* return: DeleteReply(boolean)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

## 3.6. Abandoning Shopping Cart
When the session of an anonymous customer is expired, the shopping cart will be abandoned.  
This service is triggered when the session expired.

### 3.6.1. `deleteBySessionId`(go to: 3.5.2)
