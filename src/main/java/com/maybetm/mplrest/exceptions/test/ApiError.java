package com.maybetm.mplrest.exceptions.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.http.HttpStatus;

/**
 * @author zebzeev-sv
 * @version 07.08.2019 19:44
 */
@JsonInclude(Include.NON_NULL)
public class ApiError
{
  private HttpStatus status;
  private int code;
  private String message;

  public ApiError(HttpStatus status, String message)
  {
    this.status = status;
    this.code = status.value();
    this.message = message;
  }

  public int getCode()
  {
    return code;
  }

  public void setCode(int code)
  {
    this.code = code;
  }

  public HttpStatus getStatus()
  {
    return status;
  }

  public void setStatus(HttpStatus status)
  {
    this.status = status;
  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

}
