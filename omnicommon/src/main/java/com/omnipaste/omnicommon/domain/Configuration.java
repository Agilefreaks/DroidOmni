package com.omnipaste.omnicommon.domain;

public class Configuration {
  public String channel;

  public Configuration() {
  }

  public Configuration(String channel) {
    this.channel = channel;
  }

  public Boolean hasChannel() {
    return channel != null && !channel.isEmpty();
  }
}
