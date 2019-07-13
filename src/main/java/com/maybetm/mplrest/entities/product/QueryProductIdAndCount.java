package com.maybetm.mplrest.entities.product;

/**
 * Запрос возвращает id и count из таблицы products
 * Используется для сверки количества продуктов на складе с количеством продуктов,
 * которые запросил пользователь для каждого продукта.
 *
 * @author zebzeev-sv
 * @version 12.07.2019 17:10
 */
public interface QueryProductIdAndCount
{

  Long getId();

  Long getCount();
}
