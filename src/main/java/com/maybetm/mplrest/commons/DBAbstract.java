package com.maybetm.mplrest.commons;

import javax.persistence.*;

/**
 * @author: ZebzeevSV
 * @version: 09.06.2019 23:42
 */

@MappedSuperclass
public class DBAbstract {

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
