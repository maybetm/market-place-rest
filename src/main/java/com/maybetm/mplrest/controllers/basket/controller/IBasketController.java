package com.maybetm.mplrest.controllers.basket.controller;

import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 13:29
 */
public interface IBasketController<E>
{
  // добавить в корзину
  ResponseEntity<E> addProductToBasket(@RequestBody E basket);
  // Получить корзину по id покупателя
  Optional<E> getBasketByClientId(@RequestParam Long id,
                            @PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable);
  // получить все корзины с учётом пагинации
  List<Basket> getBaskets(@PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable);
  // удалить из корзины
  void deleteProductInBasket(@RequestParam Long id);
  // изменить данные позиции в корзине
  void updateBasketLine(@RequestParam Long id);
  // оплатить выбранные элементы в корзине
  void payProducts(@RequestParam Set<Product> products);
}
