package com.maybetm.mplrest.controllers.payments.controller;

import com.maybetm.mplrest.entities.product.Product;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 11:00
 */
public interface IPaymentsController
{
  // оплатить выбранные элементы в корзине
  void payProducts(@RequestParam Set<Product> products);

}
