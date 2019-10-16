package com.maybetm.mplrest.controllers.payments.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.payments.IDBPayment;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.entities.product.IDBProduct;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.exceptions.payments.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 11:01
 */
@Service
public class PaymentsService extends AService<Payment, IDBPayment> {

	private IDBProduct idbProduct;

	@Autowired
	public PaymentsService(IDBPayment repository, IDBProduct idbProduct) {
		super(repository);
		this.idbProduct = idbProduct;
	}

	@Transactional
	public void createPayment(Set<Product> products, Account account) {
		final Map<Long, Long> productsFromBasketMap = products
				.stream().collect(Collectors.toMap(Product::getId, Product::getCount));

		final Set<Product> productsFromStore = idbProduct.findByIdIn(productsFromBasketMap.keySet());

		final Map<Long, Long> productsFromStoreMap = productsFromStore
				.stream().collect(Collectors.toMap(Product::getId, Product::getCount));

		verificationCountProducts.accept(productsFromBasketMap, productsFromStoreMap);

		final Function<Product, Long> getUpdatedCountInStore = (p) -> p.getCount() - productsFromStoreMap.get(p.getId());
		for (Product product : productsFromStore) {
			idbProduct.save(new Product(product.getId(), getUpdatedCountInStore.apply(product)));
			repository.save(new Payment(account, product, product.getCost(), ZonedDateTime.now()));
		}
	}

	private final BiConsumer<Map<Long, Long>, Map<Long, Long>> verificationCountProducts = (fromBasket, fromStore) -> {

		if (fromBasket.isEmpty()) {
			throw new PaymentException("Корзина пользователя пуста.");
		}

		if (fromStore.isEmpty()) {
			throw new PaymentException("На складе не найденно запрашиваемых продуктов.");
		}

		final boolean countProductIsNotEquals = fromBasket.size() != fromStore.size();
		if (countProductIsNotEquals) {
			throw new PaymentException("Запрашиваемое количество товаров не совпадает с количеством товаров на складе.");
		}

		final BiFunction<Long, Long, Boolean> noManyProductsFromStore = (key, value) -> fromStore.get(key) < value;
		fromBasket.forEach((key, value) -> {
			if (noManyProductsFromStore.apply(key, value)) {
				throw new PaymentException("Количество товаров на складе меньше, чем было запрошенно магазином.");
			}
		});
	};
}
