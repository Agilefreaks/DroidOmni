package com.omnipasteapp.omnicommon.services;

import com.omnipasteapp.omnicommon.interfaces.*;

public class OmniService implements IOmniService, ICanReceiveData {
  private ILocalClipboard localClipboard;
  private IOmniClipboard omniClipboard;
  private String lastData;

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
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        Thread omniInitialize = omniClipboard.initialize();
        Thread localInitialize = localClipboard.initialize();

        omniInitialize.start();
        localInitialize.start();

        try {
          omniInitialize.join();
          localInitialize.join();
        } catch (InterruptedException e) {
          Thread t = Thread.currentThread();
          t.getUncaughtExceptionHandler().uncaughtException(t, e);
        } finally {
          omniClipboard.addDataReceiver(OmniService.this);
          localClipboard.addDataReceiver(OmniService.this);
        }
      }
    });

    thread.start();
    thread.join();
  }

  @Override
  public void stop() {
    omniClipboard.removeDataReceive(this);
    localClipboard.removeDataReceive(this);
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

  private Boolean shouldPutData(String data) {
    return !data.isEmpty() && (lastData == null || !lastData.equals(data));
  }
}
