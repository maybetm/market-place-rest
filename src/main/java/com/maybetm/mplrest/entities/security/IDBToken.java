package com.maybetm.mplrest.entities.security;

import com.maybetm.mplrest.commons.repositories.ICommonRepository;

import java.util.Optional;
import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 01.08.2019 19:18
 */
public interface IDBToken extends ICommonRepository<Token>
{
  // поиск токенов по id пользователя
  Set<Token> findByAccountId(Long id);

  // поиск конкретного токена по его хешу
  Optional<Token> findByTokenAndAccountId(String token, Long accountId);
}
