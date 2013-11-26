package com.omnipasteapp.omnicommon.settings;

public class CommunicationSettings {
  public static String ChannelKey = "channel";
  public static String RegistrationIdKey = "registration_id";

  private String _channel;
  private String _registrationId;

  public CommunicationSettings(String channel, String registrationId) {
    _channel = channel;
    _registrationId = registrationId;
  }

  public String getChannel() {
    return _channel;
  }

  public void setChannel(String channel) {
    _channel = channel;
  }

  public String getRegistrationId() {
    return _registrationId;
  }

  public void setRegistrationId(String registrationId) {
    _registrationId = registrationId;
  }

  public boolean hasChannel() {
    return _channel != null && !_channel.isEmpty();
  }

  public boolean hasRegistrationId() {
    return _registrationId != null && !_registrationId.isEmpty();
  }
}
