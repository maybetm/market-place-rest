package com.maybetm.mplrest.security;

/**
 * @author zebzeev-sv
 * @version 25.07.2019 10:44
 */
public enum Roles
{
  market(2L, "Магазин", "Магазин"),
  admin(3L, "Администратор", "администратор"),
  client(4L, "Клиент", "Клиент");

  private Long id;
  private String name;
  private String info;

  Roles(Long id, String name, String info)
  {
    this.id = id;
    this.name = name;
    this.info = info;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getInfo()
  {
    return info;
  }

  public void setInfo(String info)
  {
    this.info = info;
  }
}
