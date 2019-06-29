package com.maybetm.mplrest.controllers.product;

import com.maybetm.mplrest.entities.product.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author: ZebzeevSV
 * @version: 09.06.2019 23:53
 */
@RestController
@RequestMapping (value = "product")
public class ProductController
{

  private final IDBProduct idbProduct;

  @Autowired
  public ProductController(IDBProduct idbProduct)
  {
    this.idbProduct = idbProduct;
  }

  @GetMapping (value = "getProducts")
  public List<Product> getProducts(@RequestParam (required = false, defaultValue = "") String search,
                                   @PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
  {
    if (search != null && !search.isEmpty()) {
      return idbProduct.findProductsByNameIgnoreCase(search, pageable).getContent();
    } else {
      return idbProduct.findAll(pageable).getContent();
    }
  }

  @GetMapping (value = "getProduct")
  public Optional<Product> getProduct(@RequestParam Long id)
  {
    return idbProduct.findById(id);
  }

  @GetMapping (value = "getAllProducts")
  public Iterable<Product> getAllProducts()
  {
    return idbProduct.findAll();
  }

  @DeleteMapping (value = "deleteProduct")
  public void deleteProduct(@RequestParam Long id)
  {
    idbProduct.deleteById(id);
  }

  @PatchMapping (value = "editProduct")
  public Product editProduct(@RequestParam("id") Product productfromDb,
                             @RequestBody Product productEdit)
  {
    BeanUtils.copyProperties(productEdit, productfromDb, "id");
    return idbProduct.save(productfromDb);
  }

  @PutMapping (value = "createProduct")
  public Product createProduct(@RequestBody Product product)
  {
    return idbProduct.save(product);
  }
}
