package com.omnipasteapp.omnicommon.models;

public class Clipping {
  private String token;
  private String content;
  private ClippingType type;

  public Clipping(String token, String content) {
    this.token = token;
    this.content = content;
  }

  @SuppressWarnings("UnusedDeclaration, used in ClippingBuilder")
  public Clipping(String token, String content, ClippingType type) {
    this(token, content);

    this.type = type;
  }

  public String getToken() {
    return token;
  }

  public String getContent() {
    return content;
  }

  public ClippingType getType() {
    return type;
  }
}
