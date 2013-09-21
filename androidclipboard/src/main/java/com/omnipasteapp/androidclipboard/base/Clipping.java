package com.omnipasteapp.androidclipboard.base;

import com.omnipasteapp.omnicommon.interfaces.IClipping;

public class Clipping implements IClipping {
  private String content;

  public Clipping(String content) {
    this.content = content;
  }

  @Override
  public String getToken() {
    return null;
  }

  @Override
  public String getContent() {
    return content;
  }

  @Override
  public String getType() {
    return null;
  }
}
