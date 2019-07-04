package com.maybetm.mplrest.controllers.basket.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.basket.IDBBasket;
import org.springframework.stereotype.Service;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 13:29
 */
@Service
public class BasketService extends AService<Basket, IDBBasket>
{

  public BasketService(IDBBasket repository)
  {
    super(repository);
  }
}
