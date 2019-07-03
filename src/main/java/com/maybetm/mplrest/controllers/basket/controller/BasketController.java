package com.maybetm.mplrest.controllers.basket.controller;

import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.user.User;
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

  @Override
  @PutMapping
  public void addProductToBasket(@RequestBody User user, @RequestBody Product product)
  {

  }

  @Override
  @PostMapping
  public void deleteProductInBasket(@RequestParam Long id)
  {

  }

  @Override
  @PostMapping
  public void payProducts(@RequestParam Set<Product> products)
  {

  }
}
