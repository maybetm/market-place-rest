package com.maybetm.mplrest.controllers.account.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.account.IDBAccount;
import com.maybetm.mplrest.entities.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 9:48
 */
@Service
public class AccountService extends AService<Account, IDBAccount>
{
  @Autowired
  public AccountService(IDBAccount repository)
  {
    super(repository);
  }
}
