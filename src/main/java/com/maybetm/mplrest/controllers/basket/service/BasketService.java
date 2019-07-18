package com.maybetm.mplrest.controllers.basket.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.basket.IDBBasket;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 13:29
 */
@Service
public class BasketService extends AService<Basket, IDBBasket>
{
  @Autowired
  public BasketService(IDBBasket repository)
  {
    super(repository);
  }

  public Page<Basket> getBasketByClient(Long id, Pageable pageable) {

    return repository.findByAccountId(id, pageable);
  }

  public Page<Basket> getBaskets(Pageable pageable) {

    return repository.findAll(pageable);
  }

  public Optional<Basket> updateBasketLine (Basket basketFromDb, Basket updatableBasket) {
    BeanUtils.copyProperties(updatableBasket, basketFromDb, "id");
    return super.save(basketFromDb);
  }
}
