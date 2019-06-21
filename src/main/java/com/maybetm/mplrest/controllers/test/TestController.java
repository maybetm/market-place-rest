package com.maybetm.mplrest.controllers.test;

import com.maybetm.mplrest.entities.jpaTest.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author: ZebzeevSV
 * @version: 20.06.2019 19:39
 */
@RestController
@RequestMapping(value = "test")
public class TestController {
	private final IDBTestController idbTestController;

	@Autowired
	public TestController(IDBTestController idbProduct) {
		this.idbTestController = idbProduct;
	}

	// fixme не реализован
	@GetMapping(value = "getDetail")
	public Optional<User> getDetail(@RequestParam Long id) {
		return idbTestController.findById(id);
	}

	@GetMapping(value = "getAll")
	public Iterable<User> getAll() {
		return idbTestController.findAll();
	}

}
