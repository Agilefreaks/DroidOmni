package com.omnipaste.omnicommon.dto;

import java.util.Date;

public class ClippingDto {
  private String content;
  private Date create_at;
  private ClippingType type;
  private String identifier;
  private ClippingProvider clippingProvider;

  public enum ClippingType {
    phoneNumber, uri, unknown
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

  public void setType(ClippingType type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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