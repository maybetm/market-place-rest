package com.maybetm.mplrest.controllers.payments.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.payments.IDBPayment;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.entities.product.IDBProduct;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.product.QueryProductIdAndCount;
import com.sun.xml.internal.txw2.IllegalSignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 11:01
 */
@Service
public class PaymentsService extends AService<Payment, IDBPayment>
{

  private IDBProduct idbProduct;

  @Autowired
  public PaymentsService(IDBPayment repository, IDBProduct idbProduct)
  {
    super(repository);
    this.idbProduct = idbProduct;
  }

  public ResponseEntity<Set<Payment>> createPayment(Set<Product> products)
  {

    // fixme валидацию можно перенести в отдельный дефолтный метод

    final Map<Long, Long> productsFromBasket = products
        .stream().collect(Collectors.toMap(Product::getId, Product::getCount));

    final Map<Long, Long> productsFromStore = idbProduct.findByIdIn(productsFromBasket.keySet())
        .stream().collect(Collectors.toMap(QueryProductIdAndCount::getId, QueryProductIdAndCount::getCount));

    if (productsFromBasket.isEmpty()) {
      throw new IllegalSignatureException("Корзина пользователя пуста.");
    }

    if (productsFromStore.isEmpty()) {
      throw new IllegalSignatureException("На складе не найденно запрашиваемых продуктов.");
    }

    final Supplier<Boolean> countProductIsNotEquals = () -> productsFromBasket.size() != productsFromStore.size();
    if (countProductIsNotEquals.get()) {
      throw new IllegalSignatureException("Запрашиваемое количество товаров не совпадает с количеством товаров на складе.");
    }

    final BiFunction<Long, Long, Boolean> noManyProductsFromStore = (key, value) -> productsFromStore.get(key) < value;
    productsFromBasket.forEach((key, value) -> {
      if (noManyProductsFromStore.apply(key, value)) {
        throw new IllegalSignatureException("Количество товаров на складе меньше, чем было запрошенно.");
      }
    });

    // сперва надо чекнуть количество продуктов, не превышает ли допустимый лимит
    // вернём ошибку, если превысил

    // если количество продуктов не превышает лимит,
    // то вычетаем количество покупаемых продуктов из products

    // создаём запись в таблице payments с каждой позицией
    // product_id, product_name, category, count, cost, datetime
    // можно сделать просто embedded коллекцию

    return null;
  }
}
