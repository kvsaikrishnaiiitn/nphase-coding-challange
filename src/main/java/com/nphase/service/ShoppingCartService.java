package com.nphase.service;

import java.math.BigDecimal;
import java.util.function.Function;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

public class ShoppingCartService {

	public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
		return shoppingCart.getProducts().stream()
				.map(product -> product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
				.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	// Task2
	public BigDecimal calculateTotalPriceWithDiscount(ShoppingCart shoppingCart) {

		Function<Product, BigDecimal> discountFunction = (product) -> {
			BigDecimal discount = null;
			BigDecimal productPrice = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));

			if (product.getQuantity() > 3) {
				discount = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()))
						.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100));
			}
			return discount != null ? productPrice.subtract(discount) : productPrice;
		};

		return shoppingCart.getProducts().stream().map(discountFunction).reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}

	// Task3
	public BigDecimal calculateTotalPriceWithCategoryDiscount(ShoppingCart shoppingCart) {

		Function<Product, BigDecimal> discountCategoryFunction = (product) -> {

			int totalCategoryProductsCount = shoppingCart.getProducts().stream()
					.filter(eachProduct -> eachProduct.getCategory().equals(product.getCategory()))
					.map(eachProduct -> eachProduct.getQuantity()).reduce(Integer::sum).orElse(0);

			BigDecimal discount = null;
			BigDecimal productPrice = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));

			if (totalCategoryProductsCount > 3) {
				discount = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()))
						.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100));
			}
			return discount != null ? productPrice.subtract(discount) : productPrice;
		};

		return shoppingCart.getProducts().stream().map(discountCategoryFunction).reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}

}
