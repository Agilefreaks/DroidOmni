package com.omnipaste.omnicommon.domain;

public final class Configuration {
  public String channel;
  public String gcmSenderId;
  public String apiUrl;

  public Configuration() {
  }

  public Boolean hasChannel() {
    return channel != null && !channel.isEmpty();
  }
}
