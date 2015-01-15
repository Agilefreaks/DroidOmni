package com.omnipaste.omnicommon.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class SmsMessageDto implements Parcelable {
  private String phoneNumber;
  private String contactName;
  private String content;
  private List<String> phoneNumberList;
  private List<String> contactNameList;
  private List<String> contentList;
  private String deviceId;
  private Type type;
  private State state;
  private Date createdAt;

  public enum State {
    SENDING, SENT, RECEIVED, UNKNOWN
  }

  public enum Type {
    INCOMING, OUTGOING
  }

  public static final Parcelable.Creator<PhoneCallDto> CREATOR = new Parcelable.Creator<PhoneCallDto>() {
    public PhoneCallDto createFromParcel(Parcel pc) {
      return new PhoneCallDto(pc);
    }

    public PhoneCallDto[] newArray(int size) {
      return new PhoneCallDto[size];
    }
  };

  public SmsMessageDto() {
  }

  public SmsMessageDto(String deviceId) {
    this.deviceId = deviceId;
    this.type = Type.INCOMING;
    this.state = State.RECEIVED;
  }

  public SmsMessageDto(Parcel in) {
    setPhoneNumber(in.readString());
    setContactName(in.readString());
    setContent(in.readString());
    in.readStringList(phoneNumberList);
    in.readStringList(contactNameList);
    in.readStringList(contentList);
    setState(State.valueOf(in.readString()));
    setCreateAt((Date) in.readSerializable());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeString(phoneNumber);
    parcel.writeString(contactName);
    parcel.writeString(content);
    parcel.writeStringList(phoneNumberList);
    parcel.writeStringList(contactNameList);
    parcel.writeStringList(contentList);
    parcel.writeString(state.toString());
    parcel.writeSerializable(createdAt);
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public SmsMessageDto setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public SmsMessageDto setContactName(String contactName) {
    this.contactName = contactName;
    return this;
  }

  public String getContent() {
    return content;
  }

  public SmsMessageDto setContent(String content) {
    this.content = content;
    return this;
  }

  public List<String> getPhoneNumberList() {
    return phoneNumberList;
  }

  public SmsMessageDto setPhoneNumberList(List<String> phoneNumberList) {
    this.phoneNumberList = phoneNumberList;
    return this;
  }

  public void setContactNameList(List<String> contactNameList) {
    this.contactNameList = contactNameList;
  }

  public List<String> getContentList() {
    return contentList;
  }

  public SmsMessageDto setContentList(List<String> contentList) {
    this.contentList = contentList;
    return this;
  }

  public void setCreateAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Calendar getCreatedAt() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(createdAt);
    return calendar;
  }

  public String getContactName() {
    return contactName;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }
}
