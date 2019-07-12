package com.maybetm.mplrest.controllers.payments.service;

import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.payments.IDBPayment;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.entities.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 11:01
 */
@Service
public class PaymentsService extends AService<Payment, IDBPayment>
{

  public PaymentsService(IDBPayment repository)
  {
    super(repository);
  }

  public ResponseEntity<Set<Payment>> payProducts (Set<Product> products){

    // сперва надо чекнуть количество продуктов, не превышает ли допустимый лимит
    // вернём ошибку, если превысил

    // если количество продуктов не превышает лимит,
    // то вычетаем количество покупаемых продуктов из products

    // создаём запись в таблице payments с каждой позицией
    // product_id, count, cost, datetime

    return null;
  }
}
