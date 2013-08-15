package com.omnipasteapp.pubnubclipboard;

import com.omnipasteapp.api.IOmniApi;
import com.omnipasteapp.api.OmniApi;
import com.omnipasteapp.messaging.IMessagingService;
import com.omnipasteapp.messaging.IPubNubClientFactory;
import com.omnipasteapp.messaging.IPubNubMessageBuilder;
import com.omnipasteapp.messaging.PubNubClientFactory;
import com.omnipasteapp.messaging.PubNubMessageBuilder;
import com.omnipasteapp.messaging.PubNubMessagingService;
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

  @Provides
  @Singleton
  IMessagingService provideMessagingService(PubNubMessagingService pubNubMessagingService) {
    return pubNubMessagingService;
  }
}
