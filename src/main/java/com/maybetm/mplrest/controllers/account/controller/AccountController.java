package com.maybetm.mplrest.controllers.account.controller;

import com.maybetm.mplrest.controllers.account.service.AccountService;
import com.maybetm.mplrest.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для управления
 * учетными записями пользователей
 *
 * @author zebzeev-sv
 * @version 12.07.2019 9:47
 */
@RestController
@RequestMapping (value = "account")
public class AccountController implements IAccountController<User>
{

  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService)
  {
    this.accountService = accountService;
  }

  @Override
  @PostMapping(name = "createAccount")
  public ResponseEntity<User> createAccount(@RequestBody User user)
  {
    return ResponseEntity.of(accountService.save(user));
  }

  @Override
  @DeleteMapping(name = "deleteAccount")
  public void deleteAccount(@RequestParam Long id)
  {
    accountService.deleteById(id);
  }

  @Override
  @PatchMapping(name = "updateAccount")
  public ResponseEntity<User> updateAccount(@RequestParam ("id") User fromDB, @RequestBody User toEdit)
  {
    return ResponseEntity.of(accountService.updateEntity(fromDB, toEdit));
  }
}
