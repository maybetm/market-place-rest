package com.maybetm.mplrest.controllers.product.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.product.IDBProduct;
import com.maybetm.mplrest.entities.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zebzeev-sv
 * @version 01.07.2019 12:29
 */
@Service
public class ProductService extends AService<Product, IDBProduct>
{

  public ProductService(IDBProduct repository)
  {
    super(repository);
  }

  public List<Product> getProductsFromDb(String search, Pageable pageable)
  {
    if (search != null && !search.isEmpty()) {
      return repository.findProductsByNameIgnoreCase(search, pageable).getContent();
    } else {
      return repository.findAll(pageable).getContent();
    }
  }
}
