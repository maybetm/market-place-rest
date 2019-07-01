package com.maybetm.mplrest.controllers.test;

import com.maybetm.mplrest.entities.jpaTest.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
	
  private final UserService userService;

	@Autowired
	public TestController(UserService userService) {
    this.userService = userService;
  }

	@GetMapping(value = "getDetail")
	public Optional<User> getDetail(@RequestParam Long id) {
		return userService.findById(id);
	}

	@GetMapping(value = "getAll")
	public Iterable<User> getAll(@PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
		return userService.getEntityPage(pageable);
	}

}
