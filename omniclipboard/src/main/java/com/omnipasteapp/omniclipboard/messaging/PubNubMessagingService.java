package com.omnipasteapp.omniclipboard.messaging;

import android.util.Log;

import com.pubnub.api.PubnubException;

import java.util.Hashtable;

import javax.inject.Inject;

public class PubNubMessagingService implements IMessagingService {
  private IPubNubClientFactory clientFactory;
  private IPubNubMessageBuilder messageBuilder;
  private IPubnub client;

  @Inject
  public PubNubMessagingService(IPubNubClientFactory clientFactory,
                                IPubNubMessageBuilder messageBuilder) {
    this.clientFactory = clientFactory;
    this.messageBuilder = messageBuilder;
  }

  @Override
  public boolean connect(String channel, IMessageHandler messageHandler) {
    client = clientFactory.create();

    boolean success;
    try {
      Hashtable<String, String> table = messageBuilder.setChannel(channel).build();
      client.subscribe(table, new MessageReceivedCallback(messageHandler));
      success = true;
    } catch (PubnubException e) {
      Log.i("PubNub/Subscribe", e.getMessage());
      success = false;
    }

    return success;
  }

  @Override
  public void disconnect(String channel) {
    client.unsubscribe(channel);
  }

  @Override
  public void sendAsync(String channel, String message, IMessageHandler messageHandler) {
    Hashtable<String, String> msg = messageBuilder.setChannel(channel)
        .addValue(message)
        .build();

    client.publish(msg, new MessageSentCallback(messageHandler));
  }
}
