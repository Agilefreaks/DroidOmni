package com.omnipasteapp.androidclipboard;

import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;

import java.util.ArrayList;

public class CompatibilityAndroidClipboard implements ILocalClipboard, Runnable, ClipboardManagerWrapper.OnClipChangedListener {

  @Inject
  private ClipboardManagerWrapper clipboardManager;

  private ArrayList<ICanReceiveData> dataReceivers;

  public CompatibilityAndroidClipboard() {
    dataReceivers = new ArrayList<ICanReceiveData>();
  }

  @Override
  public void addDataReceiver(ICanReceiveData dataReceiver) {
    dataReceivers.add(dataReceiver);
  }

  @Override
  public void removeDataReceiver(ICanReceiveData dataReceiver) {
    dataReceivers.remove(dataReceiver);
  }

  @Override
  public Thread initialize() {
    return new Thread(this);
  }

  @Override
  public void dispose() {
    dataReceivers.clear();
    clipboardManager.dispose();
  }

  @Override
  public void putData(String data) {
    clipboardManager.setClip(data);
  }

  @Override
  public void run() {
    clipboardManager.setOnClipChangedListener(this);
    clipboardManager.start();
  }

  @Override
  public void onPrimaryClipChanged() {
    if (!hasClipping()) {
      return;
    }

    String clip = clipboardManager.getCurrentClip();

    for (ICanReceiveData receiver : dataReceivers) {
      receiver.dataReceived(new ClipboardData(this, clip));
    }
  }

  private boolean hasClipping() {
    return clipboardManager.hasClipping();
  }
}