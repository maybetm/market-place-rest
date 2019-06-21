package com.maybetm.mplrest.controllers.product;

import com.maybetm.mplrest.entities.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author: ZebzeevSV
 * @version: 09.06.2019 23:53
 */
@RestController
@RequestMapping(value = "product")
public class ProductController {

	private final IDBProduct idbProduct;

	@Autowired
	public ProductController(IDBProduct idbProduct) {
		this.idbProduct = idbProduct;
	}

	@GetMapping(value = "getProducts")
	public String getProducts(@RequestParam Long count,
														@RequestParam Long offset,
														@RequestParam(required = false) String search) {
		return "{\"products\" : \"sd\"}";
	}

	@GetMapping(value = "getProduct")
	public Optional<Product> getProduct(@RequestParam Long id) {
		return idbProduct.findById(id);
	}

	@GetMapping(value = "getAllProducts")
	public Iterable<Product> getAllProducts() {
		return idbProduct.findAll();
	}

	// fixme не реализован
	@PutMapping(value = "updateProduct")
	public void updateProduct(Product product) {

	}
}
