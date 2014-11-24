package com.omnipaste.omnicommon.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class EventDto implements Parcelable {
  private String contactName;
  private String content;
  private String phoneNumber;
  private EventType type;

  public enum EventType {
    INCOMING_CALL_EVENT,
    INCOMING_SMS_EVENT
  }

  public EventDto(Parcel in) {
    contactName = in.readString();
    content = in.readString();
    phoneNumber = in.readString();
    type = EventType.valueOf(in.readString());
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeString(contactName);
    parcel.writeString(content);
    parcel.writeString(phoneNumber);
    parcel.writeString(type.toString());
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public EventType getType() {
    return type;
  }

  public void setType(EventType type) {
    this.type = type;
  }
}
