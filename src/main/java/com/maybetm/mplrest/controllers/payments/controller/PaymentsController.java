package com.maybetm.mplrest.controllers.payments.controller;

import com.maybetm.mplrest.controllers.payments.service.PaymentsService;
import com.maybetm.mplrest.entities.account.Account;
import com.maybetm.mplrest.entities.product.Product;
import com.maybetm.mplrest.security.annotations.RolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static com.maybetm.mplrest.security.constants.Roles.client;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 14:15
 */
@RestController
@RequestMapping(value = "payments")
public class PaymentsController implements IPaymentsController
{

  private PaymentsService paymentsService;

  @Autowired
  public PaymentsController(PaymentsService paymentsService)
  {
    this.paymentsService = paymentsService;
  }

  @PostMapping(value = "createPayment")
  @RolesMapper (roles = {client})
  public void createPayment(@RequestBody Set<Product> products, @RequestBody Account account)
  {
    paymentsService.createPayment(products, account);
  }
}
