package com.maybetm.mplrest.security.constants;


import com.maybetm.mplrest.security.annotations.RolesMapper;

/**
 * Это перечисление должно полностью дублировать роли из бд.
 * Используется для более удобного использованя аннотации {@link RolesMapper}.
 *
 * @author zebzeev-sv
 * @version 25.07.2019 10:44
 */
public enum Roles
{
  market(1L, "Магазин", "Магазин"),
  admin(2L, "Администратор", "администратор"),
  client(3L, "Клиент", "Клиент");

  public final Long id;
  public final String name;
  public final String info;

  Roles(Long id, String name, String info)
  {
    this.id = id;
    this.name = name;
    this.info = info;
  }

  public static boolean checkById(Long id)
  {
    for (Roles role : Roles.values()) {
      if (role.id.equals(id)) {
        return true;
      }
    }
    return false;
  }

  public static boolean checkByRolesMapper(RolesMapper rolesMapper, Long roleId)
  {
    for (Roles role : rolesMapper.roles()) {
      if (role.id.equals(roleId)) {
        return true;
      }
    }
    return false;
  }

}
