package com.maybetm.mplrest.controllers.account.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.user.IDBUser;
import com.maybetm.mplrest.entities.user.User;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 9:48
 */
public class AccountService extends AService<User, IDBUser>
{

  public AccountService(IDBUser repository)
  {
    super(repository);
  }
}
