package com.omnipasteapp.messaging;

import com.pubnub.api.Callback;
import com.pubnub.api.PubnubException;

import java.util.Hashtable;

public interface IPubnub {
  void publish(Hashtable<String, String> message, MessageSentCallback messageSentCallback);

  void subscribe(Hashtable<String, String> table, Callback callback) throws PubnubException;

  void unsubscribe(String channel);

  void shutdown();
}
