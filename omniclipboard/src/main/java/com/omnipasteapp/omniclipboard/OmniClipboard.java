package com.omnipasteapp.omniclipboard;

import android.util.Log;

import com.omnipasteapp.omniapi.IOmniApi;
import com.omnipasteapp.omniapi.OmniApi;
import com.omnipasteapp.omniapi.resources.IFetchClippingCompleteHandler;
import com.omnipasteapp.omniapi.resources.ISaveClippingCompleteHandler;
import com.omnipasteapp.omniclipboard.messaging.IMessageHandler;
import com.omnipasteapp.omniclipboard.messaging.IMessagingService;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import java.util.ArrayList;

import javax.inject.Inject;

public class OmniClipboard implements IOmniClipboard, Runnable, ISaveClippingCompleteHandler, IFetchClippingCompleteHandler, IMessageHandler {
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
  }

  @Override
  public void saveClippingFailed(String reason) {
    Log.i("OmniClipboard", reason);
  }

  // endregion

  // region IFetchClippingCompleteHandler

  @Override
  public void handleClipping(Clipping clip) {
    for (ICanReceiveData receiver : dataReceivers) {
      receiver.dataReceived(clip);
    }
  }

  // endregion

  // region IMessageHandler

  @Override
  public void messageReceived(String message) {
    if (message != null && !message.equals(messagingService.getRegistrationId())) {
      omniApi.clippings().getLastAsync(this);
    }
  }

  // endregion

  @Override
  public synchronized void run() {
    communicationSettings = configurationService.getCommunicationSettings();
    messagingService.connect(getChannel(), this);

    OmniApi.setApiKey(getChannel());
  }
}
