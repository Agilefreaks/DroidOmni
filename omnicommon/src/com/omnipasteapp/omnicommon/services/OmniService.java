package com.omnipasteapp.omnicommon.services;

import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.interfaces.*;

public class OmniService implements IOmniService, ICanReceiveData {
  private ILocalClipboard _localClipboard;
  private IOmniClipboard _omniClipboard;
  private String _lastData;
  private Thread _omniClipboardInitialize;
  private Thread _localClipboardInitialize;

  @Inject
  private IConfigurationService configurationService;

  @Inject
  public OmniService(ILocalClipboard localClipboard, IOmniClipboard omniClipboard) {
    _localClipboard = localClipboard;
    _omniClipboard = omniClipboard;
  }

  @Override
  public ILocalClipboard getLocalClipboard() {
    return _localClipboard;
  }

  @Override
  public IOmniClipboard getOmniClipboard() {
    return _omniClipboard;
  }

  @Override
  public void start() throws InterruptedException {
    _omniClipboardInitialize = _omniClipboard.initialize();
    _localClipboardInitialize = _localClipboard.initialize();

    _omniClipboardInitialize.start();
    _localClipboardInitialize.start();

    _omniClipboardInitialize.join();
    _localClipboardInitialize.join();

    _omniClipboard.addDataReceiver(this);
    _localClipboard.addDataReceiver(this);
  }

  @Override
  public void stop() {
    if (_omniClipboardInitialize != null && _omniClipboardInitialize.isAlive()) {
      _omniClipboardInitialize.interrupt();
    }

    if (_localClipboardInitialize != null && _localClipboardInitialize.isAlive()) {
      _localClipboardInitialize.interrupt();
    }

    _omniClipboard.removeDataReceiver(this);
    _omniClipboard.dispose();

    _localClipboard.removeDataReceiver(this);
    _localClipboard.dispose();
  }

  @Override
  public boolean isConfigured() {
    return configurationService.getCommunicationSettings().hasChannel();
  }

  @Override
  public void dataReceived(IClipboardData clipboardData) {
    if (shouldPutData(clipboardData.getData())) {
      _lastData = putData(clipboardData);
    }
  }

  private String putData(IClipboardData clipboardData) {
    String data = clipboardData.getData();

    if (clipboardData.getSender() instanceof ILocalClipboard) {
      _omniClipboard.putData(data);
    } else if (clipboardData.getSender() instanceof IOmniClipboard) {
      _localClipboard.putData(data);
    }

    return data;
  }

  private Boolean shouldPutData(String currentData) {
    return !currentData.isEmpty() && (_lastData == null || !_lastData.equals(currentData));
  }
}
