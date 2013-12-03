package com.omnipasteapp.omnicommon.interfaces;

@SuppressWarnings("UnusedDeclaration")
public interface IClipboardProvider {
  public ILocalClipboard getLocalClipboard();

  public IOmniClipboard getOmniClipboard();

  public void start() throws InterruptedException;

  public void stop();

  public void addListener(ICanReceiveData dataReceiver);

  public void removeListener(ICanReceiveData dataReceiver);

  public void messageReceived(String fromRegistrationId, String toRegistrationId);
}
