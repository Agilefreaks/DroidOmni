package com.omnipasteapp.omnicommon;

import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.models.Clipping;

public class ClipboardData implements IClipboardData {
  private Clipping clipping;
  private Object sender;

  public ClipboardData(Object sender, Clipping clipping) {
    this.sender = sender;
    this.clipping = clipping;
  }

  @Override
  public Object getSender() {
    return sender;
  }

  @Override
  public String getData() {
    return clipping.getContent();
  }

  @Override
  public Clipping getClipping() {
    return clipping;
  }
}
