package com.omnipaste.omnicommon.dto;

import java.util.Date;

public class ClippingDto {
  private String content;
  private Date create_at;
  private ClippingType type = ClippingType.unknown;
  private String identifier;
  private ClippingProvider clippingProvider;

  public enum ClippingType {
    phoneNumber, webSite, address, unknown
  }

  public enum ClippingProvider {
    local, cloud
  }

  public ClippingDto() {
  }

  public ClippingDto(ClippingDto clippingDto) {
    content = clippingDto.getContent();
    create_at = clippingDto.getCreateAt();
    type = clippingDto.getType();
    identifier = clippingDto.getIdentifier();
    clippingProvider = clippingDto.getClippingProvider();
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