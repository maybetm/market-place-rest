package com.maybetm.mplrest.controllers.basket.controller;

import com.maybetm.mplrest.controllers.basket.service.BasketService;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.maybetm.mplrest.security.constants.Roles.admin;
import static com.maybetm.mplrest.security.constants.Roles.client;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 13:28
 */
@RestController
@RequestMapping (value = "basket")
public class BasketController
{

  private final BasketService basketService;

  @Autowired
  public BasketController(BasketService basketService)
  {
    this.basketService = basketService;
  }

  /**
   * Добавление новой позиции в корзину
   *
   * @param basket fixme - корзину?
   * @return возвращает сохранённый объект
   */
  @PostMapping(value = "addProductToBasket")
  @RolesMapper (roles = {client})
  public ResponseEntity<Basket> addProductToBasket(@RequestBody Basket basket)
  {
    return ResponseEntity.of(basketService.save(basket));
  }

  /**
   * Получение корзины покупателя
   *
   * @param id - идентификатор покупателя
   * @param pageable - метод использует api пагинации
   * @return - получаем список позиций в корзине
   */
  @GetMapping (value = "getBasketByClientId")
  @RolesMapper (roles = {admin , client})
  public List<Basket> getBasketByClientId(@RequestParam Long id,
                                          @PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
  {
    return basketService.getBasketByClient(id, pageable).getContent();
  }

  /**
   * Выгрузка всех корзин с учётом пагинации
   *
   * @param pageable - api пагинации
   * @return - список позиций по всем пользователям, по умолчанию выдаёт 20 записей
   */
  @GetMapping (value = "getBaskets")
  @RolesMapper (roles = {admin})
  public List<Basket> getBaskets(@PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
  {
    return basketService.getBaskets(pageable).getContent();
  }

  /**
   * Удаление объекта из корзины по basketId
   *
   * @param id - уникальный идентификатор позиции в корзине
   */
  @DeleteMapping(value = "deleteProductInBasket")
  @RolesMapper (roles = {admin, client})
  public void deleteProductInBasket(@RequestParam Long id)
  {
    basketService.deleteById(id);
  }

  /**
   * Обновление данных позиции в корзине
   *
   * @param basketFromDb - сущность корзины выгружаемая из бд
   * @param updatableBasket - изменённые поля позиции в корзине
   * @return - возвращает обновлённую сущность
   */
  @PostMapping (value = "updateBasketLine")
  @RolesMapper (roles = {admin , client})
  public ResponseEntity<Basket> updateBasketLine(@RequestParam ("id") Basket basketFromDb,
                                                 @RequestBody Basket updatableBasket)
  {
    return ResponseEntity.of(basketService.updateEntity(basketFromDb, updatableBasket));
  }
}
