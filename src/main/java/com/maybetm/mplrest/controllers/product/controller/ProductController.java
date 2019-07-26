package com.maybetm.mplrest.controllers.product.controller;

import com.maybetm.mplrest.controllers.product.service.ProductService;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.maybetm.mplrest.security.Roles.admin;
import static com.maybetm.mplrest.security.Roles.client;

/**
 * @author zebzeev-sv
 * @version 09.06.2019 23:53
 */
@RestController
@RequestMapping(value = "product")
public class ProductController implements IProductController<Product> {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping(value = "getProducts")
	public List<Product> getProducts(@RequestParam(required = false, defaultValue = "") String search,
																	 @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
    return productService.getProductsFromDb(search, pageable);
	}

  @GetMapping(value = "getProduct")
  @RolesMapper (roles = {admin , client}) // fixme выбрали этот метод в качестве теста для разработки
	public ResponseEntity<Product> getProduct(@RequestParam Long id) {
		return ResponseEntity.of(productService.findById(id));
	}

	@DeleteMapping(value = "deleteProduct")
	public void deleteProduct(@RequestParam Long id) {
    productService.deleteById(id);
	}

	@PostMapping(value = "createProduct")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		return ResponseEntity.of(productService.save(product));
	}

  @PatchMapping (value = "editProduct")
  public ResponseEntity<Product> editProduct(@RequestParam("id") Product productfromDb,
                                             @RequestBody Product productEdit)
  {
    return ResponseEntity.of(productService.updateEntity(productfromDb, productEdit));
  }

}
