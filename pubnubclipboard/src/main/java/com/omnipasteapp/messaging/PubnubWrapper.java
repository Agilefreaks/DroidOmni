package com.omnipasteapp.messaging;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

import java.util.Hashtable;

@SuppressWarnings("deprecation")
public class PubnubWrapper implements IPubnub {

  private Pubnub pubnub;

  public PubnubWrapper(Pubnub pubnub) {
    this.pubnub = pubnub;
  }

  @Override
  public void publish(Hashtable<String, String> message, MessageSentCallback messageSentCallback) {
    pubnub.publish(message, messageSentCallback);
  }

  @Override
  public void subscribe(Hashtable<String, String> table, Callback callback) throws PubnubException {
    pubnub.subscribe(table, callback);
  }

  @Override
  public void unsubscribe(String channel) {
    pubnub.unsubscribe(channel);
  }

  @Override
  public void shutdown() {
    pubnub.shutdown();
  }
}
