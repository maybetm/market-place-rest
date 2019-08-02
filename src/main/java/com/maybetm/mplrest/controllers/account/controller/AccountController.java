package com.maybetm.mplrest.controllers.account.controller;

import com.maybetm.mplrest.controllers.account.service.AccountService;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.security.Roles;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
public class AccountController implements IAccountController<Account>
{

  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService)
  {
    this.accountService = accountService;
  }

  @Override
  @PostMapping(value = "createAccount")
  public ResponseEntity<Account> createAccount(@RequestBody(required = true) Account account)
  {
    return ResponseEntity.of(accountService.save(account));
  }

  @Override
  @DeleteMapping(value = "deleteAccount")
  @RolesMapper(roles = {Roles.admin, Roles.client, Roles.market})
  public void deleteAccount(@RequestParam Long id)
  {
    accountService.deleteById(id);
  }

  @Override
  @PatchMapping(value = "updateAccount")
  @RolesMapper(roles = {Roles.admin, Roles.client, Roles.market})
  public ResponseEntity<Account> updateAccount(@RequestParam ("id") Account fromDB, @RequestBody Account toEdit)
  {
    return ResponseEntity.of(accountService.updateEntity(fromDB, toEdit));
  }

  @Override
  @GetMapping(value = "getAccounts")
  public List<Account> getAccounts(@RequestParam (required = false, defaultValue = "") String search,
                                @PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
  {
    return accountService.getEntityOnPage(pageable).getContent();
  }
}
