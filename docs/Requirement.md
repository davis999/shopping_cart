# Shopping Cart Requirement

This service provides the following functions: adding product to a shopping cart, displaying shopping cart contents, changing quantity of a product in the shopping cart, and removing a product in the shopping cart.

Every customer (logged in or not logged in) can have a shopping cart.

## 1. Use Cases
The service supports the following use cases:

### 1.1. Adding a Product to a Shopping Cart
A customer clicks "add to cart" button in the product page, this product will be added to shopping cart.  
* If we only have a button without quantity setting, the added quantity will set to be one.  
* If we have button and quantity setting, we will use this specified quantity.
Considering the both cases, there should be a quantity parameter in the adding product to shopping cart request. 

### 1.2. Displaying Shopping Cart Content
When a customer successfully adds a product to the shopping cart or clicks the shopping cart icon in web page, the shopping cart content is retrieved and displayed to the customer thus the customer can edit the shopping cart. 

### 1.3. Editing Shpping Cart
* Customer can change product quantity in the shopping cart.
* Customer can remove product from the shopping cart.

### 1.4. Clearing Shopping Cart 
When customer submit the order, shopping cart will be cleaned.

### 1.5. Abandoning Shopping Cart
When the session of an anonymous customer is expired, the shopping cart will be abandoned. This can be performed by a separate daemon job. 


## 2. Error Handling
We have some checks when updating shopping cart:
* Quantity limit.   
 - There is a max value for the total number of item in a shopping cart. It is a configurable value.
 - Quantity > max: alert max quantity message for customer.

* Inventory check.
 - Inventory = 0 or product not available: alert zero inventory message for customer.
 - Inventory < quantity: alert less inventory message for customer.
