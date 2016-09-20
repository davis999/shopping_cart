# Shopping Cart Requirement

This service provides add product to shopping cart, change quantity of product in shopping cart and remove product in shopping cart.

Every customer (logged in or not logged in) can have a shopping cart.

The service mainly contains:
* Customer clicks "add to cart" button in the product page, this product will be added to shopping cart.  
 - If we only have a button without quantity setting, the added quantity will set to be one.  
 - If we have button and quantity setting, we will use this specified quantity.
* If the product has been added to shopping cart, we will turn to a page that shows the product list of the shopping cart.  
* Customer can change product quantity in the shopping cart.
* Customer can remove product from the shopping cart.
* When customer submit the order, shopping cart will be cleaned.

We have some check for shopping cart:
* Quantity limit.   
 - Max value configuration.
 - Quantity > max: alert max quantity message for customer.
* Inventory check.
 - Inventory = 0: alert null inventory message for customer.
 - Inventory < quantity: alert less inventory message for customer.

The temp shopping cart (requested by the customer who has not logged in) can be clean by a cron job.
* Expired time configuration.
* Cron job to clean these temp shopping cart.
