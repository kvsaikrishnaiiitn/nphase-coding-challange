package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();

    @Test
    public void calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }
    
    //Task2
    @Test
    public void calculatesPriceWithDiscount()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3)
        ));

        BigDecimal result = service.calculateTotalPriceWithDiscount(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(33.0));
    }
    
    //Task3
    @Test
    public void calculatesPriceWithCategoryDiscount()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, "Drinks"),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, "Drinks"),
                new Product("Coffee", BigDecimal.valueOf(8), 2, "Food")
        ));

        BigDecimal result = service.calculateTotalPriceWithCategoryDiscount(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(31.84));
    }

}