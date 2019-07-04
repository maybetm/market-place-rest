package com.maybetm.mplrest.controllers.basket.controller;

import com.maybetm.mplrest.controllers.basket.service.BasketService;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 13:28
 */
@RestController
@RequestMapping (value = "basket")
public class BasketController implements IBasketController
{

  private final BasketService basketService;

  @Autowired
  public BasketController(BasketService basketService)
  {
    this.basketService = basketService;
  }

  @Override
  @PutMapping(value = "addProductToBasket")
  public void addProductToBasket(@RequestBody User user, @RequestBody Product product)
  {
    // надо сделать поле product_id в корзине уникальным для конкретного юзера.
    // пользователь апи не может добавить ещё один такой же товар в корзину,
    // для этого нужно использовать функцию обновления позиции в корзине.
  }

  @Override
  @PostMapping(value = "deleteProductInBasket")
  public void deleteProductInBasket(@RequestParam Long id)
  {
    basketService.deleteById(id);
  }

  @Override
  @PostMapping(value = "updateBasketLine")
  public void updateBasketLine(@RequestParam Long id)
  {

  }

  @Override
  @PostMapping(value = "payProducts")
  public void payProducts(@RequestParam Set<Product> products)
  {
    // вот тут интересный момент
    // раньше я вызывал тут хранимую процедуру, а сейчас как быть?
  }
}
