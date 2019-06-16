package com.maybetm.mplrest.controllers.product;

import com.maybetm.mplrest.controllers.product.db.DBProduct;
import com.maybetm.mplrest.controllers.product.db.IDBProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * @author: ZebzeevSV
 * @version: 09.06.2019 23:53
 */
@RestController
@RequestMapping(value = "product", produces = "application/json")
public class ProductController {

	private final IDBProduct idbProduct;

	@Autowired
	public ProductController(IDBProduct idbProduct) {
		this.idbProduct = idbProduct;
	}

	@GetMapping(value = "getProducts")
	public String getProducts(@RequestParam Long count,
														@RequestParam Long offset,
														@RequestParam(required = false) String search)
	{
		return "{\"products\" : \"sd\"}";
	}

	@GetMapping(value = "getProduct")
	public Optional<DBProduct> getProduct(@RequestParam Long id)
	{
		return idbProduct.findById(id);
	}

	@GetMapping(value = "getAllProducts")
	public Iterable<DBProduct> getAllProducts(@RequestParam Long id)
	{
		return idbProduct.findAll();
	}
}
