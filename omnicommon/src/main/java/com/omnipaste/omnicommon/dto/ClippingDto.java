package com.omnipaste.omnicommon.dto;

import java.util.Date;

public class ClippingDto {
  private String content;
  private Date create_at;
  private ClippingType type;
  private String identifier;

  public enum ClippingType {
    phoneNumber, uri, unknown
  }

  public ClippingDto() {
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
}