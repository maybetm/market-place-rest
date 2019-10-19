package com.maybetm.configuration;

/**
 * @author zebzeev-sv
 * @version 14.10.2019 19:43
 */

public abstract class TestHelper {

  public static void configProxy() {
    configProxy("192.168.54.10", "3128");
  }

  private static void configProxy(String host, String port) {
    System.setProperty("http.proxyHost", host);
    System.setProperty("http.proxyPort", port);
    System.setProperty("https.proxyHost", host);
    System.setProperty("https.proxyPort", port);
  }
}
