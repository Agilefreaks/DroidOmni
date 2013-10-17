package com.omnipasteapp.omnicommon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Clipping implements Parcelable {
  private Sender sender;
  private String token;
  private String content;
  private ClippingType type = ClippingType.Unknown;

  public Clipping(Parcel in) {
    this.sender = (Sender) in.readSerializable();
    this.token = in.readString();
    this.content = in.readString();
    this.type = (ClippingType) in.readSerializable();
  }

  public Clipping(String token, String content) {
    this.token = token;
    this.content = content;
  }

  @SuppressWarnings("UnusedDeclaration, used in ClippingBuilder")
  public Clipping(String token, String content, ClippingType type) {
    this(token, content);

    this.type = type;
  }

  public Clipping(String token, String content, Sender sender) {
    this(token, content);

    this.sender = sender;
  }

  public Clipping(String token, String content, ClippingType type, Sender sender) {
    this(token, content, type);

    this.sender = sender;
  }

  public String getToken() {
    return token;
  }

  public String getContent() {
    return content;
  }

  public ClippingType getType() {
    return type;
  }

  public Sender getSender() {
    return sender;
  }

  public void setSender(Sender sender) {
    this.sender = sender;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeSerializable(sender);
    parcel.writeString(token);
    parcel.writeString(content);
    parcel.writeSerializable(type);
  }
}