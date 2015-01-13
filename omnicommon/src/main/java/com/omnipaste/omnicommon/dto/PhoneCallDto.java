package com.omnipaste.omnicommon.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class PhoneCallDto implements Parcelable {
  private String deviceId;
  private String number;
  private String contactName;
  private State state;
  private Date createdAt;

  public enum State {
    INITIATE, END_CALL, HOLD, UNKNOWN, INCOMING
  }

  public static final Parcelable.Creator<PhoneCallDto> CREATOR = new Parcelable.Creator<PhoneCallDto>() {
    public PhoneCallDto createFromParcel(Parcel pc) {
      return new PhoneCallDto(pc);
    }

    public PhoneCallDto[] newArray(int size) {
      return new PhoneCallDto[size];
    }
  };

  public PhoneCallDto(String deviceId, String number, String contactName, State state) {
    this.deviceId = deviceId;
    this.number = number;
    this.contactName = contactName;
    this.state = state;
  }

  public PhoneCallDto(String deviceId, String number, String contactName) {
    this(deviceId, number, contactName, State.INCOMING);
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

  public void setCreateAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Calendar getCreatedAt() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(createdAt);
    return calendar;
  }
}
