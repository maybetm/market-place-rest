package com.maybetm.mplrest.controllers.product;

import com.maybetm.mplrest.commons.repositories.CommonRepository;
import com.maybetm.mplrest.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author: ZebzeevSV
 * @version: 16.06.2019 21:06
 */
public interface IDBProduct extends CommonRepository<Product> {

	/**
	 * Returns list of products filtered by name
	 *
	 * @param pageable value of name pageable.
	 * @return list of matching products.
	 */
	Page<Product> findAll(Pageable pageable);

	/**
	 * Returns list of products filtered by name
	 *
	 * @param likeName value of name search.
	 * @param pageable value of name pageable.
	 * @return list of matching products.
	 */
	Page<Product> findProductsByNameIgnoreCase(String likeName, Pageable pageable);

}
