package com.maybetm.mplrest.commons.repositories;

import com.maybetm.mplrest.commons.DBAbstract;
import org.springframework.data.repository.CrudRepository;
/**
 * @author: ZebzeevSV
 * @version: 09.06.2019 23:36
 */
public interface IRepository<Model extends DBAbstract> extends CrudRepository<Model, Long> {
}
