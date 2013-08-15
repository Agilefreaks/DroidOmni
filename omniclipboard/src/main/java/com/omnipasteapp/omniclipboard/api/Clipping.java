package com.omnipasteapp.omniclipboard.api;

public class Clipping {
  private String Token;
  private String Content;

  public Clipping() {
  }

  public Clipping(String token, String content) {
    Token = token;
    Content = content;
  }

  public String getToken() {
    return Token;
  }

  public void setToken(String token) {
    Token = token;
  }

  public String getContent() {
    return Content;
  }

  public void setContent(String content) {
    Content = content;
  }
}
