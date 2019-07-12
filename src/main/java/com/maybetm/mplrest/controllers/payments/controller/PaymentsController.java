package com.maybetm.mplrest.controllers.payments.controller;

import com.maybetm.mplrest.controllers.payments.service.PaymentsService;
import com.maybetm.mplrest.entities.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 10:59
 */
@RestController
@RequestMapping(name = "payments")
public class PaymentsController implements IPaymentsController
{

  private PaymentsService paymentsService;

  @Autowired
  public PaymentsController(PaymentsService paymentsService)
  {
    this.paymentsService = paymentsService;
  }

  @Override
  @PostMapping(name = "payProducts")
  public void payProducts(@RequestParam Set<Product> products)
  {
    paymentsService.payProducts(products);
  }
}
