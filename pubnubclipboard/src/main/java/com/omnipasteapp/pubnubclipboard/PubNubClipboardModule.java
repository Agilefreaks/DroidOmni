package com.omnipasteapp.pubnubclipboard;

import com.omnipasteapp.api.IOmniApi;
import com.omnipasteapp.api.OmniApi;
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

  @Provides
  @Singleton
  IOmniApi provideOmniApi(OmniApi omniApi) {
    return omniApi;
  }
}
