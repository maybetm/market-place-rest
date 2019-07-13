package com.maybetm.mplrest.entities.product;

import com.maybetm.mplrest.commons.repositories.ICommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 16.06.2019 21:06
 */
public interface IDBProduct extends ICommonRepository<Product>
{

  /**
   * Поиск товаров по введеному названию
   *
   * @param likeName входной параметр с предполоагаемым наименованием продукта.
   * @param pageable параметр отвечающий за пагинацию результирующего списка.
   * @return для получения списка товаров требуется вызвать метод getContent(), возвращаемого объекта Page<Product>.
   */
  Page<Product> findProductsByNameIgnoreCase(String likeName, Pageable pageable);

  /**
   * Получить список товаров по их уникальным идентификаторам
   *
   * @param ids - список уникальных идентификаторов
   * @return - используем пагинацию, если вдруг вернётся слишком много записей.
   */
  List<QueryProductIdAndCount> findByIdIn (Set<Long> ids);
}
