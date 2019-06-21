package com.maybetm.mplrest.commons.repositories;

import com.maybetm.mplrest.commons.AEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author: ZebzeevSV
 * @version: 09.06.2019 23:36
 */
@NoRepositoryBean
public interface CommonRepository<Model extends AEntity> extends JpaRepository<Model, Long> {
}
