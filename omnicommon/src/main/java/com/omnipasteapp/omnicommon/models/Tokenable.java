package com.omnipasteapp.omnicommon.models;

public abstract class Tokenable {
  protected String token;

  protected Tokenable() {
  }

  public Tokenable(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
