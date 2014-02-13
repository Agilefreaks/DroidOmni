package com.omnipaste.droidomni.events;

public class LoginEvent {
  private String channel;

  public LoginEvent(String channel) {
    this.channel = channel;
  }

  public String getChannel() {
    return channel;
  }
}
