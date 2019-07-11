package com.maybetm.mplrest.entities.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;

import javax.persistence.Entity;

/**
 * Сущность категорий продуктов
 *
 * @author zebzeev-sv
 * @version 19.06.2019 20:46
 */
@Entity(name = "category")
@JsonIgnoreProperties({"product"})
public class Category extends AEntity {

	private String name;

/*	private Set<Product> product;*/
/*

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	public Set<Product> getProduct() {
		return product;
	}
*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

/*	public void setProduct(Set<Product> product) {
		this.product = product;
	}*/
}
