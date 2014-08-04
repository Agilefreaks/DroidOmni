package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class TelephonyEventDto {
  private String identifier;
  private TelephonyEventType type;
  private IncomingCallEventDto incomingCall;

  public enum TelephonyEventType {
    incomingCall, incomingSms, unknown
  }

  public TelephonyEventDto() {
  }

  public TelephonyEventDto(TelephonyEventType type, String phoneNumber) {
    this.type = type;
    this.incomingCall = new IncomingCallEventDto(phoneNumber);
  }

  public TelephonyEventDto(String identifier, TelephonyEventType type, String phoneNumber) {
    this(type, phoneNumber);
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public TelephonyEventType getType() {
    return type;
  }

  public void setType(TelephonyEventType type) {
    this.type = type;
  }

  public IncomingCallEventDto getIncomingCall() {
    return incomingCall;
  }

  public void setIncomingCall(IncomingCallEventDto incomingCall) {
    this.incomingCall = incomingCall;
  }
}
