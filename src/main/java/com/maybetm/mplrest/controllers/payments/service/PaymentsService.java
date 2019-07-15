package com.maybetm.mplrest.controllers.payments.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.payments.IDBPayment;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.entities.product.IDBProduct;
import com.maybetm.mplrest.entities.product.Product;
import com.sun.xml.internal.txw2.IllegalSignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
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

  @Transactional
  public ResponseEntity<Set<Payment>> createPayment(Set<Product> products)
  {
    // fixme валидацию можно перенести в отдельный дефолтный метод

    final Map<Long, Long> productsFromBasket = products
        .stream().collect(Collectors.toMap(Product::getId, Product::getCount));

    final Map<Long, Long> productsFromStoreMap = idbProduct.findByIdIn(productsFromBasket.keySet())
        .stream().collect(Collectors.toMap(Product::getId, Product::getCount));

    if (productsFromBasket.isEmpty()) {
      throw new IllegalSignatureException("Корзина пользователя пуста.");
    }

    if (productsFromStoreMap.isEmpty()) {
      throw new IllegalSignatureException("На складе не найденно запрашиваемых продуктов.");
    }

    final Supplier<Boolean> countProductIsNotEquals = () -> productsFromBasket.size() != productsFromStoreMap.size();
    if (countProductIsNotEquals.get()) {
      throw new IllegalSignatureException("Запрашиваемое количество товаров не совпадает с количеством товаров на складе.");
    }

    final BiFunction<Long, Long, Boolean> noManyProductsFromStore = (key, value) -> productsFromStoreMap.get(key) < value;
    productsFromBasket.forEach((key, value) -> {
      if (noManyProductsFromStore.apply(key, value)) {
        throw new IllegalSignatureException("Количество товаров на складе меньше, чем было запрошенно.");
      }
    });

    // потом начать ходить по ней и корректировать количество продуктов на складе
    final Function<Product, Long> updateCountInStore = (p) -> p.getCount() - productsFromStoreMap.get(p.getId());
    products.forEach(product -> idbProduct.save(new Product(product.getId(), updateCountInStore.apply(product))));

    // создать коллекцию Set<Payment>

    // сохранить коллекцию Set<Payment>

    return null;
  }


}
