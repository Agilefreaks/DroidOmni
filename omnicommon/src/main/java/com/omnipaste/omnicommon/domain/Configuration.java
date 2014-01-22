package com.omnipaste.omnicommon.domain;

public final class Configuration {
  private String channel, gcmSenderId, apiUrl;

  public Configuration() {
  }

  public Boolean hasChannel() {
    return channel != null && !channel.isEmpty();
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String value) {
    channel = value;
  }

  public String getGcmSenderId() {
    return gcmSenderId;
  }

  public void setGcmSenderId(String value) { gcmSenderId = value; }

  public String getApiUrl() {
    return apiUrl;
  }

  public void setApiUrl(String value) { apiUrl = value; }
}
