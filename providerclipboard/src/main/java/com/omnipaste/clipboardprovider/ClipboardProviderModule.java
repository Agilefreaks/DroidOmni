package com.omnipaste.clipboardprovider;

import com.omnipaste.clipboardprovider.androidclipboard.LocalClipboardManager;
import com.omnipaste.clipboardprovider.omniclipboard.OmniClipboardManager;
import com.omnipaste.omniapi.resource.v1.Clippings;
import com.omnipaste.omnicommon.providers.NotificationProvider;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class ClipboardProviderModule {
  @Provides
  public OmniClipboardManager providesOmniClipboardManager(NotificationProvider notificationProvider, Clippings clippings) {
    return new OmniClipboardManager(notificationProvider, clippings);
  }

  @Provides
  public LocalClipboardManager providesLocalClipboardManager(android.content.ClipboardManager clipboardManager) {
    return new LocalClipboardManager(clipboardManager);
  }
}
