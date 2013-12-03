package com.omnipasteapp.omnicommon.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Clipping extends Tokenable implements Parcelable {
  private Sender sender;
  private String content;
  private ClippingType type = ClippingType.Unknown;

  public static final Creator<Clipping> CREATOR = new Creator<Clipping>() {
    @Override
    public Clipping createFromParcel(Parcel parcel) {
      return new Clipping(parcel);
    }

    @Override
    public Clipping[] newArray(int size) {
      return new Clipping[size];
    }
  };

  public Clipping(Parcel in) {
    this.token = in.readString();
    this.content = in.readString();
    this.type = (ClippingType) in.readSerializable();
    this.sender = (Sender) in.readSerializable();
  }

  public Clipping(String token, String content) {
    super(token);

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
    parcel.writeString(token);
    parcel.writeString(content);
    parcel.writeSerializable(type);
    parcel.writeSerializable(sender);
  }
}
