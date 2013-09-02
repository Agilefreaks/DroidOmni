package com.omnipasteapp.omniclipboard.api.models;

public class Clipping {
  private String token;
  private String content;

  public Clipping(String token, String content) {
    this.token = token;
    this.content = content;
  }

  public String getToken() {
    return token;
  }

  public String getContent() {
    return content;
  }
}
