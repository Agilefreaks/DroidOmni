package com.omnipaste.omnicommon.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class PhoneCallDto implements Parcelable {
  private String deviceId;
  private String number;
  private String contactName;
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

  public PhoneCallDto(String deviceId, String number, String contactName, Type type, State state) {
    this.deviceId = deviceId;
    this.number = number;
    this.contactName = contactName;
    this.type = type;
    this.state = state;
  }

  public PhoneCallDto(String deviceId, String number, String contactName) {
    this(deviceId, number, contactName, Type.INCOMING, State.STARTING);
  }

  public PhoneCallDto(Parcel in) {
    setDeviceId(in.readString());
    setNumber(in.readString());
    setContactName(in.readString());
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
    parcel.writeString(state.toString());
    parcel.writeSerializable(createdAt);
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
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
