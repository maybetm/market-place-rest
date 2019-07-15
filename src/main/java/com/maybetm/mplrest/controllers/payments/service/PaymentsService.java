package com.maybetm.mplrest.controllers.payments.service;

import com.maybetm.mplrest.commons.exeptions.payments.PaymentExeption;
import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.payments.IDBPayment;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.entities.product.IDBProduct;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.user.User;
import com.sun.xml.internal.txw2.IllegalSignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
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
  public void createPayment(Set<Product> products, User user)
  {
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

    final Function<Product, Long> updateCountInStore = (p) -> p.getCount() - productsFromStoreMap.get(p.getId());
    for (Product product : products) {
      idbProduct.save(new Product(product.getId(), updateCountInStore.apply(product)));
      repository.save(new Payment(user, product, product.getCost(), ZonedDateTime.now()));
    }
  }


}
