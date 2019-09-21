package com.maybetm.mplrest.controllers.payments.controller;

import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.entities.account.Account;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 11:00
 */
public interface IPaymentsController
{
  // создание платежа с выбранными элементами в корзине корзине
  void createPayment(@RequestBody Set<Product> products, @RequestBody Account account);

}
