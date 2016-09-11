package io.reactivesw.shopping.cart

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.context.WebApplicationContext

import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class ShoppingCartServiceStarterTest extends Specification {

  @Autowired
  WebApplicationContext context

  def "should boot up without errors"() {
    expect: "web application context exists"
    context != null
  }
}
