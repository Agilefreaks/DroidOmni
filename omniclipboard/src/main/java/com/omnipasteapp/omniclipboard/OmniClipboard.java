package com.omnipasteapp.omniclipboard;

import android.util.Log;

import com.omnipasteapp.omniclipboard.api.IGetClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.api.IOmniApi;
import com.omnipasteapp.omniclipboard.api.ISaveClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.api.OmniApi;
import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omniclipboard.messaging.IMessageHandler;
import com.omnipasteapp.omniclipboard.messaging.IMessagingService;
import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import java.util.ArrayList;
import java.util.UUID;

import javax.inject.Inject;

public class OmniClipboard implements IOmniClipboard, Runnable, ISaveClippingCompleteHandler, IGetClippingCompleteHandler, IMessageHandler {
  private static String messageUuid;

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
    this.messagingService = messagingService;
    this.omniApi = omniApi;

    dataReceivers = new ArrayList<ICanReceiveData>();
  }

  // region IOmniClipboard/IClipboard

  @Override
  public Thread initialize() {
    return new Thread(this);
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
  public void dispose() {
    messagingService.disconnect(getChannel());
  }

  @Override
  public void putData(String data) {
    omniApi.clippings().saveAsync(data, this);
  }

  // endregion

  // region ISaveClippingCompleteHandler

  @Override
  public void saveClippingSucceeded() {
    messagingService.sendAsync(getChannel(), getMessageUuid(), this);
  }

  @Override
  public void saveClippingFailed(String reason) {
    Log.i("OmniClipboard", reason);
  }

  // endregion

  // region IGetClippingCompleteHandler

  @Override
  public void handleClipping(Clipping clip) {
    ClipboardData data = new ClipboardData(this, clip);
    for (ICanReceiveData receiver : dataReceivers) {
      receiver.dataReceived(data);
    }
  }

  // endregion

  // region IMessageHandler

  @Override
  public void messageReceived(String message) {
    if (message != null && !message.equals(messageUuid)) {
      omniApi.clippings().getLastAsync(this);
    }
  }

  @Override
  public void messageSent(String message) {
  }

  @Override
  public void messageSendFailed(String message, String reason) {
    Log.i("OmniClipboard", message + " " + reason);
  }

  // endregion

  public String getMessageUuid() {
    messageUuid = UUID.randomUUID().toString();
    return messageUuid;
  }

  public void setMessageUuid(String value) {
    messageUuid = value;
  }

  @Override
  public synchronized void run() {
    communicationSettings = configurationService.getCommunicationSettings();
    messagingService.connect(getChannel(), this);

    OmniApi.setApiKey(getChannel());
  }

}
