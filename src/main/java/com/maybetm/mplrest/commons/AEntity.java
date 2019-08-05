package com.maybetm.mplrest.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;

/**
 * Базовый класс для всех сущностей jpa
 * Все наследники должны использовать приватные поля с геттерами и сеттерами.
 *
 * @author zebzeev-sv
 * @version 09.06.2019 23:42
 */
@JsonInclude(Include.NON_NULL)
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
