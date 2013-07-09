package com.omnipasteapp.omnicommon.interfaces;

@SuppressWarnings("UnusedDeclaration")
public interface IOmniService {
  public ILocalClipboard getLocalClipboard();

  public IOmniClipboard getOmniClipboard();

  public void start() throws InterruptedException;

  public void stop();

  public boolean isConfigured();

  public void addListener(ICanReceiveData dataReceiver);

  public void removeListener(ICanReceiveData dataReceiver);
}
