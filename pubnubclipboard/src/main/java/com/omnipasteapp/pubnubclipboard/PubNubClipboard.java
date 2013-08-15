package com.omnipasteapp.pubnubclipboard;

import android.util.Log;

import com.omnipasteapp.api.IGetClippingCompleteHandler;
import com.omnipasteapp.api.IOmniApi;
import com.omnipasteapp.api.ISaveClippingCompleteHandler;
import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.pubnub.api.Callback;
import com.pubnub.api.PubnubException;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.inject.Inject;

public class PubNubClipboard extends Callback implements IOmniClipboard, Runnable, ISaveClippingCompleteHandler, IGetClippingCompleteHandler {

  private final IConfigurationService configurationService;
  private final IOmniApi omniApi;
  private final IPubNubClientFactory pubNubClientFactory;
  private IPubNubMessageBuilder pubNubMessageBuilder;
  private CommunicationSettings communicationSettings;

  private IPubnub pubnub;
  private ArrayList<ICanReceiveData> dataReceivers;

  @Inject
  public PubNubClipboard(IConfigurationService configurationService,
                         IOmniApi omniApi,
                         IPubNubClientFactory pubNubClientFactory,
                         IPubNubMessageBuilder pubNubMessageBuilder) {
    this.configurationService = configurationService;
    this.omniApi = omniApi;
    this.pubNubClientFactory = pubNubClientFactory;
    this.pubNubMessageBuilder = pubNubMessageBuilder;
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
  public void putData(String data) {
    omniApi.saveClippingAsync(data, this);
  }

  @Override
  public void saveClippingSucceeded() {
    Hashtable<String, String> message = pubNubMessageBuilder.setChannel(getChannel())
        .addValue("NewMessage")
        .build();

    pubnub.publish(message, new MessageSentCallback());
  }

  @Override
  public void saveClippingFailed(String reason) {
  }

  @Override
  public Thread initialize() {
    return new Thread(this);
  }

  @Override
  public synchronized void run() {
    communicationSettings = configurationService.getCommunicationSettings();
    pubnub = pubNubClientFactory.create();

    try {
      Hashtable<String, String> table = pubNubMessageBuilder.setChannel(communicationSettings.getChannel()).build();
      pubnub.subscribe(table, this);
    } catch (PubnubException e) {
      Log.i("PubNub/Subscribe", e.getMessage());
    }
  }

  @Override
  public void dispose() {
    if (pubnub != null) {
      pubnub.shutdown();
    }
  }

  @Override
  public void successCallback(String channel, Object message) {
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
  public void errorCallback(String s, Object o) {
    Log.e("PubNub/Error", o.toString());
  }
}
