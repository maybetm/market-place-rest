package com.maybetm.mplrest.security.jwt;

/**
 * @author zebzeev-sv
 * @version 02.08.2019 18:15
 */
public class SecurityConstants
{
  // время жизни ключа доступа
  public final static long tokenLiveTime = 108_000;

  // дополнительный ключ безопасности. Можно использовать вместо хардкода какой-нибудь сертификат.
  public final static String secretToken = "SDXS66W2K8Y5Q62Y6L2570HLWQAZOC8D89E5YYUKG9O6DOCA8";

}
