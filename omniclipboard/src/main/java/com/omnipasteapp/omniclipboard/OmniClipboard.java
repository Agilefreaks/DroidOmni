package com.omnipasteapp.omniclipboard;

import android.util.Log;

import com.omnipasteapp.omniclipboard.api.IGetClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.api.IOmniApi;
import com.omnipasteapp.omniclipboard.api.ISaveClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.messaging.IMessageHandler;
import com.omnipasteapp.omniclipboard.messaging.IMessagingService;
import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import java.util.ArrayList;

import javax.inject.Inject;

public class OmniClipboard implements IOmniClipboard, Runnable, ISaveClippingCompleteHandler, IGetClippingCompleteHandler, IMessageHandler {

  private final IConfigurationService configurationService;
  private final IOmniApi omniApi;
  private final IMessagingService messagingService;
  private CommunicationSettings communicationSettings;
  private ArrayList<ICanReceiveData> dataReceivers;

  @Inject
  public OmniClipboard(IConfigurationService configurationService,
                       IOmniApi omniApi,
                       IMessagingService messagingService) {
    this.configurationService = configurationService;
    this.omniApi = omniApi;
    this.messagingService = messagingService;
    dataReceivers = new ArrayList<ICanReceiveData>();
  }

  @Override
  public String getChannel() {
    return communicationSettings.getChannel();
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
  public synchronized void run() {
    communicationSettings = configurationService.getCommunicationSettings();
    messagingService.connect(getChannel(), this);
  }

  @Override
  public void putData(String data) {
    omniApi.saveClippingAsync(data, this);
  }

  @Override
  public void saveClippingSucceeded() {
    messagingService.sendAsync(getChannel(), "NewMessage", this);
  }

  @Override
  public void saveClippingFailed(String reason) {
    Log.i("OmniClipboard", reason);
  }

  @Override
  public void messageReceived(String message) {
    if (message != null) {
      omniApi.getLastClippingAsync(this);
    }
  }

  @Override
  public void handleClipping(String clip) {
    ClipboardData data = new ClipboardData(this, clip);
    for (ICanReceiveData receiver : dataReceivers) {
      receiver.dataReceived(data);
    }
  }

  @Override
  public void messageSent(String message) {
  }

  @Override
  public void messageSendFailed(String message, String reason) {
    Log.i("OmniClipboard", message + " " + reason);
  }

  @Override
  public void dispose() {
    messagingService.disconnect(getChannel());
  }
}
