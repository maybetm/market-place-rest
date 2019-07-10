package com.maybetm.mplrest.controllers.test;

import com.maybetm.mplrest.entities.test.IDBUserAddress;
import com.maybetm.mplrest.entities.test.UsersAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zebzeev-sv
 * @version 10.07.2019 11:58
 */
@RestController
@RequestMapping (value = "test")
public class testController
{
  public IDBUserAddress idbUserAddress;

  @Autowired
  public testController(IDBUserAddress idbUserAddress)
  {
    this.idbUserAddress = idbUserAddress;
  }

  @GetMapping(value = "getDataTest")
  public List<UsersAddress> getDataTest () {
    return idbUserAddress.findAll();
  }
}
