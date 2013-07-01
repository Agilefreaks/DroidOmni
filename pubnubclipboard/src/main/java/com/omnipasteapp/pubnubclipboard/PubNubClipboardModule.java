package com.omnipasteapp.pubnubclipboard;

import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class PubNubClipboardModule {
  @Provides
  @Singleton
  IPubNubClientFactory providePubNubClientFactory() {
    return new PubNubClientFactory();
  }

  @Provides
  @Singleton
  IPubNubMessageBuilder providePubNubMessageBuilder() {
    return new PubNubMessageBuilder();
  }

  @Provides
  IOmniClipboard provideOmniClipboard(PubNubClipboard pubNubClipboard) {
    return pubNubClipboard;
  }
}
