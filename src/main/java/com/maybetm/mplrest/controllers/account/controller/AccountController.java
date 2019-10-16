package com.maybetm.mplrest.controllers.account.controller;

import com.maybetm.mplrest.controllers.account.service.AccountService;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import com.maybetm.mplrest.security.constants.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для управления
 * учетными записями пользователей
 *
 * @author zebzeev-sv
 * @version 12.07.2019 9:47
 */
@RestController
@RequestMapping (value = "account")
public class AccountController
{
  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService)
  {
    this.accountService = accountService;
  }

  /**
   * Создание учетной записи пользователя
   * @param account - модель учетной записи
   * @return возвращает учетную запись пользователя
   */
  @PostMapping(value = "createAccount")
  public ResponseEntity<Account> createAccount(@RequestBody Account account)
  {
    return ResponseEntity.of(accountService.save(account));
  }

  /**
   * Удаление учетной записи из системы
   * @param id - уникальный идентификатор учетной записи
   */
  @DeleteMapping(value = "deleteAccount")
  @RolesMapper(roles = {Roles.admin, Roles.client, Roles.market})
  public void deleteAccount(@RequestParam Long id)
  {
    accountService.deleteById(id);
  }

  /**
   * Обновление учётной записи в системе
   * @return возвращает учетную запись с обновленными данными
   */
  @PatchMapping(value = "updateAccount")
  @RolesMapper(roles = {Roles.admin, Roles.client, Roles.market})
  public ResponseEntity<Account> updateAccount(@RequestParam ("id") Account fromDB, @RequestBody Account toEdit)
  {
    return ResponseEntity.of(accountService.updateEntity(fromDB, toEdit));
  }

  /**
   * Метод для получения постраничного получения учетных записей
   * @param pageable - объект для пагинации списка
   * @return возвращает список учетных записей в записимости от свойств объекта pageable
   */
  @GetMapping(value = "getAccounts")
  @RolesMapper(roles = {Roles.admin})
  public List<Account> getAccounts(@PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
  {
    return accountService.getEntityOnPage(pageable).getContent();
  }
}
