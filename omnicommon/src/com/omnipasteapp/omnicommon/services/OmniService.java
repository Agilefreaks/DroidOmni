package com.omnipasteapp.omnicommon.services;

import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.*;

public class OmniService implements IOmniService, ICanReceiveData {
  private ILocalClipboard localClipboard;
  private IOmniClipboard omniClipboard;
  private String lastData;
  private Thread omniClipboardInitialize;
  private Thread localClipboardInitialize;

  @Inject
  public OmniService(ILocalClipboard localClipboard, IOmniClipboard omniClipboard) {
    this.localClipboard = localClipboard;
    this.omniClipboard = omniClipboard;
  }

  @Override
  public ILocalClipboard getLocalClipboard() {
    return localClipboard;
  }

  @Override
  public IOmniClipboard getOmniClipboard() {
    return omniClipboard;
  }

  @Override
  public void start() throws InterruptedException {
    omniClipboardInitialize = omniClipboard.initialize();
    localClipboardInitialize = localClipboard.initialize();

    omniClipboardInitialize.start();
    localClipboardInitialize.start();

    omniClipboardInitialize.join();
    localClipboardInitialize.join();

    omniClipboard.addDataReceiver(OmniService.this);
    localClipboard.addDataReceiver(OmniService.this);
  }

  @Override
  public void stop() {
    if (omniClipboardInitialize != null && omniClipboardInitialize.isAlive()) {
      omniClipboardInitialize.interrupt();
    }

    if (localClipboardInitialize != null && localClipboardInitialize.isAlive()) {
      localClipboardInitialize.interrupt();
    }

    omniClipboard.removeDataReceiver(this);
    omniClipboard.dispose();

    localClipboard.removeDataReceiver(this);
    localClipboard.dispose();
  }

  @Override
  public void dataReceived(IClipboardData clipboardData) {
    if (shouldPutData(clipboardData.getData())) {
      lastData = putData(clipboardData);
    }
  }

  private String putData(IClipboardData clipboardData) {
    String data = clipboardData.getData();

    if (clipboardData.getSender() instanceof ILocalClipboard) {
      omniClipboard.putData(data);
    } else if (clipboardData.getSender() instanceof IOmniClipboard) {
      localClipboard.putData(data);
    }

    return data;
  }

  private Boolean shouldPutData(String currentData) {
    return !currentData.isEmpty() && (lastData == null || !lastData.equals(currentData));
  }
}
