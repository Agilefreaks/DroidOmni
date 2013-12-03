package com.omnipasteapp.clipboardprovider;

import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IClipboardProvider;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.ILocalClipboard;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.models.Sender;

import java.util.ArrayList;

import javax.inject.Inject;

public class ClipboardProvider implements IClipboardProvider, ICanReceiveData {
  private ILocalClipboard _localClipboard;
  private IOmniClipboard _omniClipboard;
  private String _lastData;
  private Thread _omniClipboardInitialize;
  private Thread _localClipboardInitialize;
  private ArrayList<ICanReceiveData> _dataReceivers;

  @Inject
  public IConfigurationService configurationService;

  public ClipboardProvider() {
    _dataReceivers = new ArrayList<>();
  }

  @Inject
  public ClipboardProvider(ILocalClipboard localClipboard, IOmniClipboard omniClipboard) {
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

    _localClipboard.removeDataReceiver(this);
    _localClipboard.dispose();
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
  public void dataReceived(Clipping clipping) {
    if (shouldPutData(clipping.getContent())) {
      _lastData = clipping.getContent();

      putData(clipping);

      // notify listeners
      for (ICanReceiveData receiver : _dataReceivers) {
        receiver.dataReceived(clipping);
      }
    }
  }

  @Override
  public void handle(String fromRegistrationId, String toRegistrationId) {
    if (fromRegistrationId != null && !fromRegistrationId.equals(toRegistrationId)) {
      _omniClipboard.fetchClipping();
    }
  }

  private void putData(Clipping clipping) {
    String content = clipping.getContent();

    if (clipping.getSender() == Sender.Local) {
      _omniClipboard.putData(content);
    } else if (clipping.getSender() == Sender.Omni) {
      _localClipboard.putData(content);
    }
  }

  private Boolean shouldPutData(String currentData) {
    return !currentData.isEmpty() && (_lastData == null || !_lastData.equals(currentData));
  }
}
