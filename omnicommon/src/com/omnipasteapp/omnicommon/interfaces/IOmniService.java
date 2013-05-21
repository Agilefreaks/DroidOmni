package com.omnipasteapp.omnicommon.interfaces;

public interface IOmniService {
  public ILocalClipboard getLocalClipboard();

  public IOmniClipboard getOmniClipboard();

  public void start() throws InterruptedException;

  public void stop();
}
