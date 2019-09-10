package com.maybetm.mplrest.controllers.account.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 9:47
 */
public interface IAccountController<E>
{

  /**
   * Создание учетной записи пользователя
   *
   * @param account - модель учетной записи
   * @return возвращает учетную запись пользователя
   */
  ResponseEntity<E> createAccount(@RequestBody E account);
  /**
   * Удаление учетной записи из системы
   *
   * @param id - уникальный идентификатор учетной записи
   */
  void deleteAccount(@RequestParam Long id);

  /**
   * Обновление учётной записи в системе
   *
   * @return возвращает учетную запись с обновленными данными
   */
  ResponseEntity<E> updateAccount(@RequestParam ("id") E fromDB, @RequestBody E toEdit);

  /**
   * Метод для получения постраничного получения учетных записей
   *
   * @param pageable - объект для пагинации списка
   * @return возвращает список учетных записей в записимости от свойств объекта pageable
   */
  List<E> getAccounts(@PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable);
}
