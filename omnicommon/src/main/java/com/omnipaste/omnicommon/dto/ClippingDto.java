package com.omnipaste.omnicommon.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ClippingDto implements Parcelable {
  private String content;
  private Date create_at;
  private ClippingType type = ClippingType.UNKNOWN;
  private String identifier;
  private ClippingProvider clippingProvider;

  public enum ClippingType {
    PHONE_NUMBER,
    URL,
    ADDRESS,
    UNKNOWN
  }

  public enum ClippingProvider {
    LOCAL,
    CLOUD
  }

  public static final Parcelable.Creator<ClippingDto> CREATOR = new Parcelable.Creator<ClippingDto>() {
    public ClippingDto createFromParcel(Parcel pc) {
      return new ClippingDto(pc);
    }

    public ClippingDto[] newArray(int size) {
      return new ClippingDto[size];
    }
  };

  public ClippingDto() {
  }

  public ClippingDto(ClippingDto clippingDto) {
    content = clippingDto.getContent();
    create_at = clippingDto.getCreateAt();
    type = clippingDto.getType();
    identifier = clippingDto.getIdentifier();
    clippingProvider = clippingDto.getClippingProvider();
  }

  public ClippingDto(Parcel in) {
    content = in.readString();
    type = ClippingType.valueOf(in.readString());
    identifier = in.readString();
    clippingProvider = ClippingProvider.valueOf(in.readString());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(content);
    parcel.writeString(type.toString());
    parcel.writeString(identifier);
    parcel.writeString(clippingProvider.toString());
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public ClippingType getType() {
    return type;
  }

  public ClippingDto setType(ClippingType type) {
    this.type = type;

    return this;
  }

  public String getContent() {
    return content;
  }

  public ClippingDto setContent(String content) {
    this.content = content;

    return this;
  }

  public Date getCreateAt() {
    return create_at;
  }

  public void setCreateAt(Date createAt) {
    this.create_at = createAt;
  }

  public ClippingProvider getClippingProvider() {
    return clippingProvider;
  }

  public ClippingDto setClippingProvider(ClippingProvider clippingProvider) {
    this.clippingProvider = clippingProvider;

    return this;
  }
}