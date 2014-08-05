package com.omnipaste.omnicommon.dto;

@SuppressWarnings("UnusedDeclaration")
public class TelephonyEventDto {
  private String identifier;
  private TelephonyEventType type;
  private IncomingCallEventDto incomingCall;
  private IncomingSmsEventDto incomingSms;

  public enum TelephonyEventType {
    incomingCall, incomingSms, unknown
  }

  public TelephonyEventDto() {
  }

  public TelephonyEventDto(TelephonyEventType type, IncomingCallEventDto incomingCallEventDto) {
    this.type = type;
    this.incomingCall = incomingCallEventDto;
  }

  public TelephonyEventDto(TelephonyEventType type, IncomingSmsEventDto incomingSmsEventDto) {
    this.type = type;
    this.incomingSms = incomingSmsEventDto;
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

  public IncomingSmsEventDto getIncomingSms() {
    return incomingSms;
  }

  public void setIncomingSms(IncomingSmsEventDto incomingSms) {
    this.incomingSms = incomingSms;
  }
}
