package com.maybetm.mplrest.commons;

import javax.persistence.*;

/**
 * Базовый класс для всех сущностей jpa
 *
 * @author: ZebzeevSV
 * @version: 09.06.2019 23:42
 */
@MappedSuperclass
public abstract class AEntity {

	private Long id;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
