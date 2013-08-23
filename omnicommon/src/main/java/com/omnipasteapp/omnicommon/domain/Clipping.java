package com.omnipasteapp.omnicommon.domain;

import java.util.Date;

public class Clipping {
  private Date dateCreated;
  private String content;

  public Clipping() {
  }

  public Clipping(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }
}
