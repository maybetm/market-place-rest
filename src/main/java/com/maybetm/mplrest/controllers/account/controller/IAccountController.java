package com.maybetm.mplrest.controllers.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 9:47
 */
public interface IAccountController <E>
{
  // создание учётной записи
  ResponseEntity<E> createAccount (@RequestBody E user);
  // удаление учетной записи
  void deleteAccount (@RequestParam Long id);
  // редактирование учетной записи
  ResponseEntity<E> updateAccount(@RequestParam ("id") E fromDB, @RequestBody E toEdit);
}
