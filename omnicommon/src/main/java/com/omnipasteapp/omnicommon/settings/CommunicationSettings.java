package com.omnipasteapp.omnicommon.settings;

public class CommunicationSettings {
  public static String ChannelKey = "channel";

  private String channel;

  public CommunicationSettings(String channel) {
    this.channel = channel;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public boolean hasChannel() {
    return channel != null && !channel.isEmpty();
  }
}
