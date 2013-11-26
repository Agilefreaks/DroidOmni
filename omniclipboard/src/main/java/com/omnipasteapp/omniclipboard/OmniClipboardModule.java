package com.omnipasteapp.omniclipboard;

import com.omnipasteapp.omniclipboard.api.IOmniApi;
import com.omnipasteapp.omniclipboard.api.OmniApi;
import com.omnipasteapp.omniclipboard.messaging.GoogleMessagingService;
import com.omnipasteapp.omniclipboard.messaging.IMessagingService;
import com.omnipasteapp.omnicommon.interfaces.IOmniClipboard;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class OmniClipboardModule {
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
}
