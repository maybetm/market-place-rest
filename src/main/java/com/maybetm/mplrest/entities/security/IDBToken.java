package com.maybetm.mplrest.entities.security;

import com.maybetm.mplrest.commons.repositories.ICommonRepository;

import java.util.Optional;

/**
 * @author zebzeev-sv
 * @version 01.08.2019 19:18
 */
public interface IDBToken extends ICommonRepository<Token>
{
  // поиск конкретного токена по его хешу
  Optional<Token> findByTokenAndAccountId(String token, Long accountId);

  // удаление пользовательского токена из бд
  void deleteByTokenAndAccountId(String token, Long accountId);
}
