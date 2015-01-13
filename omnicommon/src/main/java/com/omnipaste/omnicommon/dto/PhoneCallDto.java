package com.omnipaste.omnicommon.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class PhoneCallDto implements Parcelable {
  private String deviceId;
  private String phoneNumber;
  private String contactName;
  private State state;

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

  public PhoneCallDto(String deviceId, String phoneNumber, String contactName, State state) {
    this.deviceId = deviceId;
    this.phoneNumber = phoneNumber;
    this.contactName = contactName;
    this.state = state;
  }

  public PhoneCallDto(String deviceId, String phoneNumber, String contactName) {
    this(deviceId, phoneNumber, contactName, State.INCOMING);
  }

  public PhoneCallDto(Parcel in) {
    setDeviceId(in.readString());
    setPhoneNumber(in.readString());
    setContactName(in.readString());
    setState(State.valueOf(in.readString()));
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeString(deviceId);
    parcel.writeString(phoneNumber);
    parcel.writeString(contactName);
    parcel.writeString(state.toString());
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
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
}
