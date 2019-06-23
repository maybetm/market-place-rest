package com.maybetm.mplrest.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maybetm.mplrest.commons.AEntity;

import javax.persistence.*;

/**
 * Модель товара в магазине
 *
 * @author: ZebzeevSV
 * @version: 09.06.2019 23:43
 */
@Entity(name = "products")
public class Product extends AEntity {

	private String name;

	private String info;

	private Long cost;

	private Long count;

	private String logo;

	private Long marketId;

	private Category category;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "categoryId")
  public Category getCategory() {
    return category;
  }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}
}
