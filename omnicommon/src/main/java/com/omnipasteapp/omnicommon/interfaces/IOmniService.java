package com.omnipasteapp.omnicommon.interfaces;

import com.omnipasteapp.omnicommon.domain.Clipping;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public interface IOmniService {
  public ILocalClipboard getLocalClipboard();

  public IOmniClipboard getOmniClipboard();

  public List<Clipping> getClippings();

  public void start() throws InterruptedException;

  public void stop();

  public boolean isConfigured();

  public void addListener(ICanReceiveData dataReceiver);

  public void removeListener(ICanReceiveData dataReceiver);
}
