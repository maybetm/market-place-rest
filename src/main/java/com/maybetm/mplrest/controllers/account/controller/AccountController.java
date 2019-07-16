package com.maybetm.mplrest.controllers.account.controller;

import com.maybetm.mplrest.controllers.account.service.AccountService;
import com.maybetm.mplrest.entities.user.User;
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
public class AccountController implements IAccountController<User>
{

  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService)
  {
    this.accountService = accountService;
  }

  @Override
  @PostMapping(value = "createAccount")
  public ResponseEntity<User> createAccount(@RequestBody User user)
  {
    return ResponseEntity.of(accountService.save(user));
  }

  @Override
  @DeleteMapping(value = "deleteAccount")
  public void deleteAccount(@RequestParam Long id)
  {
    accountService.deleteById(id);
  }

  @Override
  @PatchMapping(value = "updateAccount")
  public ResponseEntity<User> updateAccount(@RequestParam ("id") User fromDB, @RequestBody User toEdit)
  {
    return ResponseEntity.of(accountService.updateEntity(fromDB, toEdit));
  }

  @Override
  @GetMapping(value = "getAccounts")
  public List<User> getAccounts(@RequestParam (required = false, defaultValue = "") String search,
                                          @PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
  {
    return accountService.getEntityOnPage(pageable).getContent();
  }
}
