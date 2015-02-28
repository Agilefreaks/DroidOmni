package com.omnipaste.omnicommon.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
public class PhoneCallDto implements Parcelable {
  private String deviceId;
  private String number;
  private String contactName;
  private Long contactId;
  private Type type;
  private State state;
  private Date createdAt;

  public enum State {
    STARTED, STARTING, ENDED, ENDING, UNKNOWN
  }

  public enum Type {
    INCOMING, OUTGOING, UNKNOWN
  }

  public static final Parcelable.Creator<PhoneCallDto> CREATOR = new Parcelable.Creator<PhoneCallDto>() {
    public PhoneCallDto createFromParcel(Parcel pc) {
      return new PhoneCallDto(pc);
    }

    public PhoneCallDto[] newArray(int size) {
      return new PhoneCallDto[size];
    }
  };

  public PhoneCallDto() {
    type = Type.INCOMING;
    state = State.STARTING;
  }

  public PhoneCallDto(Parcel in) {
    setDeviceId(in.readString());
    setNumber(in.readString());
    setContactName(in.readString());
    setContactId(in.readLong());
    setState(State.valueOf(in.readString()));
    setCreateAt((Date) in.readSerializable());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeString(deviceId);
    parcel.writeString(number);
    parcel.writeString(contactName);
    parcel.writeLong(contactId);
    parcel.writeString(state.toString());
    parcel.writeSerializable(createdAt);
  }

  public String getDeviceId() {
    return deviceId;
  }

  public PhoneCallDto setDeviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public Long getContactId() {
    return contactId;
  }

  public PhoneCallDto setContactId(Long contactId) {
    this.contactId = contactId;
    return this;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public void setCreateAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Calendar getCreatedAt() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(createdAt);
    return calendar;
  }
}
