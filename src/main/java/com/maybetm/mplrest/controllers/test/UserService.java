package com.maybetm.mplrest.controllers.test;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.jpaTest.User;

/**
 * @author: ZebzeevSV
 * @version: 29.06.2019 18:57
 */
public class UserService extends AService<User, IDBTestController> {

	public UserService(IDBTestController repository) {
		super(repository);
	}
}
