package com.omnipasteapp.pubnubclipboard;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;

public class PubNubClipboardModule extends AbstractModule{
  @Override
  protected void configure() {
      bind(IPubNubClientFactory.class).to(PubNubClientFactory.class).in(Singleton.class);
      bind(IPubNubMessageBuilder.class).to(PubNubMessageBuilder.class).in(Singleton.class);
      bind(IOmniClipboard.class).to(PubNubClipboard.class);
  }
}
