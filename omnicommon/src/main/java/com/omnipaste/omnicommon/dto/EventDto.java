package com.omnipaste.omnicommon.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class EventDto implements Parcelable {
  private String contactName;
  private String content;
  private String phoneNumber;
  private EventType type;
  private Calendar createdAt;

  public enum EventType {
    INCOMING_CALL_EVENT,
    INCOMING_SMS_EVENT,
    UNKNOWN
  }

  public static final Parcelable.Creator<EventDto> CREATOR = new Parcelable.Creator<EventDto>() {
    public EventDto createFromParcel(Parcel pc) {
      return new EventDto(pc);
    }

    public EventDto[] newArray(int size) {
      return new EventDto[size];
    }
  };

  public EventDto() {
    this.createdAt = Calendar.getInstance();
  }

  public EventDto(Parcel in) {
    this();

    this.contactName = in.readString();
    this.content = in.readString();
    this.phoneNumber = in.readString();
    this.type = EventType.valueOf(in.readString());
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

  public Calendar getCreatedAt() {
    return createdAt;
  }
}
