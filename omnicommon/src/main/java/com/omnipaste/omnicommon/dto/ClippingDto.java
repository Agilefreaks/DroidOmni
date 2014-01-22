package com.omnipaste.omnicommon.dto;

import java.util.Date;

public class ClippingDto {
  private String content;
  private Date create_at;

  public ClippingDto() {
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreate_at() {
    return create_at;
  }

  public void setCreate_at(Date create_at) {
    this.create_at = create_at;
  }
}