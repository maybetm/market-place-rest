package com.maybetm.mplrest.controllers.payments.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maybetm.mplrest.controllers.payments.service.PaymentsService;
import com.maybetm.mplrest.entities.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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

  @PostMapping(value = "payProducts")
  public void payProducts(@RequestBody Set<Product> products) throws JsonProcessingException
  {
    paymentsService.payProducts(products);
  }
}
