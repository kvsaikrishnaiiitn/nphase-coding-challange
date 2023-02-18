package com.nphase.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

public class ShoppingCartServiceTest {
	private final ShoppingCartService service = new ShoppingCartService();

	@Test
	public void calculatesPrice() {
		ShoppingCart cart = new ShoppingCart(Arrays.asList(new Product("Tea", BigDecimal.valueOf(5.0), 2),
				new Product("Coffee", BigDecimal.valueOf(6.5), 1)));

		BigDecimal result = service.calculateTotalPrice(cart);

		Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
	}

	// Task2
	@Test
	public void calculatesPriceWithDiscount() {
		ShoppingCart cart = new ShoppingCart(Arrays.asList(new Product("Tea", BigDecimal.valueOf(5.0), 5),
				new Product("Coffee", BigDecimal.valueOf(3.5), 3)));

		BigDecimal result = service.calculateTotalPriceWithDiscount(cart);

		Assertions.assertEquals(result, BigDecimal.valueOf(33.0));
	}

	// Task3
	@Test
	public void calculatesPriceWithCategoryDiscount() {
		ShoppingCart cart = new ShoppingCart(Arrays.asList(new Product("Tea", BigDecimal.valueOf(5.3), 2, "Drinks"),
				new Product("Coffee", BigDecimal.valueOf(3.5), 2, "Drinks"),
				new Product("Cheese", BigDecimal.valueOf(8), 2, "Food")));

		BigDecimal result = service.calculateTotalPriceWithCategoryDiscount(cart);

		Assertions.assertEquals(result, BigDecimal.valueOf(31.84));
	}

	// Task4
	@Test
	public void calculatesPriceWithConfiguration() {

		try (InputStream inputStream = new FileInputStream("application.properties");) {
			Properties properties = new Properties();
			properties.load(inputStream);

			// get the property values
			String teaPrice = properties.getProperty("tea.price.per.unit");
			String coffeePrice = properties.getProperty("coffee.price.per.unit");
			String cheesePrice = properties.getProperty("cheese.price.per.unit");
			String discountPercentage = properties.getProperty("discount.percentage");

			if (teaPrice != null && !teaPrice.isEmpty() && coffeePrice != null && !coffeePrice.isEmpty()
					&& cheesePrice != null && !cheesePrice.isEmpty() && discountPercentage != null
					&& !discountPercentage.isEmpty()) {

				ShoppingCart cart = new ShoppingCart(
						Arrays.asList(new Product("Tea", new BigDecimal(teaPrice), 2, "Drinks"),
								new Product("Coffee", new BigDecimal(coffeePrice), 2, "Drinks"),
								new Product("Cheese", new BigDecimal(cheesePrice), 2, "Food")));

				BigDecimal result = service.calculatesPriceWithConfiguration(cart, Integer.valueOf(discountPercentage));

				Assertions.assertEquals(result, BigDecimal.valueOf(31.84));
				
			} else {
				System.err.print("Required configuraiton is not available.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}