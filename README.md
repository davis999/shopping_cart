[![Build Status](https://travis-ci.org/reactivesw/shoppingcart.svg?branch=master)](https://travis-ci.org/reactivesw/shoppingcart)
[![codecov](https://codecov.io/gh/reactivesw/shopping_cart/branch/master/graph/badge.svg)](https://codecov.io/gh/reactivesw/shopping_cart)

# shoppingcart
Shopping cart service

Configuration properties:
```
datasource:
  url: jdbc:mysql://localhost/sample?useSSL=false  // db name
  username: sample    // db username
  password: sample    // db password
  driver-class-name: com.mysql.jdbc.Driver
```

GRpc can change port to others:
```
grpc:
  port : 6565
```
