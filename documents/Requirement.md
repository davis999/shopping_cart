# Shopping Cart Requirement

This document describes the requirements for shopping cart service.
This service provides the following functions:

Every customer (logged in or not logged in) can have a shopping cart.

## 1. The purpose of this project
This project provide services for customer to adding product to a shopping cart, displaying shopping cart contents, changing quantity of a product in the shopping cart, and removing products from the shopping cart.

## 2. The output of this project
This project output an docker image.

## 3. The type of service
This project provide grpc API.

## 4. User Requirements
The service supports the following use cases:

### 4.1. Adding a Product to a Shopping Cart
A customer clicks "add to cart" button in the product page, this product will be added to shopping cart.  
* If we only have a button without quantity setting, the added quantity will set to be one.  
* If we have button and quantity setting, we will use this specified quantity.
Considering the both cases, there should be a quantity parameter in the adding product to shopping cart request.

We have total quantity limit check and inventory check. If check failed adding product will failed.

### 4.2. Displaying Shopping Cart Content
When a customer successfully adds a product to the shopping cart or clicks the shopping cart icon in web page, the shopping cart content is retrieved and displayed to the customer thus the customer can edit the shopping cart. 

### 4.3. Editing Shpping Cart
* Customer can change product quantity in the shopping cart.
* Customer can remove product from the shopping cart.

We have total quantity limit check and inventory check. If check failed adding product will failed.

### 4.4. Clearing Shopping Cart
When customer clicks the "x" icon, the sku in the shopping cart will be deleted.

### 4.5. Delete Sku from Shopping Cart
When customer submits the order, shopping cart will be cleaned.

### 4.6. Abandoning Shopping Cart
When the session of an anonymous customer is expired, the shopping cart will be abandoned.

### 4.7. Admin Panel
