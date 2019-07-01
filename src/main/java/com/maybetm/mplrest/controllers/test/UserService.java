package com.maybetm.mplrest.controllers.test;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.jpaTest.IDBTestController;
import com.maybetm.mplrest.entities.jpaTest.User;
import org.springframework.stereotype.Service;

/**
 * @author: ZebzeevSV
 * @version: 29.06.2019 18:57
 */
@Service
public class UserService extends AService<User, IDBTestController> {

	public UserService(IDBTestController repository) {
		super(repository);
	}
}
