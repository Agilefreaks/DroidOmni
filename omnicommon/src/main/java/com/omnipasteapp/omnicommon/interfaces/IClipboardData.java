package com.omnipasteapp.omnicommon.interfaces;

import com.omnipasteapp.omnicommon.models.Clipping;

public interface IClipboardData {
  public Object getSender();

  public String getData();

  public Clipping getClipping();
}
