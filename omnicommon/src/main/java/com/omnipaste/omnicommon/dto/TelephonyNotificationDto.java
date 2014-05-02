package com.omnipaste.omnicommon.dto;

public class TelephonyNotificationDto {
  private String identifier;
  private TelephonyNotificationType type;
  private IncomingCallNotificationDto incomingCall;

  public enum TelephonyNotificationType {
    incomingCall, incomingSms, unknown
  }

  public TelephonyNotificationDto() {
  }

  public TelephonyNotificationDto(TelephonyNotificationType type, String phoneNumber) {
    this.type = type;
    this.incomingCall = new IncomingCallNotificationDto(phoneNumber);
  }

  public TelephonyNotificationDto(String identifier, TelephonyNotificationType type, String phoneNumber) {
    this(type, phoneNumber);
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public TelephonyNotificationType getType() {
    return type;
  }

  public void setType(TelephonyNotificationType type) {
    this.type = type;
  }

  public IncomingCallNotificationDto getIncomingCall() {
    return incomingCall;
  }

  public void setIncomingCall(IncomingCallNotificationDto incomingCall) {
    this.incomingCall = incomingCall;
  }
}
