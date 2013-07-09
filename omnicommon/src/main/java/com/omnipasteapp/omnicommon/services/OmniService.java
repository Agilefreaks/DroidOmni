package com.omnipasteapp.omnicommon.services;

import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniService;

import java.util.ArrayList;

import javax.inject.Inject;

public class OmniService implements IOmniService, ICanReceiveData {
  private ILocalClipboard _localClipboard;
  private IOmniClipboard _omniClipboard;
  private String _lastData;
  private Thread _omniClipboardInitialize;
  private Thread _localClipboardInitialize;
  private ArrayList<ICanReceiveData> _dataReceivers;

  @Inject
  public IConfigurationService configurationService;

  public OmniService() {
    _dataReceivers = new ArrayList<ICanReceiveData>();
  }

  @Inject
  public OmniService(ILocalClipboard localClipboard, IOmniClipboard omniClipboard) {
    this();

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
  public synchronized void start() throws InterruptedException {
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
  public synchronized void stop() {
    if (_omniClipboardInitialize != null && _omniClipboardInitialize.isAlive()) {
      _omniClipboardInitialize.interrupt();
    }

    if (_localClipboardInitialize != null && _localClipboardInitialize.isAlive()) {
      _localClipboardInitialize.interrupt();
    }

    _omniClipboard.removeDataReceiver(this);
    _omniClipboard.dispose();
    _omniClipboard = null;

    _localClipboard.removeDataReceiver(this);
    _localClipboard.dispose();
    _localClipboard = null;
  }

  @Override
  public boolean isConfigured() {
    return configurationService.getCommunicationSettings().hasChannel();
  }

  @Override
  public void addListener(ICanReceiveData dataReceiver) {
    _dataReceivers.add(dataReceiver);
  }

  @Override
  public void removeListener(ICanReceiveData dataReceiver) {
    _dataReceivers.remove(dataReceiver);
  }

  @Override
  public void dataReceived(IClipboardData clipboardData) {
    if (shouldPutData(clipboardData.getData())) {
      _lastData = putData(clipboardData);

      // notify listeners
      for (ICanReceiveData receiver : _dataReceivers) {
        receiver.dataReceived(clipboardData);
      }
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
