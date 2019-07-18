package com.maybetm.mplrest.entities.basket;

import com.maybetm.mplrest.commons.repositories.ICommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 13:36
 */
public interface IDBBasket extends ICommonRepository<Basket>
{

  /**
   * Получить корзину по Id клиента
   *
   * @param userId   уникальный идентификатор пользователя
   * @param pageable пагинация коллекции
   * @return возвращает контект с учётом пагинации
   */
  Page<Basket> findByAccountId(Long userId, Pageable pageable);

}
