# Shopping Cart Design
This service will be called when customer click the "add to cart" button.
* Add Product to Shopping Cart
* List Shopping Cart for Customer
* Error Handle
* GRPC API
* Database Model

## Add Product to Shopping Cart
Request service supplies sku_id, quantity, inventory, customer_id, session_id.

Validate product parameters, inventory.

If product has enough inventory, save the product to database.
- If product has existed in cart, merge the record.
- If product is new in cart, add the product record.

If product has been added successfully, return the shopping cart id to the client caller.

## List Shopping Cart for Customer
Request service supplies customer_id or session_id.  

Validate parameters required.

- If request shopping cart with customer_id, find the shopping cart list for the customer who has logged in.
- If request shopping cart with session_id, find the shopping cart list for the customer who has not logged in.

Reply the shopping cart list to the request service.

## Error Handle

- If sku_id or inventory is null, return error: *INVALID_ARGUMENT*.
- If both customer id and session id are null, return error: *INVALID_ARGUMENT*.
- If request quantity (merge the quantity that cart database stored) is larger than inventory, thrown SHoppingCartException: *RESOURCE_EXHAUSTED*.
- Other exception, log it and thrown SHoppingCartException: *INTERNAL*.

## GRPC API

### Service
```
service ShoppingCart {
    // Sends a product to add product to shopping cart service
    rpc addProductToShoppingCart(AddRequest) returns (AddReply) {}

    // Lists the shopping cart for customer service
    rpc listShoppingCartForCustomer (CustomerListRequest) returns (ShoppingCartListReply) {}

    // Lists the shopping cart for session service
    rpc listShoppingCartForSession (SessionListRequest) returns (ShoppingCartListReply) {}
}
```

### Grpc DTO definition:
```
message GrpcShoppingCart {
   int64 shopping_cart_id = 1;
   string session_id = 2;
   string customer_id = 3;
   string sku_id = 4;
   int32 quantity = 5;
}
```

### Add product to shopping cart

#### Service API
`addProductToShoppingCart(AddRequest) returns (AddReply) {}`

#### Request:  
Param Name | Required | Description
---------- | -------- | -----------
session_id | No | If customer_id does not exist, required
customer_id | No | If session_id does not exist, required
sku_id | Yes |
quantity | No | Default: 1
inventory | Yes |

Request:  
```
message AddRequest {
    string session_id = 1;
    string customer_id = 2;
    string sku_id = 3;
    int32 quantity = 4;
    int32 inventory = 5;
}
```

#### Response:
Reply:  
```
message AddReply {
  string shopping_cart_id = 1;
}
```

#### Error Code
* `INVALID_ARGUMENT`
* `RESOURCE_EXHAUSTED`
* `INTERNAL`

### list shopping cart for customer who has logged in

#### Service API
`listShoppingCartForCustomer (CustomerListRequest) returns (ShoppingCartListReply) {}`

#### Request:  
Param Name | Required | Description
---------- | -------- | -----------
customer_id | Yes |

Request:  
```
message CustomerListRequest {
    string customer_id = 1;
}
```

#### Response:
Reply:  
```
message ShoppingCartListReply {
    repeated GrpcShoppingCart shoppingCart = 1;
}
```

### list shopping cart for customer who has not logged in

#### Service API
`listShoppingCartForSession (SessionListRequest) returns (ShoppingCartListReply) {}`

#### Request:  
Param Name | Required | Description
---------- | -------- | -----------
session_id | Yes |

Request:  
```
message SessionListRequest {
    string session_id = 1;
}
```

#### Response:
Reply:  
```
message ShoppingCartListReply {
    repeated GrpcShoppingCart shoppingCart = 1;
}
```

## Database Model

### Shopping Cart

Field Name | Not Null | Data Type | Description | Related
---------- | -------- | --------- | ----------- | -------
shopping_cart_id | Yes | String | PK |
customer_id | No | String | FK | Customer
session_id | No | String |  |
sku_id | Yes | String | FK | SKU
quantity | Yes | Int | |
modified_date | Yes | Date | |
