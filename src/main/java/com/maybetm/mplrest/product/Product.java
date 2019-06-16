package com.maybetm.mplrest.product;

import com.maybetm.mplrest.commons.DBAbstract;
import com.maybetm.mplrest.market.Market;

import javax.persistence.Entity;

/**
 * Модель товара
 *
 * @author: ZebzeevSV
 * @version: 09.06.2019 23:43
 */


public class Product extends DBAbstract {

	private Market market;

	private String name;

	private String info;

	private Long cost;

	private Long count;
}
