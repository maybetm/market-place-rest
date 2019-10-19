package com.maybetm.mplrest.controllers.payments.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.controllers.account.service.AccountService;
import com.maybetm.mplrest.controllers.product.service.ProductService;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.basket.IDBBasket;
import com.maybetm.mplrest.entities.payments.IDBPayment;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.exceptions.payments.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

	private IDBBasket idbBasket;
	private ProductService productService;
	private AccountService accountService;

	@Autowired
	public PaymentsService(IDBPayment repository, ProductService productService, IDBBasket idbBasket, AccountService accountService) {
		super(repository);
		this.productService = productService;
		this.idbBasket = idbBasket;
		this.accountService = accountService;
	}

	public List<Payment> getPaymentStatistics(Long clientId, Pageable pageable) {
		return repository.findByAccountId(clientId, pageable).getContent();
	}

	@Transactional(timeout = 40)
	public void createPayment(Set<Basket> baskets, Long clientId, String token) {
		// получаем мапу id товара и его количество из корзины, на основе данных полученных от клиента
		final Map<Long, Long> productsFromBasketMap = baskets.stream()
				.collect(Collectors.toMap(o -> o.getProduct().getId(), o -> o.getProduct().getCount()));
		// получаем свежие данные по товарам из бд, по идентификаторам которые прислал клиент
		final Set<Product> productsDb = productService.findProductsByIds(productsFromBasketMap.keySet());
		// получаем мапу id товара и его количество на основе выборки из бд
		final Map<Long, Long> productsFromStoreMap = productsDb
				.stream().collect(Collectors.toMap(Product::getId, Product::getCount));

		// наконец выполняем валидацию
		verificationCountProducts.accept(productsFromBasketMap, productsFromStoreMap);
		// выполняем создание платежа
		createPaymentTransaction(baskets, clientId, token);
	}

	private void createPaymentTransaction(Set<Basket> baskets, Long clientId, String token) {
		final Function<Basket, Long> getNewProductCount = (b) -> b.getProduct().getCount() - b.getCount();
		for (Basket basket : baskets) {
			final Product productDB = productService.findById(basket.getProduct().getId()).get();
			final Product productUp = new Product(basket.getProduct().getId(), getNewProductCount.apply(basket));
			// обновляем количесво товаров после покупки
			productService.updateEntity(productDB, productUp);

			final Account client = accountService.findById(clientId).get();
			// Добавляем записи в таблицу учёта платежей
			repository.save(new Payment(client, token));
			// Удаляем выбранные продукты из корзины
			idbBasket.deleteById(basket.getId());
		}
	}

	/**
	 * Сверяем количество товаров которые запросил пользователь,
	 * с тем что имеется в бд
	 */
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
