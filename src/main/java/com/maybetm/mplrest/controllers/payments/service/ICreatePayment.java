package com.maybetm.mplrest.controllers.payments.service;

import com.maybetm.mplrest.commons.exeptions.payments.PaymentExeption;
import com.maybetm.mplrest.entities.product.IDBProduct;
import com.maybetm.mplrest.entities.product.Product;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@FunctionalInterface
public interface ICreatePayment {

	void createPayment(Set<Product> product);

	default ICreatePayment andThen(ICreatePayment after, Set<Product> products, IDBProduct idbProduct) {
		Objects.requireNonNull(idbProduct);
		final Map<Long, Long> productsFromBasket = products
				.stream().collect(Collectors.toMap(Product::getId, Product::getCount));

		final Map<Long, Long> productsFromStoreMap = idbProduct.findByIdIn(productsFromBasket.keySet())
				.stream().collect(Collectors.toMap(Product::getId, Product::getCount));

		if (productsFromBasket.isEmpty()) {
			throw new PaymentExeption("Корзина пользователя пуста.");
		}

		if (productsFromStoreMap.isEmpty()) {
			throw new PaymentExeption("На складе не найденно запрашиваемых продуктов.");
		}

		final Supplier<Boolean> countProductIsNotEquals = () -> productsFromBasket.size() != productsFromStoreMap.size();
		if (countProductIsNotEquals.get()) {
			throw new PaymentExeption("Запрашиваемое количество товаров не совпадает с количеством товаров на складе.");
		}

		final BiFunction<Long, Long, Boolean> noManyProductsFromStore = (key, value) -> productsFromStoreMap.get(key) < value;
		productsFromBasket.forEach((key, value) -> {
			if (noManyProductsFromStore.apply(key, value)) {
				throw new PaymentExeption("Количество товаров на складе меньше, чем было запрошенно.");
			}
		});

		return (Set<Product> product) -> {createPayment(products); after.createPayment(product);};
	}
}
