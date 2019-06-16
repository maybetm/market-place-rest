package com.maybetm.mplrest.controllers.product.db;

import com.maybetm.mplrest.commons.DBAbstract;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.math.BigDecimal;
/**
 * @author: ZebzeevSV
 * @version: 16.06.2019 20:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "goods")
public class DBProduct extends DBAbstract {

	private String name;

	private String info;

	private Long count;

	private BigDecimal price;

	private String logo;

}


