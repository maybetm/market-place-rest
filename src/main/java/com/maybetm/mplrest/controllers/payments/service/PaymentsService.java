package com.maybetm.mplrest.controllers.payments.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.basket.IDBBasket;
import com.maybetm.mplrest.entities.payments.IDBPayment;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.entities.product.IDBProduct;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.exceptions.payments.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

	private IDBProduct idbProduct;
	private IDBBasket idbBasket;

	@Autowired
	public PaymentsService(IDBPayment repository, IDBProduct idbProduct, IDBBasket idbBasket) {
		super(repository);
		this.idbProduct = idbProduct;
		this.idbBasket = idbBasket;
	}

	public List<Payment> getPaymentStatistics (Long clientId, Pageable pageable) {
	  return repository.findByAccountId(clientId, pageable).getContent();
  }

	@Transactional(timeout = 30)
	public void createPayment(Set<Basket> baskets, Long clientId) {
	  // получаем мапу id товара и его количество из корзины, на основе данных от клиента
		final Map<Long, Long> productsFromBasketMap = baskets.stream().collect(
		    Collectors.toMap(o -> o.getProduct().getId(), o -> o.getProduct().getCount()));
    // получаем свежие данные по товарам из бд, по идентификаторам которые прислал клиент
		final Set<Product> productsFromStore = idbProduct.findByIdIn(productsFromBasketMap.keySet());
		// получаем мапу id товара и его количество на основе выборки из бд
		final Map<Long, Long> productsFromStoreMap = productsFromStore
				.stream().collect(Collectors.toMap(Product::getId, Product::getCount));

		verificationCountProducts.accept(productsFromBasketMap, productsFromStoreMap);

    final Function<Product, Long> getUpdatedCountInStore = (p) -> p.getCount() - productsFromStoreMap.get(p.getId());
    for (Basket basket : baskets) {
      // обновляем количесво товаров после покупки
      idbProduct.save(new Product(basket.getProduct().getId(), getUpdatedCountInStore.apply(basket.getProduct())));
      // Добавляем записи в таблицу учёта платежей fixme тут денормализовать таблицу payments, писать в неё
      repository.save(new Payment(clientId, basket.getProduct(), basket.getProduct().getCost(), LocalDateTime.now()));
      // Удаляем выбранные продукты из корзины
      idbBasket.deleteById(basket.getId());
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
