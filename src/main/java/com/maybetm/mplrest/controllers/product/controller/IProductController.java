package com.maybetm.mplrest.controllers.product.controller;

import com.maybetm.mplrest.entities.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zebzeev-sv
 * @version 01.07.2019 14:36
 */
public interface IProductController<E>
{

  ResponseEntity<E> editProduct(@RequestParam ("id") Product productfromDb, @RequestBody Product productEdit);

  ResponseEntity<E> createProduct(@RequestBody Product product);

  void deleteProduct(@RequestParam Long id);

  ResponseEntity<E> getProduct(@RequestParam Long id);

  List<E> getProducts(@RequestParam (required = false, defaultValue = "") String search,
                      @PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable);

}
