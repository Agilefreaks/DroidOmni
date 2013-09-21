package com.omnipasteapp.omnicommon;

import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.interfaces.IClipping;

public class ClipboardData implements IClipboardData {
  private IClipping clipping;
  private Object sender;

  public ClipboardData(Object sender, IClipping clipping) {
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
  public IClipping getClipping() {
    return clipping;
  }
}
