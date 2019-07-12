package com.maybetm.mplrest.controllers.basket.controller;

import com.maybetm.mplrest.controllers.basket.service.BasketService;
import com.maybetm.mplrest.entities.basket.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 13:28
 */
@RestController
@RequestMapping (value = "basket")
public class BasketController implements IBasketController<Basket>
{

  private final BasketService basketService;

  @Autowired
  public BasketController(BasketService basketService)
  {
    this.basketService = basketService;
  }

  @Override
  @PostMapping(value = "addProductToBasket")
  public ResponseEntity<Basket> addProductToBasket(@RequestBody Basket basket)
  {
    return ResponseEntity.of(basketService.save(basket));
  }

  @Override
  @GetMapping (value = "getBasketByClientId")
  public List<Basket> getBasketByClientId(@RequestParam Long id,
                                          @PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
  {
    return basketService.getBasketByClient(id, pageable).getContent();
  }

  @Override
  @GetMapping (value = "getBaskets")
  public List<Basket> getBaskets(@PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
  {
    return basketService.getBaskets(pageable).getContent();
  }

  @Override
  @DeleteMapping(value = "deleteProductInBasket")
  public void deleteProductInBasket(@RequestParam Long id)
  {
    basketService.deleteById(id);
  }

  @Override
  @PostMapping (value = "updateBasketLine")
  public ResponseEntity<Basket> updateBasketLine(@RequestParam ("id") Basket basketFromDb,
                                                 @RequestBody Basket updatableBasket)
  {
    return ResponseEntity.of(basketService.updateBasketLine(basketFromDb, updatableBasket));
  }

}
