package com.maybetm.mplrest.entities.account;

import com.maybetm.mplrest.commons.repositories.ICommonRepository;

import java.util.Optional;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 14:14
 */
public interface IDBAccount extends ICommonRepository<Account>
{

  // поиск аккаунта по полям email/login и password
  Optional<Account> findByEmailOrLoginAndPasswordIgnoreCase(String email, String login, String password);
}
