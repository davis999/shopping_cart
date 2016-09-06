# Shopping Cart Design
This service will be called when customer click the "add to cart" button.
* Add Product to Shopping Cart
* Error Handle
* GRPC API
* Database Model

## Add Product to Shopping Cart
Product supplies SKU, qty, inventory, customer_id, session_id.

Validate product SKU, inventory.

If product has enough inventory, save the product to database.
- If product has existed in cart, merge the record.
- If product is new in cart, add the product record.

If product has been added successfully, return "success" message to the caller.

## Error Handle

- If SKU is a wrong number, return error: *GRPC_STATUS_UNIMPLEMENTED*.
- If request qty (merge the qty that cart database stored) is larger than inventory, return error: *GRPC_STATUS_UNIMPLEMENTED*.
- Other exception, log it and return error: *GRPC_STATUS_UNKNOWN*.

## GRPC API

### Add product to shopping cart

#### Request:  

Param Name | Required | Description
---------- | -------- | -----------
session_id | No | If customer_id does not exist, required
customer_id | No | If session_id does not exist, required
sku | Yes |
qty | No | Default: 1

Example:  
`TBD
`

#### Response:

Example:  
`TBD
`

#### Error Code
TBD

## Database Model

### Shopping Cart

Field Name | Not Null | Data Type | Description | Related
---------- | -------- | --------- | ----------- | -------
cart_id | Yes | String | PK |
customer_id | No | String | FK | Customer
session_id | No | String |  |
sku | Yes | String | FK | SKU
qty | Yes | Int | |
modified_date | Yes | Date | |
