package com.maybetm.mplrest.controllers.security.service;

import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.account.IDBAccount;
import com.maybetm.mplrest.entities.security.IDBToken;
import com.maybetm.mplrest.entities.security.Token;
import com.maybetm.mplrest.exceptions.security.auth_exception.AuthException;
import com.maybetm.mplrest.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.maybetm.mplrest.security.constants.SecurityConstants.TokenParams.creationTime;
import static com.maybetm.mplrest.security.constants.SecurityConstants.TokenParams.id;
import static com.maybetm.mplrest.security.constants.SecurityConstants.TokenParams.roleId;

/**
 * @author zebzeev-sv
 * @version 01.08.2019 19:20
 */
@Service
public class ServiceSecurity {

	private IDBAccount idbAccount;
	private IDBToken idbToken;

	@Autowired
	public ServiceSecurity(IDBAccount idbAccount, IDBToken idbToken) {
		this.idbAccount = idbAccount;
		this.idbToken = idbToken;
	}

	@Transactional(timeout = 30)
	public void destroyToken(String jwt) {
		final Optional<Token> token = JwtService.parse(jwt);
		if (token.isPresent()) {
			idbToken.deleteByTokenAndAccountId(token.get().getToken(), token.get().getAccount().getId());
		} else {
			throw new AuthException("Не удалось отозвать пользовательский токен.");
		}
	}

	public Token getAccessToken(Account identificationData) {
		Optional<Account> account = searchAccount.apply(identificationData);

		if (account.isPresent()) {
			return createAccessToken(account.get());
		} else {
			throw new AuthException("Ошибка идентификации. " +
					"Не удалось найти пользователя или не верные параметры идентификации.");
		}
	}

	private final Function<Account, Optional<Account>> searchAccount = (a) -> idbAccount
			.findByEmailOrLoginAndPasswordIgnoreCase(a.getEmail(), a.getLogin(), a.getPassword());

	private Token createAccessToken(Account account) {
		// функция для создания сущности токена с мета информацией
		// используется для сохранения и ответа идентифицированному пользователю
		final BiFunction<Account, String, Token> getTokenEntity = (a, token) ->
				new Token(new Account(a.getId()), token, LocalDateTime.now());

		// генирируем ключ доступа
		String jwt = JwtService.createJwt(getJwtParams.apply(account));
		// сохраняем ключ и привязываем его к конкретному пользователю
		idbToken.saveAndFlush(getTokenEntity.apply(account, jwt));

		return getTokenEntity.apply(account, jwt);
	}

	private final Function<Account, Map<String, Object>> getJwtParams = (account) -> {

		Map<String, Object> result = new HashMap<>(3);
		result.put(id, account.getId());
		result.put(roleId, account.getRole().getId());
		result.put(creationTime, DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));

		return result;
	};
}
