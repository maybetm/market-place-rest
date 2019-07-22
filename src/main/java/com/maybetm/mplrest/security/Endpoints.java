package com.maybetm.mplrest.security;

import com.maybetm.mplrest.entities.roles.Role;

import java.util.Set;

/**
 * Перечисление конечных точек апи и тип роли,
 * которой он доступен.
 *
 * @author zebzeev-sv
 * @version 22.07.2019 19:01
 */
public enum Endpoints
{
  getBasket("", null);

  private String endpoint;

  private Set<Role> roles;

  Endpoints(String endpoint, Set<Role> roles)
  {
    this.endpoint = endpoint;
    this.roles = roles;
  }

  public String getEndpoint()
  {
    return endpoint;
  }

  public Set<Role> getRoles()
  {
    return roles;
  }

  public void setRoles(Set<Role> roles)
  {
    this.roles = roles;
  }
}
