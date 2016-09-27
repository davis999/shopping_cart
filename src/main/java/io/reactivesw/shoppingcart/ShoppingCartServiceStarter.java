package io.reactivesw.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootApplication for service start.
 * @author janeli
 */
@SpringBootApplication
public class ShoppingCartServiceStarter {

  /**
   * spring boot start.
   * @param args default parameters String[]
   */
  public static void main(String[] args) {
    SpringApplication.run(ShoppingCartServiceStarter.class, args);
  }
}
