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
* service: listShoppingCartForCustomer (CustomerShoppingCartListRequest) returns (ShoppingCartListReply)
* param: CustomerShoppingCartListRequest(customer_id(Long))
* return: ShoppingCartListReply(List<GrpcShoppingCart>)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

#### 2.1.2. Request by session id
* service: listShoppingCartForSession (SessionShoppingCartListRequest) returns (ShoppingCartListReply)
* param: SessionShoppingCartListRequest(session_id(String))
* return: ShoppingCartListReply(List<GrpcShoppingCart>)
* exception: StatusRuntimeException
 - INVALID_ARGUMENT

### 2.2. Domain Service

#### 2.2.1. Workflow
* get shopping cart list
 - get shopping cart list by customer id `List<ShoppingCart> listCustomerShoppingCart(long customerId)`
 - get shopping cart list by session id `List<ShoppingCart> listSessionShoppingCart(String sessionId)`
* call sku grpc service to get product information `List getProductsInfo(List<long> skuIdList)`
 - list shopping cart sku id
 - call sku grpc service
 - list shopping cart product information

#### 2.2.2. Exception
* ShoppingCartParamException
 - customer_id is required: `listCustomerShoppingCart`
 - session_id is required: `listSessionShoppingCart`

### 2.3 Error Handle

Grpc Status | Grpc Message | Customerized Exception | Customerized Message
----------- | ------------ | ---------------------- | --------------------
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | customer_id is required
INVALID_ARGUMENT | parameters are invalid | ShoppingCartParamException | session_id is required

## 3. Editing Shpping Cart

## 4. Clearing Shopping Cart 

## 5. Abandoning Shopping Cart