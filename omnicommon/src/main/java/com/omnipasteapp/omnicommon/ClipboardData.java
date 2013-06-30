package com.omnipasteapp.omnicommon;

import com.omnipasteapp.omnicommon.interfaces.IClipboardData;

public class ClipboardData implements IClipboardData {
  private String data;
  private Object sender;

  public ClipboardData(Object sender, String data) {
    this.sender = sender;
    this.data = data;
  }

  @Override
  public Object getSender() {
    return sender;
  }

  @Override
  public String getData() {
    return data;
  }
}
