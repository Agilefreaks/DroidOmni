package com.omnipasteapp.clipboardprovider.omniclipboard;

import android.util.Log;

import com.omnipasteapp.omniapi.IOmniApi;
import com.omnipasteapp.omniapi.OmniApi;
import com.omnipasteapp.omniapi.resources.IFetchClippingCompleteHandler;
import com.omnipasteapp.omniapi.resources.ISaveClippingCompleteHandler;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.messaging.IMessagingService;
import com.omnipasteapp.omnicommon.models.Clipping;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;

import java.util.ArrayList;

import javax.inject.Inject;

public class OmniClipboard implements IOmniClipboard, Runnable, ISaveClippingCompleteHandler, IFetchClippingCompleteHandler {
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

  public void saveClippingSucceeded() {
  }

  public void saveClippingFailed(String reason) {
    Log.i("OmniClipboard", reason);
  }

  // endregion

  // region IFetchClippingCompleteHandler

  public void handleClipping(Clipping clip) {
    for (ICanReceiveData receiver : dataReceivers) {
      receiver.dataReceived(clip);
    }
  }

  // endregion

  // region IMessageHandler

  public void messageReceived(String message) {
    if (message != null && !message.equals(messagingService.getRegistrationId())) {
      omniApi.clippings().getLastAsync(this);
    }
  }

  // endregion

  @Override
  public synchronized void run() {
    communicationSettings = configurationService.getCommunicationSettings();
//    messagingService.connect(getChannel(), this);

    OmniApi.setApiKey(getChannel());
  }

  @Override
  public void fetchSuccess(Clipping resource) {

  }

  @Override
  public void saveSuccess() {

  }

  @Override
  public void callFailed(String reason) {

  }
}
