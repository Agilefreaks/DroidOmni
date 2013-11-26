package com.omnipasteapp.omniclipboard;

import com.omnipasteapp.omniclipboard.api.IOmniApi;
import com.omnipasteapp.omniclipboard.api.OmniApi;
import com.omnipasteapp.omniclipboard.messaging.GoogleMessagingService;
import com.omnipasteapp.omniclipboard.messaging.IMessagingService;
import com.omnipasteapp.omniclipboard.messaging.IPubNubClientFactory;
import com.omnipasteapp.omniclipboard.messaging.IPubNubMessageBuilder;
import com.omnipasteapp.omniclipboard.messaging.PubNubClientFactory;
import com.omnipasteapp.omniclipboard.messaging.PubNubMessageBuilder;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class OmniClipboardModule {
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
  IOmniClipboard provideOmniClipboard(OmniClipboard pubNubClipboard) {
    return pubNubClipboard;
  }

  @Provides
  @Singleton
  IOmniApi provideOmniApi(OmniApi omniApi) {
    return omniApi;
  }

  @Provides
  @Singleton
  IMessagingService provideMessagingService(GoogleMessagingService googleMessagingService) {
    return googleMessagingService;
  }

//  @Provides
//  @Singleton
//  IMessagingService provideMessagingService(PubNubMessagingService pubNubMessagingService) {
//    return pubNubMessagingService;
//  }
}
