package com.omnipasteapp.pubnubclipboard;

import android.util.Log;
import com.google.inject.Inject;
import com.omnipasteapp.omnicommon.ClipboardData;
import com.omnipasteapp.omnicommon.interfaces.ICanReceiveData;
import com.omnipasteapp.omnicommon.interfaces.IConfigurationService;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;
import com.omnipasteapp.omnicommon.settings.CommunicationSettings;
import com.pubnub.api.Callback;
import com.pubnub.api.PubnubException;

import java.util.ArrayList;
import java.util.Hashtable;

public class PubNubClipboard extends Callback implements IOmniClipboard, Runnable {

  private final IConfigurationService configurationService;
  private final IPubNubClientFactory pubNubClientFactory;
  private IPubNubMessageBuilder pubNubMessageBuilder;
  private CommunicationSettings communicationSettings;

  private IPubnub pubnub;
  private ArrayList<ICanReceiveData> dataReceivers;

  @Inject
  public PubNubClipboard(IConfigurationService configurationService,
                         IPubNubClientFactory pubNubClientFactory,
                         IPubNubMessageBuilder pubNubMessageBuilder) {
    this.configurationService = configurationService;
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
    Hashtable<String, String> message = pubNubMessageBuilder.setChannel(getChannel())
            .addValue(data)
            .build();

    pubnub.publish(message, new MessageSentCallback());
  }

  @Override
  public Thread initialize() {
    return new Thread(this);
  }

  @Override
  public void run() {
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
      ClipboardData data = new ClipboardData(this, message.toString());
      for (ICanReceiveData receiver : dataReceivers) {
        receiver.dataReceived(data);
      }
    }
  }

  @Override
  public void errorCallback(java.lang.String s, java.lang.Object o) {
    Log.e("PubNub/Error", o.toString());
  }
}
