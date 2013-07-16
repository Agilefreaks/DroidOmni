package com.omnipasteapp.pubnubclipboard;

import com.pubnub.api.Callback;

public class MessageSentCallback extends Callback {
  @Override
  public void successCallback(String channel, Object message) {
    // do something here
  }
}
