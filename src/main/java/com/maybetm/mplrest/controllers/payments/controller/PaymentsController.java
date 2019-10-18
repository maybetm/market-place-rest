package com.maybetm.mplrest.controllers.payments.controller;

import com.maybetm.mplrest.controllers.payments.service.PaymentsService;
import com.maybetm.mplrest.entities.basket.Basket;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.maybetm.mplrest.security.constants.Roles.admin;
import static com.maybetm.mplrest.security.constants.Roles.client;
import static com.maybetm.mplrest.security.constants.Roles.market;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 14:15
 */
@RestController
@RequestMapping(value = "payments")
public class PaymentsController
{

  private PaymentsService paymentsService;

  @Autowired
  public PaymentsController(PaymentsService paymentsService)
  {
    this.paymentsService = paymentsService;
  }

  /**
   * Создание платежа.
   * Метод создания платежа редактирует данные товара
   * и удаляет его из корзины пользователя.
   * Последним шагом метаинформация о платеже добавляется в таблицу payments.
   *
   * @param baskets - список позиций в карзине
   * @param clientId - id учётной записи пользователя
   */
  @PostMapping(value = "createPayment")
  @RolesMapper (roles = {client})
  public void createPayment(@RequestBody Set<Basket> baskets, @RequestParam("clientId") Long clientId)
  {
    paymentsService.createPayment(baskets, clientId);
  }

  /**
   * Получение статистики платежей клиента
   */
  @GetMapping(value = "getPaymentsStatistic")
  @RolesMapper(roles = {client, admin, market})
  public List<Payment> getPaymentStatistics (@RequestParam Long clientId,
                                             @PageableDefault (sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
    return paymentsService.getPaymentStatistics(clientId, pageable);
  }
}
