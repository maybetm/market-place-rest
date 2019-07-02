package com.maybetm.mplrest.entities.user;

import com.maybetm.mplrest.commons.AEntity;

import java.time.LocalDateTime;

/**
 * @author zebzeev-sv
 * @version 02.07.2019 14:13
 */
public class User extends AEntity
{

  private String login;

  private String password;

  private LocalDateTime dateRegistration;
}
