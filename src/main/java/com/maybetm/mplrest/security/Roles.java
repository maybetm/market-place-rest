package com.maybetm.mplrest.security;


import com.maybetm.mplrest.security.annotations.RolesMapper;

/**
 * Это перечисление должно полностью дублировать роли из бд.
 * Используется для более удобного использованя аннотации {@link RolesMapper}.
 *
 * fixme Можно сделать валидацию id ролей перед запуском,
 * fixme если они не совпадают, то можно крашить приложение
 *
 * @author zebzeev-sv
 * @version 25.07.2019 10:44
 */
public enum Roles
{
  market(2L, "Магазин", "Магазин"),
  admin(3L, "Администратор", "администратор"),
  client(4L, "Клиент", "Клиент");

  public final Long id;
  public final String name;
  public final String info;

  Roles(Long id, String name, String info)
  {
    this.id = id;
    this.name = name;
    this.info = info;
  }
}
