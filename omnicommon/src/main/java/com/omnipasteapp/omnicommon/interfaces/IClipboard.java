package com.omnipasteapp.omnicommon.interfaces;

public interface IClipboard {
  public void addDataReceiver(ICanReceiveData dataReceiver);

  public void removeDataReceiver(ICanReceiveData dataReceiver);

  public Thread initialize();

  public void dispose();

  public void putData(String data);
}
