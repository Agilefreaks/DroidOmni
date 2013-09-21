package com.omnipasteapp.omniclipboard.api.models;

import com.omnipasteapp.omnicommon.interfaces.IClipping;

public class Clipping implements IClipping {
  private String token;
  private String content;
  private String type;

  public Clipping(String token, String content) {
    this.token = token;
    this.content = content;
  }

  @SuppressWarnings("UnusedDeclaration, used in ClippingBuilder")
  public Clipping(String token, String content, String type) {
    this(token, content);

    this.type = type;
  }

  public String getToken() {
    return token;
  }

  public String getContent() {
    return content;
  }

  public String getType() {
    return type;
  }
}
