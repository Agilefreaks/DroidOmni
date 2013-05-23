package com.omnipasteapp.pubnubclipboard;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;

public class PubNubClipboardModule extends AbstractModule{
  @Override
  protected void configure() {
    bind(IOmniClipboard.class).to(PubNubClipboard.class).in(Singleton.class);
  }
}
