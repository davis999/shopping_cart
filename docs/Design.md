# Shopping Cart Design
This service will be called when customer click the "add to cart" button.
* Add Product to Shopping Cart
* Error Handle
* GRPC API
* Database Model

## Add Product to Shopping Cart
Product supplies SKU, qty, inventory, customer_id, session_id.

Validate product parameters, inventory.

If product has enough inventory, save the product to database.
- If product has existed in cart, merge the record.
- If product is new in cart, add the product record.

If product has been added successfully, return the shopping cart id to the client caller.

## Error Handle

- If SKU or inventory is null, return error: *INVALID_ARGUMENT*.
- If both customer id and session id are null, return error: *INVALID_ARGUMENT*.
- If request qty (merge the qty that cart database stored) is larger than inventory, return error: *RESOURCE_EXHAUSTED*.
- Other exception, log it and return error: *INTERNAL*.

## GRPC API

### Add product to shopping cart

#### Service:
```
service ShoppingCart {
    // Sends a product to add product to shopping cart service
    rpc AddProductToShoppingCart(AddRequest) returns (AddReply) {}

}
```

#### Request:  

Param Name | Required | Description
---------- | -------- | -----------
session_id | No | If customer_id does not exist, required
customer_id | No | If session_id does not exist, required
sku | Yes |
qty | No | Default: 1
inventory | Yes |

Request:  
```
message AddRequest {
    string session_id = 1;
    string customer_id = 2;
    string sku = 3;
    int32 qty = 4;
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

## Database Model

### Shopping Cart

Field Name | Not Null | Data Type | Description | Related
---------- | -------- | --------- | ----------- | -------
shopping_cart_id | Yes | String | PK |
customer_id | No | String | FK | Customer
session_id | No | String |  |
sku | Yes | String | FK | SKU
qty | Yes | Int | |
modified_date | Yes | Date | |
