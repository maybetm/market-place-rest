package com.maybetm.mplrest.security.constants;

/**
 * @author zebzeev-sv
 * @version 02.08.2019 18:15
 */
public abstract class SecurityConstants
{

  // время жизни ключа доступа ~ 1 день.
  public final static long tokenLiveTime = 864_000_00;

  // дополнительный ключ безопасности. Можно использовать вместо хардкода какой-нибудь сертификат.
  public final static String secretToken = "SDXS66W2K8Y5Q62Y6L2570HLWQAZOC8D89E5YYUKG9O6DOCA8";

  // приватные ендпойнты будут смотреть на этот заголовок в запросе.
  public final static String headerAuth = "Authorization";

  // поля которые должен содержать JWT токен
  public final static class TokenParams
  {
    public static final String id = "id";
    public static final String roleId = "roleId";
    public static final String creationTime = "creationTime";
  }
}
