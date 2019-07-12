package com.maybetm.mplrest.controllers.payments.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybetm.mplrest.commons.services.AService;
import com.maybetm.mplrest.entities.payments.IDBPayment;
import com.maybetm.mplrest.entities.payments.Payment;
import com.maybetm.mplrest.entities.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zebzeev-sv
 * @version 12.07.2019 11:01
 */
@Service
public class PaymentsService extends AService<Payment, IDBPayment>
{
  @Autowired
  public PaymentsService(IDBPayment repository)
  {
    super(repository);
  }

  @Transactional
  public ResponseEntity<Set<Payment>> payProducts (List<Product> products)
  {
    Map<Long, Long> productsMap = products.stream().collect(Collectors.toMap(Product::getId, Product::getCost));

    try {
      System.out.println("product :" + new ObjectMapper().writeValueAsString(productsMap));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    // сперва надо чекнуть количество продуктов, не превышает ли допустимый лимит
    // вернём ошибку, если превысил

    // если количество продуктов не превышает лимит,
    // то вычетаем количество покупаемых продуктов из products

    // создаём запись в таблице payments с каждой позицией
    // product_id, product_name, category, count, cost, datetime
    // можно сделать просто embedded коллекцию

    return new ResponseEntity<Set<Payment>>(HttpStatus.MULTI_STATUS);
  }
}
