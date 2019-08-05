package com.maybetm.mplrest.entities.security;

import com.maybetm.mplrest.commons.repositories.ICommonRepository;

import java.util.Optional;

/**
 * @author zebzeev-sv
 * @version 01.08.2019 19:18
 */
public interface IDBToken extends ICommonRepository<Token>
{
  // поиск ранее созданного токена.
  Optional<Token> findByToken(String token);

}
