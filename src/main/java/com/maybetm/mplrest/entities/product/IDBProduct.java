package com.maybetm.mplrest.entities.product;

import com.maybetm.mplrest.commons.repositories.ICommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author: ZebzeevSV
 * @version: 16.06.2019 21:06
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

}
