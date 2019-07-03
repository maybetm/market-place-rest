package com.maybetm.mplrest.controllers.basket.controller;

import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.user.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 13:29
 */
public interface IBasketController
{
  // добавить в корзину
  void addProductToBasket(@RequestBody User user, @RequestBody Product product);
  // удалить из корзины
  void deleteProductInBasket(@RequestParam Long id);
  // оплатить выбранные элементы в корзине
  void payProducts(@RequestParam Set<Product> products);
}
